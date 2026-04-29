import { t, type UnwrapSchema } from 'elysia'

export const AuthModel = {
	signUpBody: t.Object({
		email: t.String({ format: 'email' }),
		password: t.String({ minLength: 6 }),
		confirmPassword: t.String(),
		cpf: t.String({ pattern: '\\d{11}' }),
		profileType: t.Union([t.Literal('client'), t.Literal('freelancer')]),
	}),
	signInBody: t.Object({
		email: t.String({ format: 'email' }),
		password: t.String(),
	}),
	signUpResponse: t.Object({
		token: t.String(),
		user: t.Object({
			id: t.Number(),
			email: t.String(),
			profileType: t.String(),
		}),
	}),
	signInResponse: t.Object({
		token: t.String(),
		user: t.Object({
			id: t.Number(),
			email: t.String(),
			profileType: t.String(),
		}),
	}),
} as const

export type AuthModel = {
	[k in keyof typeof AuthModel]: UnwrapSchema<typeof AuthModel[k]>
}