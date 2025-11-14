'use client';

import React from 'react';
import { Button } from './ui/button';

interface OnboardingStep1Props {
  onNext: () => void;
  onSkip: () => void;
}

export const OnboardingStep1 = ({ onNext, onSkip }: OnboardingStep1Props) => {
  return (
    <div className="relative flex min-h-screen w-full flex-col bg-background-light dark:bg-background-dark group/design-root overflow-x-hidden">
      <div className="layout-container flex h-full grow flex-col">
        <main className="flex flex-1 flex-col justify-center items-center py-5">
          <div className="layout-content-container flex flex-col w-full max-w-md flex-1">
            <div className="absolute top-5 right-5 md:right-10 z-10">
              <p onClick={onSkip} className="text-gray-500 dark:text-gray-400 text-sm font-medium leading-normal underline cursor-pointer">Bỏ qua</p>
            </div>
            <div className="flex flex-1 flex-col justify-between p-6">
              <div className="flex-grow flex items-center">
                <div className="w-full">
                  <div className="flex w-full grow bg-transparent py-3">
                    <div className="w-full gap-1 overflow-hidden bg-transparent @[480px]:gap-2 flex">
                      <img
                        className="w-full h-auto object-cover aspect-square max-w-[320px] mx-auto"
                        data-alt="Stylized illustration of a person with a backpack looking out over a Vietnamese landscape with rice paddies and mountains."
                        src="https://lh3.googleusercontent.com/aida-public/AB6AXuCCdel6TNtv67xcM13tUClH9pL71KQfzyCeaba_Q5r-bvoQeVm_vLv1ppEEvrKQeKHBNosUW5hp6X-o22u5LMOw17E13XaJyP8GtQEtnriW2X_3SJ4kBRzD9csHQr1BWsi7vgG52-uj1PIBuOydAn6jhMUucClMMP6rKMKV9152awxlDmWbq4CrstzS3jv19bUoaszByGi9_r-fQSvSEHaw323jMhEQuYpRD0v-QeFx9OjlhAMl_L3Ik8I3-f7ovVGWZf7XO-L8lLE"
                      />
                    </div>
                  </div>
                </div>
              </div>
              <div className="text-center">
                <h1 className="text-gray-900 dark:text-white tracking-tight text-[32px] font-bold leading-tight pb-3">Khám phá Việt Nam dễ dàng</h1>
                <p className="text-gray-600 dark:text-gray-300 text-base font-normal leading-normal pb-6">Your personal guide to finding the best destinations, food, and culture.</p>
                <div className="flex px-4 py-3 justify-center">
                  <Button onClick={onNext} className="w-full min-w-[84px] max-w-[480px]">Bắt đầu</Button>
                </div>
              </div>
            </div>
          </div>
        </main>
      </div>
    </div>
  );
};
