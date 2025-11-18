"use client";

import { useEffect } from "react";
import { useRouter } from "next/navigation";

const ONBOARDING_KEY = "onboarding_completed";

export default function HomePage() {
  const router = useRouter();

  useEffect(() => {
    const hasCompletedOnboarding = localStorage.getItem(ONBOARDING_KEY);
    if (hasCompletedOnboarding !== "true") {
      router.replace("/onboarding");
    }
  }, [router]);

  return (
    <main className="flex min-h-screen flex-col items-center justify-center p-24">
      <div className="text-center">
        <h1 className="text-4xl font-bold">Trang chủ</h1>
        <p className="mt-4 text-lg text-gray-600">
          Chào mừng bạn đã quay trở lại!
        </p>
      </div>
    </main>
  );
}
