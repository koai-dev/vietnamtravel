"use client";

import Image from "next/image";
import { Button } from "@/components/ui/button";

interface Step2Props {
  onComplete: () => void;
  onSkip: () => void;
}

export default function Step2({ onComplete, onSkip }: Step2Props) {
  return (
    <main className="flex h-full flex-1 flex-col items-center justify-center p-6">
      <div className="absolute top-5 right-5 z-10 md:right-10">
        <Button variant="link" onClick={onSkip} className="text-gray-500 dark:text-gray-400">
          Bỏ qua
        </Button>
      </div>
      <div className="flex w-full max-w-md flex-1 flex-col justify-between">
        <div className="flex flex-grow items-center">
          <div className="w-full">
            <div className="flex w-full grow justify-center bg-transparent py-3">
              {/*
                TODO: Replace the placeholder image with the actual illustration.
                The image should be optimized for the web (e.g., converted to .webp format).
                Update the `src` attribute to point to the new image file.
                Example: src="/images/onboarding/vietnam-discovery.webp"
              */}
              <Image
                src="/images/onboarding/onboarding-hero.svg"
                alt="Stylized illustration of a person with a backpack looking out over a Vietnamese landscape with rice paddies and mountains."
                width={320}
                height={320}
                className="aspect-square h-auto w-full max-w-[320px] rounded-lg object-cover"
              />
            </div>
          </div>
        </div>
        <div className="text-center">
          <h1 className="pb-3 text-3xl font-bold tracking-tight text-gray-900 dark:text-white md:text-4xl">
            Khám phá Việt Nam dễ dàng
          </h1>
          <p className="pb-6 text-base text-gray-600 dark:text-gray-300">
            Your personal guide to finding the best destinations, food, and
            culture.
          </p>
          <div className="flex justify-center px-4 py-3">
            <Button onClick={onComplete} size="lg" className="w-full max-w-sm">
              Bắt đầu
            </Button>
          </div>
        </div>
      </div>
    </main>
  );
}
