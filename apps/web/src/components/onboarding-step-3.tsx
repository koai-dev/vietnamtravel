'use client';

import React from 'react';
import { Button } from './ui/button';

interface OnboardingStep3Props {
  onAllow: () => void;
  onSkip: () => void;
}

export const OnboardingStep3 = ({ onAllow, onSkip }: OnboardingStep3Props) => {
  return (
    <div className="relative flex h-auto min-h-screen w-full flex-col bg-background-light dark:bg-background-dark group/design-root overflow-x-hidden">
      <div className="layout-container flex h-full grow flex-col">
        <div className="flex flex-1 items-center justify-center p-4 sm:p-6 md:p-8">
          <div className="layout-content-container flex w-full max-w-md flex-col items-center justify-center rounded-xl bg-white dark:bg-background-dark/50 p-6 shadow-sm sm:p-8">
            <div className="flex w-full max-w-xs grow bg-white dark:bg-transparent @container pb-6">
              <div className="w-full gap-1 overflow-hidden bg-white dark:bg-transparent @[480px]:gap-2 aspect-square flex">
                <div
                  className="w-full bg-center bg-no-repeat bg-contain aspect-auto rounded-none flex-1"
                  data-alt="Stylized illustration of a map with a location pin"
                  style={{
                    backgroundImage:
                      'url("https://lh3.googleusercontent.com/aida-public/AB6AXuB1zwczvEvnkAhGwq9FU4POeSBNVIWItivJqWAW9AV5IMkpUm-kVzhbRr23gievKn6bfnG9hbMeRKSmbS-uiXGeWNwJ-_6RntnOUOZWFtz2UQQda3mFmU-CU4_8w7gjctqNZPe9CgdTBZ68OQQHBs-eehPe-mRrJP7fmh9ZLemrjvEA0zbFw4s7WqSrlblxEGyqilWIcV4k-Bl0vk7zuQlsU9wTQpfKXXldvyMu23ziyoIE66Wwf1rg2bG2SYMO4cBqp5-wTzDFr7s")',
                  }}
                ></div>
              </div>
            </div>
            <h1 className="text-[#111418] dark:text-white tracking-tight text-2xl sm:text-3xl font-bold leading-tight px-4 text-center pb-2">Tìm kiếm địa điểm xung quanh bạn</h1>
            <p className="text-gray-600 dark:text-gray-300 text-base font-normal leading-normal pb-6 pt-1 px-4 text-center">Cho phép truy cập vị trí để gợi ý chính xác hơn.</p>
            <div className="flex w-full justify-center">
              <div className="flex w-full flex-1 gap-3 max-w-xs flex-col items-stretch px-4 py-3">
                <Button onClick={onAllow}>Cho phép</Button>
                <Button onClick={onSkip} variant="secondary">Không phải bây giờ</Button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};
