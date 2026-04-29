import { eq } from 'drizzle-orm'
import bcrypt from 'bcryptjs'
import { db } from '../../db'
import { users } from '../../db/schema'
import type { AuthModel } from './auth.model'

export abstract class Auth {
	public static async signUp({
		email,
		password,
		confirmPassword,
		cpf,
		profileType,
	}: AuthModel['signUpBody']) {
		if (password !== confirmPassword) {
			throw new Error('Passwords do not match')
		}

		const existingUser = await db
			.select()
			.from(users)
			.where(eq(users.email, email))
			.limit(1)

		if (existingUser.length > 0) {
			throw new Error('User already exists')
		}

		const hashedPassword = await bcrypt.hash(password, 10)

		const [newUser] = await db
			.insert(users)
			.values({
				email,
				password: hashedPassword,
				cpf,
				profileType,
				createdAt: new Date(),
			})
			.returning()

		return {
			token: 'jwt-token-here', // Will be set by controller with JWT
			user: {
				id: newUser.id,
				email: newUser.email,
				profileType: newUser.profileType,
			},
		}
	}

	public static async signIn({ email, password }: AuthModel['signInBody']) {
		const [user] = await db
			.select()
			.from(users)
			.where(eq(users.email, email))
			.limit(1)

		if (!user) {
			throw new Error('Invalid credentials')
		}

		const isPasswordValid = await bcrypt.compare(password, user.password)
		if (!isPasswordValid) {
			throw new Error('Invalid credentials')
		}

		return {
			token: 'jwt-token-here', // Will be set by controller with JWT
			user: {
				id: user.id,
				email: user.email,
				profileType: user.profileType,
			},
		}
	}
}