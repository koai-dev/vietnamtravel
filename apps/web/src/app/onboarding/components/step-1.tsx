"use client";

import { Button } from "@/components/ui/button";
import { Card, CardContent } from "@/components/ui/card";

interface Step1Props {
  onNext: () => void;
  onSkip: () => void;
}

const features = [
  {
    icon: "place",
    title: "Gợi ý địa điểm",
    description: "Tìm kiếm những địa điểm hấp dẫn và phù hợp với sở thích của bạn.",
    iconBgColor: "bg-blue-100 dark:bg-blue-900/30",
    iconTextColor: "text-blue-600 dark:text-blue-300",
  },
  {
    icon: "restaurant",
    title: "Đặc sản",
    description: "Khám phá ẩm thực địa phương và những món ăn không thể bỏ lỡ.",
    iconBgColor: "bg-orange-100 dark:bg-orange-900/30",
    iconTextColor: "text-orange-600 dark:text-orange-300",
  },
  {
    icon: "route",
    title: "Lộ trình nhanh",
    description: "Lên kế hoạch cho chuyến đi một cách nhanh chóng và hiệu quả.",
    iconBgColor: "bg-blue-100 dark:bg-blue-900/30",
    iconTextColor: "text-blue-600 dark:text-blue-300",
  },
];

export default function Step1({ onNext, onSkip }: Step1Props) {
  return (
    <main className="flex h-full flex-1 flex-col items-center justify-center p-6">
      <div className="absolute top-5 right-5 z-10 md:right-10">
        <Button variant="link" onClick={onSkip} className="text-gray-500 dark:text-gray-400">
          Bỏ qua
        </Button>
      </div>
      <div className="flex w-full max-w-5xl flex-1 flex-col justify-center">
        <div className="mb-8 text-center md:mb-12">
          <h1 className="pb-3 text-3xl font-bold tracking-tight text-gray-900 dark:text-white md:text-4xl">
            Những gì bạn có thể làm
          </h1>
          <p className="mx-auto max-w-md text-base text-gray-600 dark:text-gray-300">
            Tận hưởng chuyến đi của bạn với những tính năng độc đáo được thiết
            kế riêng.
          </p>
        </div>
        <div className="mb-12 grid grid-cols-1 gap-6 md:grid-cols-2 lg:grid-cols-3">
          {features.map((feature, index) => (
            <Card
              key={index}
              className="flex flex-col items-center border-gray-200 bg-white p-6 text-center shadow-sm dark:border-gray-700 dark:bg-gray-800/50 md:col-span-1 lg:col-span-1"
            >
              <CardContent className="flex flex-col items-center p-0">
                <div
                  className={`mb-4 flex h-16 w-16 items-center justify-center rounded-full ${feature.iconBgColor}`}
                >
                  <span
                    className={`material-symbols-outlined text-3xl ${feature.iconTextColor}`}
                  >
                    {feature.icon}
                  </span>
                </div>
                <h3 className="mb-2 text-lg font-bold text-gray-900 dark:text-white">
                  {feature.title}
                </h3>
                <p className="text-sm text-gray-600 dark:text-gray-300">
                  {feature.description}
                </p>
              </CardContent>
            </Card>
          ))}
        </div>
        <div className="flex justify-center px-4 py-3">
          <Button onClick={onNext} size="lg" className="w-full max-w-sm">
            Tiếp tục
          </Button>
        </div>
      </div>
    </main>
  );
}
