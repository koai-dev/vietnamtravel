"use server"

import { api } from "@/lib/api"
import { revalidatePath } from "next/cache"

export async function deleteUser(userId: string) {
  try {
    await api.delete(`/users/${userId}`)
    revalidatePath("/dashboard/users")
  } catch (error) {
    console.error("Failed to delete user:", error)
    throw new Error("Failed to delete user")
  }
}
