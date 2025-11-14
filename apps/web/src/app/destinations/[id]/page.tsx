'use client';

import React, { useEffect, useState } from 'react';

export default function DestinationDetailPage({ params }: { params: { id: string } }) {
  const [destination, setDestination] = useState<any>(null);

  useEffect(() => {
    fetch(`http://localhost:8080/api/destinations/${params.id}`)
      .then((response) => response.json())
      .then((data) => setDestination(data));
  }, [params.id]);

  if (!destination) {
    return <div>Loading...</div>;
  }

  return (
    <div className="bg-background-light dark:bg-background-dark font-display">
      <div className="relative flex h-auto min-h-screen w-full flex-col">
        <header className="sticky top-0 z-50 flex items-center justify-center border-b border-solid border-white/10 bg-background-light/80 px-4 py-3 backdrop-blur-sm dark:bg-background-dark/80">
          <div className="flex w-full max-w-6xl items-center justify-between">
            <div className="flex items-center gap-8">
              <div className="flex items-center gap-3 text-slate-800 dark:text-white">
                <div className="size-6 text-primary">
                  <svg fill="none" viewBox="0 0 48 48" xmlns="http://www.w3.org/2000/svg">
                    <path clip-rule="evenodd" d="M39.475 21.6262C40.358 21.4363 40.6863 21.5589 40.7581 21.5934C40.7876 21.655 40.8547 21.857 40.8082 22.3336C40.7408 23.0255 40.4502 24.0046 39.8572 25.2301C38.6799 27.6631 36.5085 30.6631 33.5858 33.5858C30.6631 36.5085 27.6632 38.6799 25.2301 39.8572C24.0046 40.4502 23.0255 40.7407 22.3336 40.8082C21.8571 40.8547 21.6551 40.7875 21.5934 40.7581C21.5589 40.6863 21.4363 40.358 21.6262 39.475C21.8562 38.4054 22.4689 36.9657 23.5038 35.2817C24.7575 33.2417 26.5497 30.9744 28.7621 28.762C30.9744 26.5497 33.2417 24.7574 35.2817 23.5037C36.9657 22.4689 38.4054 21.8562 39.475 21.6262ZM4.41189 29.2403L18.7597 43.5881C19.8813 44.7097 21.4027 44.9179 22.7217 44.7893C24.0585 44.659 25.5148 44.1631 26.9723 43.4579C29.9052 42.0387 33.2618 39.5667 36.4142 36.4142C39.5667 33.2618 42.0387 29.9052 43.4579 26.9723C44.1631 25.5148 44.659 24.0585 44.7893 22.7217C44.9179 21.4027 44.7097 19.8813 43.5881 18.7597L29.2403 4.41187C27.8527 3.02428 25.8765 3.02573 24.2861 3.36776C22.6081 3.72863 20.7334 4.58419 18.8396 5.74801C16.4978 7.18716 13.9881 9.18353 11.5858 11.5858C9.18354 13.988 7.18717 16.4978 5.74802 18.8396C4.58421 20.7334 3.72865 22.6081 3.36778 24.2861C3.02574 25.8765 3.02429 27.8527 4.41189 29.2403Z" fill="currentColor" fill-rule="evenodd"></path>
                  </svg>
                </div>
                <h2 className="text-lg font-bold leading-tight tracking-[-0.015em]">Vietnam Travel</h2>
              </div>
              <nav className="hidden items-center gap-8 md:flex">
                <a className="text-sm font-medium text-slate-600 hover:text-primary dark:text-slate-300 dark:hover:text-primary" href="#">Home</a>
                <a className="text-sm font-bold text-primary" href="#">Destinations</a>
                <a className="text-sm font-medium text-slate-600 hover:text-primary dark:text-slate-300 dark:hover:text-primary" href="#">Blog</a>
              </nav>
            </div>
            <div className="flex flex-1 items-center justify-end">
              <button className="flex h-10 w-10 cursor-pointer items-center justify-center overflow-hidden rounded-full bg-slate-200 text-slate-600 hover:bg-slate-300 dark:bg-slate-800 dark:text-slate-300 dark:hover:bg-slate-700">
                <span className="material-symbols-outlined text-xl">search</span>
              </button>
            </div>
          </div>
        </header>
        <main className="flex w-full flex-1 flex-col items-center px-4 py-10 sm:px-6 md:py-16">
          <div className="w-full max-w-6xl">
            <div className="flex flex-wrap justify-between gap-3 px-4 py-4">
              <h1 className="text-3xl font-black leading-tight tracking-[-0.033em] text-slate-800 dark:text-white md:text-4xl">{destination.name}</h1>
            </div>
            <section className="mt-8">
              <div className="group relative overflow-hidden rounded-xl">
                <div className="bg-cover bg-center flex h-full min-h-[320px] flex-col items-stretch justify-end transition-transform duration-500 ease-in-out group-hover:scale-105" style={{ backgroundImage: `url(${destination.imageUrl})` }}>
                  <div className="flex w-full flex-col gap-4 p-6">
                    <div className="flex items-center"><span className="rounded-full bg-[#FFA07A]/20 px-3 py-1 text-xs font-bold text-[#FFA07A] backdrop-blur-sm">{destination.season}</span></div>
                    <div className="flex flex-col gap-1">
                      <h3 className="text-2xl font-bold leading-tight text-white">{destination.name}</h3>
                      <p className="text-base font-medium leading-normal text-slate-200">{destination.description}</p>
                    </div>
                  </div>
                </div>
              </div>
            </section>
          </div>
        </main>
      </div>
    </div>
  );
}
