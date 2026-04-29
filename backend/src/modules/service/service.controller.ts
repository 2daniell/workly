import Elysia from 'elysia'
import { t } from 'elysia'
import { jwt } from '@elysiajs/jwt'
import { ServiceService } from './service.service'
import { ServiceModel } from './service.model'

const ServiceController = new Elysia()
	.use(
		jwt({
			name: 'jwt',
			secret: 'super-secret-key', // In production, use env variable
		})
	)
	.group('/services', (app) =>
		app
			.post(
				'/',
				async ({ body, jwt }) => {
					const payload = await jwt.verify()
					if (!payload || typeof payload !== 'object' || !('id' in payload)) throw new Error('Unauthorized')
					const id = (payload as { id: number }).id

					const service = await ServiceService.createService(id, body)
					return {
						...service,
						createdAt: service.createdAt.toISOString(),
					}
				},
				{
					body: ServiceModel.createServiceBody,
					response: ServiceModel.serviceResponse,
				}
			)
			.get(
				'/',
				async ({ query }) => {
					const page = parseInt(query.page || '1')
					const limit = parseInt(query.limit || '10')
					return ServiceService.getServices(page, limit)
				},
				{
					query: t.Object({
						page: t.Optional(t.String()),
						limit: t.Optional(t.String()),
					}),
					response: ServiceModel.servicesListResponse,
				}
			)
	)
	.group('/proposals', (app) =>
		app.post(
			'/',
			async ({ body, jwt }) => {
				const payload = await jwt.verify()
				if (!payload || typeof payload !== 'object' || !('id' in payload)) throw new Error('Unauthorized')
				const id = (payload as { id: number }).id

				const proposal = await ServiceService.createProposal(id, body)
				return {
					...proposal,
					createdAt: proposal.createdAt.toISOString(),
				}
			},
			{
				body: ServiceModel.createProposalBody,
				response: ServiceModel.proposalResponse,
			}
		)
	)

export { ServiceController }