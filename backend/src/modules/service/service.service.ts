import { eq, and } from 'drizzle-orm'
import { db } from '../../db'
import { services, users, proposals } from '../../db/schema'
import type { ServiceModel } from './service.model'

export abstract class ServiceService {
	public static async createService(
		clientId: number,
		{ title, description, estimatedValue }: ServiceModel['createServiceBody']
	) {
		const [client] = await db
			.select()
			.from(users)
			.where(eq(users.id, clientId))
			.limit(1)

		if (!client || client.profileType !== 'client') {
			throw new Error('Only clients can create services')
		}

		const [newService] = await db
			.insert(services)
			.values({
				title,
				description,
				estimatedValue,
				clientId,
				createdAt: new Date(),
			})
			.returning()

		return newService
	}

	public static async getServices(page: number = 1, limit: number = 10) {
		const offset = (page - 1) * limit

		const servicesList = await db
			.select()
			.from(services)
			.limit(limit)
			.offset(offset)

		const [{ count }] = await db
			.select({ count: services.id })
			.from(services)

		return {
			services: servicesList.map((s) => ({
				...s,
				createdAt: s.createdAt.toISOString(),
			})),
			total: count,
			page,
			limit,
		}
	}

	public static async createProposal(
		freelancerId: number,
		{ serviceId, proposalText }: ServiceModel['createProposalBody']
	) {
		const [freelancer] = await db
			.select()
			.from(users)
			.where(eq(users.id, freelancerId))
			.limit(1)

		if (!freelancer || freelancer.profileType !== 'freelancer') {
			throw new Error('Only freelancers can send proposals')
		}

		const [service] = await db
			.select()
			.from(services)
			.where(eq(services.id, serviceId))
			.limit(1)

		if (!service) {
			throw new Error('Service not found')
		}

		// Check if freelancer already sent a proposal for this service
		const existingProposal = await db
			.select()
			.from(proposals)
			.where(and(eq(proposals.serviceId, serviceId), eq(proposals.freelancerId, freelancerId)))
			.limit(1)

		if (existingProposal.length > 0) {
			throw new Error('You have already sent a proposal for this service')
		}

		const [newProposal] = await db
			.insert(proposals)
			.values({
				serviceId,
				freelancerId,
				proposalText,
				status: 'pending',
				createdAt: new Date(),
			})
			.returning()

		return newProposal
	}
}