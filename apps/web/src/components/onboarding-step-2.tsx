'use client';

import React from 'react';
import { Button } from './ui/button';

interface OnboardingStep2Props {
  onNext: () => void;
  onSkip: () => void;
}

export const OnboardingStep2 = ({ onNext, onSkip }: OnboardingStep2Props) => {
  return (
    <div className="relative flex min-h-screen w-full flex-col bg-background-light dark:bg-background-dark group/design-root overflow-x-hidden">
      <div className="layout-container flex h-full grow flex-col">
        <main className="flex flex-1 flex-col justify-center items-center py-5">
          <div className="layout-content-container flex flex-col w-full max-w-5xl flex-1 p-6">
            <div className="absolute top-5 right-5 md:right-10 z-10">
              <p onClick={onSkip} className="text-gray-500 dark:text-gray-400 text-sm font-medium leading-normal underline cursor-pointer">Bỏ qua</p>
            </div>
            <div className="flex flex-1 flex-col justify-center">
              <div className="text-center mb-8 md:mb-12">
                <h1 className="text-gray-900 dark:text-white tracking-tight text-[32px] font-bold leading-tight pb-3">Những gì bạn có thể làm</h1>
                <p className="text-gray-600 dark:text-gray-300 text-base font-normal leading-normal max-w-md mx-auto">Tận hưởng chuyến đi của bạn với những tính năng độc đáo được thiết kế riêng.</p>
              </div>
              <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6 mb-12">
                <div className="bg-white dark:bg-gray-800/50 p-6 rounded-xl shadow-sm border border-gray-200 dark:border-gray-700 flex flex-col items-center text-center">
                  <div className="w-16 h-16 rounded-full bg-accent-blue dark:bg-accent-blue-dark/20 flex items-center justify-center mb-4">
                    <span className="material-symbols-outlined text-3xl text-primary dark:text-blue-300">place</span>
                  </div>
                  <h3 className="text-gray-900 dark:text-white font-bold text-lg mb-2">Gợi ý địa điểm</h3>
                  <p className="text-gray-600 dark:text-gray-300 text-sm">Tìm kiếm những địa điểm hấp dẫn và phù hợp với sở thích của bạn.</p>
                </div>
                <div className="bg-white dark:bg-gray-800/50 p-6 rounded-xl shadow-sm border border-gray-200 dark:border-gray-700 flex flex-col items-center text-center">
                  <div className="w-16 h-16 rounded-full bg-accent-orange dark:bg-accent-orange-dark/20 flex items-center justify-center mb-4">
                    <span className="material-symbols-outlined text-3xl text-accent-orange-dark dark:text-orange-300">restaurant</span>
                  </div>
                  <h3 className="text-gray-900 dark:text-white font-bold text-lg mb-2">Đặc sản</h3>
                  <p className="text-gray-600 dark:text-gray-300 text-sm">Khám phá ẩm thực địa phương và những món ăn không thể bỏ lỡ.</p>
                </div>
                <div className="bg-white dark:bg-gray-800/50 p-6 rounded-xl shadow-sm border border-gray-200 dark:border-gray-700 flex flex-col items-center text-center md:col-span-2 lg:col-span-1 md:max-w-sm md:mx-auto lg:max-w-none">
                  <div className="w-16 h-16 rounded-full bg-accent-blue dark:bg-accent-blue-dark/20 flex items-center justify-center mb-4">
                    <span className="material-symbols-outlined text-3xl text-primary dark:text-blue-300">route</span>
                  </div>
                  <h3 className="text-gray-900 dark:text-white font-bold text-lg mb-2">Lộ trình nhanh</h3>
                  <p className="text-gray-600 dark:text-gray-300 text-sm">Lên kế hoạch cho chuyến đi một cách nhanh chóng và hiệu quả.</p>
                </div>
              </div>
              <div className="flex px-4 py-3 justify-center">
                <Button onClick={onNext} className="w-full min-w-[84px] max-w-sm">Tiếp tục</Button>
              </div>
            </div>
          </div>
        </main>
      </div>
    </div>
  );
};
