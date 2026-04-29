import Elysia from 'elysia'
import { t } from 'elysia'
import { jwt } from '@elysiajs/jwt'
import { eq } from 'drizzle-orm'
import { db } from '../../db'
import { services, users } from '../../db/schema'

// Map to hold validated data by token
const validatedTokens = new Map<string, { userId: number; serviceId: number; freelancerId: number }>()

// Map to hold chat rooms: key is `${serviceId}-${freelancerId}`, value is Set of ServerWebSocket
const chatRooms = new Map<string, Set<any>>()

const ChatController = new Elysia()
	.use(
		jwt({
			name: 'jwt',
			secret: 'super-secret-key', // In production, use env variable
		})
	)
	.ws('/chats/:serviceId/:freelancerId', {
		beforeHandle: async ({ params, query, jwt }) => {
			const token = query.token as string
			if (!token) throw new Error('Token required')

			const payload = await jwt.verify(token)
			if (!payload || typeof payload !== 'object' || !('id' in payload)) throw new Error('Invalid token')

			const userId = (payload as { id: number }).id
			const serviceId = parseInt(params.serviceId)
			const freelancerId = parseInt(params.freelancerId)

			// Verify service exists and user is either the freelancer or the client
			const [service] = await db
				.select({ clientId: services.clientId })
				.from(services)
				.where(eq(services.id, serviceId))
				.limit(1)

			if (!service) throw new Error('Service not found')

			if (userId !== freelancerId && userId !== service.clientId) {
				throw new Error('Unauthorized')
			}

			// Verify freelancer exists and is freelancer
			const [freelancer] = await db
				.select()
				.from(users)
				.where(eq(users.id, freelancerId))
				.limit(1)

			if (!freelancer || freelancer.profileType !== 'freelancer') {
				throw new Error('Invalid freelancer')
			}

			validatedTokens.set(token, { userId, serviceId, freelancerId })
		},
		open: (ws) => {
			const token = ws.data.query?.token as string
			const data = validatedTokens.get(token)
			if (!data) {
				ws.close(1008, 'Unauthorized')
				return
			}
			const { serviceId, freelancerId } = data
			const roomKey = `${serviceId}-${freelancerId}`
			if (!chatRooms.has(roomKey)) {
				chatRooms.set(roomKey, new Set())
			}
			//hatRooms.get(roomKey)!.add(ws.raw)
			// Store data on ws for later use
			(ws as any).chatData = data
		},
		message: (ws, message) => {
			const data = (ws as any).chatData as { userId: number; serviceId: number; freelancerId: number }
			if (!data) return
			const { serviceId, freelancerId, userId } = data
			const roomKey = `${serviceId}-${freelancerId}`
			const room = chatRooms.get(roomKey)
			if (room) {
				const msg = JSON.stringify({
					userId,
					message,
					timestamp: new Date().toISOString(),
				})
				room.forEach((client) => {
					if (client !== ws.raw) { // Don't send to self
						client.send(msg)
					}
				})
			}
		},
		close: (ws) => {
			const data = (ws as any).chatData as { userId: number; serviceId: number; freelancerId: number }
			if (!data) return
			const { serviceId, freelancerId } = data
			const roomKey = `${serviceId}-${freelancerId}`
			const room = chatRooms.get(roomKey)
			if (room) {
				room.delete(ws.raw)
				if (room.size === 0) {
					chatRooms.delete(roomKey)
				}
			}
		},
	})

export { ChatController }
