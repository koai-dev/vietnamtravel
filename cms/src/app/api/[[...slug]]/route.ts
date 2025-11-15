import { NextRequest, NextResponse } from "next/server";
import { cookies } from "next/headers";

const BACKEND_URL = process.env.NEXT_PUBLIC_BACKEND_BASE_URL;

async function handler(req: NextRequest) {
  const { pathname, search } = req.nextUrl;
  const path = pathname.replace("/api", "");
  const url = `${BACKEND_URL}${path}${search}`;

  const headers = new Headers(req.headers);
  const accessToken = cookies().get("accessToken")?.value;

  if (accessToken) {
    headers.set("Authorization", `Bearer ${accessToken}`);
  }

  try {
    const response = await fetch(url, {
      method: req.method,
      headers,
      body: req.body,
      // @ts-ignore
      duplex: "half",
    });

    return response;
  } catch (error) {
    console.error("API proxy error:", error);
    return new NextResponse("API proxy error", { status: 500 });
  }
}

export { handler as GET, handler as POST, handler as PUT, handler as DELETE, handler as PATCH };
