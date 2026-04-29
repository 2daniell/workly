import { sqliteTable, text, integer, real } from 'drizzle-orm/sqlite-core'

export const users = sqliteTable('users', {
  id: integer('id').primaryKey({ autoIncrement: true }),
  email: text('email').unique().notNull(),
  password: text('password').notNull(),
  cpf: text('cpf').unique().notNull(),
  profileType: text('profile_type').notNull(), // 'client' or 'freelancer'
  createdAt: integer('created_at', { mode: 'timestamp' }).notNull(),
})

export const services = sqliteTable('services', {
  id: integer('id').primaryKey({ autoIncrement: true }),
  title: text('title').notNull(),
  description: text('description').notNull(),
  estimatedValue: real('estimated_value').notNull(),
  clientId: integer('client_id').references(() => users.id).notNull(),
  createdAt: integer('created_at', { mode: 'timestamp' }).notNull(),
})

export const proposals = sqliteTable('proposals', {
  id: integer('id').primaryKey({ autoIncrement: true }),
  serviceId: integer('service_id').references(() => services.id).notNull(),
  freelancerId: integer('freelancer_id').references(() => users.id).notNull(),
  proposalText: text('proposal_text').notNull(),
  status: text('status').default('pending').notNull(), // 'pending', 'accepted', 'rejected'
  createdAt: integer('created_at', { mode: 'timestamp' }).notNull(),
})

export const chats = sqliteTable('chats', {
  id: integer('id').primaryKey({ autoIncrement: true }),
  serviceId: integer('service_id').references(() => services.id).notNull(),
  freelancerId: integer('freelancer_id').references(() => users.id).notNull(),
  createdAt: integer('created_at', { mode: 'timestamp' }).notNull(),
})

export const messages = sqliteTable('messages', {
  id: integer('id').primaryKey({ autoIncrement: true }),
  chatId: integer('chat_id').references(() => chats.id).notNull(),
  senderId: integer('sender_id').references(() => users.id).notNull(),
  message: text('message').notNull(),
  createdAt: integer('created_at', { mode: 'timestamp' }).notNull(),
})