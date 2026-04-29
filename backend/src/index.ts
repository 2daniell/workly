import { Elysia } from 'elysia'
import { jwt } from '@elysiajs/jwt'
import { AuthController } from './modules/auth/auth.controller'
import { ServiceController } from './modules/service/service.controller'
import { ChatController } from './modules/chat/chat.controller'

const app = new Elysia()
	.use(
		jwt({
			name: 'jwt',
			secret: 'super-secret-key', // In production, use env variable
		})
	)
	.use(AuthController)
	.use(ServiceController)
	.use(ChatController)
	.get('/', () => 'Hello Workly')
	.listen(3000)
