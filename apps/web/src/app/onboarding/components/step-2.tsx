"use client";

import Image from "next/image";

interface Step2Props {
  onComplete: () => void;
  onSkip: () => void;
}

export default function Step2({ onComplete, onSkip }: Step2Props) {
  return (
    <div className="layout-container flex h-full grow flex-col">
      <main className="flex flex-1 flex-col justify-center items-center py-5">
        <div className="layout-content-container flex flex-col w-full max-w-md flex-1">
          <div className="absolute top-5 right-5 md:right-10 z-10">
            <p
              onClick={onSkip}
              className="text-gray-500 dark:text-gray-400 text-sm font-medium leading-normal underline cursor-pointer"
            >
              Bỏ qua
            </p>
          </div>
          <div className="flex flex-1 flex-col justify-between p-6">
            <div className="flex-grow flex items-center">
              <div className="w-full">
                <div className="flex w-full grow bg-transparent py-3">
                  <div className="w-full gap-1 overflow-hidden bg-transparent @[480px]:gap-2 flex">
                    <Image
                      className="w-full h-auto object-cover aspect-square max-w-[320px] mx-auto"
                      alt="Stylized illustration of a person with a backpack looking out over a Vietnamese landscape with rice paddies and mountains."
                      src="/images/onboarding-illustration.png"
                      width={320}
                      height={320}
                    />
                  </div>
                </div>
              </div>
            </div>
            <div className="text-center">
              <h1 className="text-gray-900 dark:text-white tracking-tight text-[32px] font-bold leading-tight pb-3">
                Khám phá Việt Nam dễ dàng
              </h1>
              <p className="text-gray-600 dark:text-gray-300 text-base font-normal leading-normal pb-6">
                Your personal guide to finding the best destinations, food, and
                culture.
              </p>
              <div className="flex px-4 py-3 justify-center">
                <button
                  onClick={onComplete}
                  className="flex w-full min-w-[84px] max-w-[480px] cursor-pointer items-center justify-center overflow-hidden rounded-xl h-12 px-5 bg-primary text-white text-base font-bold leading-normal tracking-[0.015em] hover:bg-primary/90 focus:ring-4 focus:ring-primary/30 dark:focus:ring-primary/40"
                >
                  <span className="truncate">Bắt đầu</span>
                </button>
              </div>
            </div>
          </div>
        </div>
      </main>
    </div>
  );
}
