"use client"

import { zodResolver } from "@hookform/resolvers/zod"
import { useForm } from "react-hook-form"
import { z } from "zod"
import { useParams, useRouter } from "next/navigation"
import { useEffect } from "react"

import { Button } from "@/components/ui/button"
import {
  Form,
  FormControl,
  FormDescription,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from "@/components/ui/form"
import { Input } from "@/components/ui/input"
import { api } from "@/lib/api"
import { Province } from "../columns"

const formSchema = z.object({
  name_en: z.string().min(2, {
    message: "English name must be at least 2 characters.",
  }),
  name_vi: z.string().min(2, {
    message: "Vietnamese name must be at least 2 characters.",
  }),
  image: z.string().url({ message: "Please enter a valid URL." }),
})

export default function ProvinceForm() {
  const router = useRouter()
  const { provinceId } = useParams()
  const isEditing = provinceId !== "new"

  const form = useForm<z.infer<typeof formSchema>>({
    resolver: zodResolver(formSchema),
    defaultValues: {
      name_en: "",
      name_vi: "",
      image: "",
    },
  })

  useEffect(() => {
    if (isEditing) {
      const fetchProvince = async () => {
        try {
          const response = await api.get<Province>(`/provinces/${provinceId}`)
          form.reset({
            name_en: response.data.name.en,
            name_vi: response.data.name.vi,
            image: response.data.image,
          })
        } catch (error) {
          console.error("Failed to fetch province:", error)
        }
      }
      fetchProvince()
    }
  }, [isEditing, provinceId, form])

  async function onSubmit(values: z.infer<typeof formSchema>) {
    try {
      if (isEditing) {
        await api.put(`/provinces/${provinceId}`, {
          name: {
            en: values.name_en,
            vi: values.name_vi,
          },
          image: values.image,
        })
      } else {
        await api.post("/provinces", {
          name: {
            en: values.name_en,
            vi: values.name_vi,
          },
          image: values.image,
        })
      }
      router.push("/dashboard/provinces")
    } catch (error) {
      console.error("Failed to save province:", error)
    }
  }

  return (
    <Form {...form}>
      <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-8">
        <FormField
          control={form.control}
          name="name_en"
          render={({ field }) => (
            <FormItem>
              <FormLabel>English Name</FormLabel>
              <FormControl>
                <Input placeholder="Hanoi" {...field} />
              </FormControl>
              <FormMessage />
            </FormItem>
          )}
        />
        <FormField
          control={form.control}
          name="name_vi"
          render={({ field }) => (
            <FormItem>
              <FormLabel>Vietnamese Name</FormLabel>
              <FormControl>
                <Input placeholder="Hà Nội" {...field} />
              </FormControl>
              <FormMessage />
            </FormItem>
          )}
        />
        <FormField
          control={form.control}
          name="image"
          render={({ field }) => (
            <FormItem>
              <FormLabel>Image URL</FormLabel>
              <FormControl>
                <Input placeholder="https://example.com/hanoi.jpg" {...field} />
              </FormControl>
              <FormMessage />
            </FormItem>
          )}
        />
        <Button type="submit">Submit</Button>
      </form>
    </Form>
  )
}
