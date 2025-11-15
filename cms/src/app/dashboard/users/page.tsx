import Link from "next/link"
import { User, columns } from "./columns"
import { DataTableClient } from "@/components/data-table/data-table-client"
import { api } from "@/lib/api"
import { Button } from "@/components/ui/button"

async function getData(search: string, page: number, pageSize: number): Promise<User[]> {
  try {
    const response = await api.get(`/users?q=${search}&page=${page}&pageSize=${pageSize}`)
    return response.data
  } catch (error) {
    console.error("Failed to fetch users:", error)
    return []
  }
}

export default async function UsersPage({
  searchParams,
}: {
  searchParams?: {
    q?: string
    page?: string
    pageSize?: string
  }
}) {
  const page = Number(searchParams?.page) || 1
  const pageSize = Number(searchParams?.pageSize) || 10
  const data = await getData(searchParams?.q || "", page, pageSize)

  return (
    <div className="container mx-auto py-10">
      <div className="flex justify-end mb-4">
        <Button asChild>
          <Link href="/dashboard/users/new">Add User</Link>
        </Button>
      </div>
      <DataTableClient columns={columns} data={data} />
    </div>
  )
}
