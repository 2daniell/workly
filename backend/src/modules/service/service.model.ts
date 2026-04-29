import { t, type UnwrapSchema } from 'elysia'

export const ServiceModel = {
	createServiceBody: t.Object({
		title: t.String(),
		description: t.String(),
		estimatedValue: t.Number(),
	}),
	serviceResponse: t.Object({
		id: t.Number(),
		title: t.String(),
		description: t.String(),
		estimatedValue: t.Number(),
		clientId: t.Number(),
		createdAt: t.String(),
	}),
	servicesListResponse: t.Object({
		services: t.Array(ServiceModel.serviceResponse),
		total: t.Number(),
		page: t.Number(),
		limit: t.Number(),
	}),
	createProposalBody: t.Object({
		serviceId: t.Number(),
		proposalText: t.String(),
	}),
	proposalResponse: t.Object({
		id: t.Number(),
		serviceId: t.Number(),
		freelancerId: t.Number(),
		proposalText: t.String(),
		status: t.String(),
		createdAt: t.String(),
	}),
} as const

export type ServiceModel = {
	[k in keyof typeof ServiceModel]: UnwrapSchema<typeof ServiceModel[k]>
}