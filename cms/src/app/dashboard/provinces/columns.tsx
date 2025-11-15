"use client"

import { ColumnDef } from "@tanstack/react-table"
import { MoreHorizontal } from "lucide-react"
import { useRouter } from "next/navigation"

import { Button } from "@/components/ui/button"
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuLabel,
  DropdownMenuSeparator,
  DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu"
import Image from "next/image"
import { deleteProvince } from "./actions"

// This type is temporary. I'll replace it with the actual type from the backend.
export type Province = {
  id: string
  name: {
    en: string
    vi: string
  }
  image: string
}

export const columns: ColumnDef<Province>[] = [
  {
    accessorKey: "image",
    header: "Image",
    cell: ({ row }) => {
      const imageUrl = row.getValue("image") as string;
      return <Image src={imageUrl} alt={row.original.name.en} width={64} height={64} className="rounded-md" />
    }
  },
  {
    accessorKey: "name",
    header: "Name",
    cell: ({ row }) => {
      const name = row.getValue("name") as { en: string, vi: string };
      return <div>{name.en} / {name.vi}</div>
    }
  },
  {
    id: "actions",
    cell: ({ row }) => {
      const province = row.original
      const router = useRouter()

      return (
        <DropdownMenu>
          <DropdownMenuTrigger asChild>
            <Button variant="ghost" className="h-8 w-8 p-0">
              <span className="sr-only">Open menu</span>
              <MoreHorizontal className="h-4 w-4" />
            </Button>
          </DropdownMenuTrigger>
          <DropdownMenuContent align="end">
            <DropdownMenuLabel>Actions</DropdownMenuLabel>
            <DropdownMenuItem
              onClick={() => navigator.clipboard.writeText(province.id)}
            >
              Copy province ID
            </DropdownMenuItem>
            <DropdownMenuSeparator />
            <DropdownMenuItem onClick={() => router.push(`/dashboard/provinces/${province.id}`)}>Edit</DropdownMenuItem>
            <DropdownMenuItem onClick={() => {
              if (confirm("Are you sure you want to delete this province?")) {
                deleteProvince(province.id)
              }
            }}>Delete</DropdownMenuItem>
          </DropdownMenuContent>
        </DropdownMenu>
      )
    },
  },
]
