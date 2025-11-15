"use server"

import { api } from "@/lib/api"
import { revalidatePath } from "next/cache"

export async function deleteProvince(provinceId: string) {
  try {
    await api.delete(`/provinces/${provinceId}`)
    revalidatePath("/dashboard/provinces")
  } catch (error) {
    console.error("Failed to delete province:", error)
    throw new Error("Failed to delete province")
  }
}
