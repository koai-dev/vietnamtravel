import Link from "next/link"
import { Province, columns } from "./columns"
import { DataTable } from "@/components/data-table/data-table"
import { api } from "@/lib/api"
import { Button } from "@/components/ui/button"

async function getData(): Promise<Province[]> {
  try {
    const response = await api.get("/provinces")
    return response.data
  } catch (error) {
    console.error("Failed to fetch provinces:", error)
    return []
  }
}

export default async function ProvincesPage() {
  const data = await getData()

  return (
    <div className="container mx-auto py-10">
      <div className="flex justify-end mb-4">
        <Button asChild>
          <Link href="/dashboard/provinces/new">Add Province</Link>
        </Button>
      </div>
      <DataTable columns={columns} data={data} />
    </div>
  )
}
