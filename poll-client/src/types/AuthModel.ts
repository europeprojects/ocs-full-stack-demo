export type LoginResponse = {
  success: boolean
  accessToken: string
  user: PollUser
}

export type PollUser = {
  username: string
  name: string
  type: "admin" | "user" | "anonymous"
  id: number
}
