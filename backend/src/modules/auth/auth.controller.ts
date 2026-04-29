
import Elysia from 'elysia'
import { jwt } from '@elysiajs/jwt'
import { Auth } from './auth.service'
import { AuthModel } from './auth.model'

const AuthController = new Elysia()
	.use(
		jwt({
			name: 'jwt',
			secret: 'super-secret-key', // In production, use env variable
		})
	)
	.group('/auth', (app) =>
		app
			.post(
				'/signup',
				async ({ body, jwt }) => {
					const result = await Auth.signUp(body)
					const token = await jwt.sign({ id: result.user.id, email: result.user.email })
					return {
						token,
						user: result.user,
					}
				},
				{
					body: AuthModel.signUpBody,
					response: AuthModel.signUpResponse,
				}
			)
			.post(
				'/signin',
				async ({ body, jwt }) => {
					const result = await Auth.signIn(body)
					const token = await jwt.sign({ id: result.user.id, email: result.user.email })
					return {
						token,
						user: result.user,
					}
				},
				{
					body: AuthModel.signInBody,
					response: AuthModel.signInResponse,
				}
			)
	)

export { AuthController }