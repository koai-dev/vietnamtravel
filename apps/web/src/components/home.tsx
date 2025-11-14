'use client';

import React, { useEffect, useState } from 'react';

export const HomePage = () => {
  const [destinations, setDestinations] = useState([]);

  useEffect(() => {
    fetch('http://localhost:8080/api/destinations')
      .then((response) => response.json())
      .then((data) => setDestinations(data));
  }, []);

  return (
    <div className="bg-background-light dark:bg-background-dark font-display text-text-primary-light dark:text-text-primary-dark">
      <div className="relative flex h-auto min-h-screen w-full flex-col group/design-root overflow-x-hidden">
        <div className="layout-container flex h-full grow flex-col">
          <div className="flex flex-1 justify-center py-5 sm:px-10 lg:px-20 xl:px-40">
            <div className="layout-content-container flex flex-col w-full max-w-7xl flex-1">
              <header className="flex items-center justify-between whitespace-nowrap border-b border-solid border-gray-200 dark:border-gray-700 px-4 sm:px-10 py-4">
                <div className="flex items-center gap-3">
                  <div className="size-8 text-primary">
                    <svg fill="none" viewBox="0 0 48 48" xmlns="http://www.w3.org/2000/svg">
                      <path d="M8.57829 8.57829C5.52816 11.6284 3.451 15.5145 2.60947 19.7452C1.76794 23.9758 2.19984 28.361 3.85056 32.3462C5.50128 36.3314 8.29667 39.7376 11.8832 42.134C15.4698 44.5305 19.6865 45.8096 24 45.8096C28.3135 45.8096 32.5302 44.5305 36.1168 42.134C39.7033 39.7375 42.4987 36.3314 44.1494 32.3462C45.8002 28.361 46.2321 23.9758 45.3905 19.7452C44.549 15.5145 42.4718 11.6284 39.4217 8.57829L24 24L8.57829 8.57829Z" fill="currentColor"></path>
                    </svg>
                  </div>
                  <h2 className="text-text-primary-light dark:text-text-primary-dark text-xl font-bold leading-tight tracking-[-0.015em]">Vietnam Travel</h2>
                </div>
                <div className="hidden md:flex flex-1 justify-end gap-8">
                  <div className="flex items-center gap-9">
                    <a className="text-text-primary-light dark:text-text-primary-dark text-sm font-medium leading-normal hover:text-primary dark:hover:text-primary" href="#">Destinations</a>
                    <a className="text-text-secondary-light dark:text-text-secondary-dark text-sm font-medium leading-normal hover:text-primary dark:hover:text-primary" href="#">Blog</a>
                    <a className="text-text-secondary-light dark:text-text-secondary-dark text-sm font-medium leading-normal hover:text-primary dark:hover:text-primary" href="#">About</a>
                  </div>
                  <div className="flex gap-2">
                    <button className="flex max-w-[480px] cursor-pointer items-center justify-center overflow-hidden rounded-full h-10 w-10 bg-card-light dark:bg-card-dark text-text-primary-light dark:text-text-primary-dark">
                      <span className="material-symbols-outlined text-xl">language</span>
                    </button>
                    <button className="flex max-w-[480px] cursor-pointer items-center justify-center overflow-hidden rounded-full h-10 w-10 bg-card-light dark:bg-card-dark text-text-primary-light dark:text-text-primary-dark">
                      <span className="material-symbols-outlined text-xl">account_circle</span>
                    </button>
                  </div>
                </div>
                <button className="md:hidden flex items-center justify-center rounded-full h-10 w-10 bg-card-light dark:bg-card-dark text-text-primary-light dark:text-text-primary-dark">
                  <span className="material-symbols-outlined text-2xl">menu</span>
                </button>
              </header>
              <main className="flex flex-col gap-8 sm:gap-10 md:gap-12 p-4 sm:p-6 md:p-8">
                <div className="flex flex-wrap justify-center gap-4 text-center">
                  <div className="flex w-full flex-col gap-4">
                    <h1 className="text-text-primary-light dark:text-text-primary-dark text-4xl sm:text-5xl font-black leading-tight tracking-[-0.033em]">Welcome to Vietnam</h1>
                    <p className="text-text-secondary-light dark:text-text-secondary-dark text-lg font-normal leading-normal">Discover your next adventure in this beautiful country.</p>
                  </div>
                </div>
                <div className="px-4 py-3 max-w-2xl mx-auto w-full">
                  <label className="flex flex-col min-w-40 h-14 w-full">
                    <div className="flex w-full flex-1 items-stretch rounded-full h-full shadow-sm">
                      <div className="text-text-secondary-light dark:text-text-secondary-dark flex bg-input-light dark:bg-input-dark items-center justify-center pl-5 rounded-l-full border-r-0">
                        <span className="material-symbols-outlined">search</span>
                      </div>
                      <input className="form-input flex w-full min-w-0 flex-1 resize-none overflow-hidden rounded-full text-text-primary-light dark:text-text-primary-dark focus:outline-0 focus:ring-2 focus:ring-primary/50 border-none bg-input-light dark:bg-input-dark h-full placeholder:text-text-secondary-light dark:placeholder:text-text-secondary-dark px-4 rounded-l-none border-l-0 pl-2 text-base font-normal leading-normal" placeholder="Tìm kiếm tỉnh thành, địa điểm..." value="" />
                    </div>
                  </label>
                </div>
                <section className="flex flex-col gap-4">
                  <h2 className="text-text-primary-light dark:text-text-primary-dark text-2xl font-bold leading-tight tracking-[-0.015em] px-4">Tỉnh nổi bật</h2>
                  <div className="relative">
                    <div className="flex overflow-x-auto [-ms-scrollbar-style:none] [scrollbar-width:none] [&::-webkit-scrollbar]:hidden px-4 py-2 -mx-4 gap-4 sm:gap-6">
                      {destinations.map((destination: any) => (
                        <div key={destination.id} className="flex h-full flex-col rounded-xl bg-card-light dark:bg-card-dark shadow-md min-w-[240px] sm:min-w-[280px] overflow-hidden transition-transform duration-300 hover:-translate-y-1">
                          <div className="w-full bg-center bg-no-repeat aspect-video bg-cover flex flex-col" style={{ backgroundImage: `url(${destination.imageUrl})` }}></div>
                          <div className="flex flex-col flex-1 justify-between p-4 gap-3">
                            <div>
                              <p className="text-text-primary-light dark:text-text-primary-dark text-lg font-bold leading-normal">{destination.name}</p>
                              <p className="text-text-secondary-light dark:text-text-secondary-dark text-sm font-normal leading-normal">{destination.description}</p>
                            </div>
                            <button className="flex min-w-[84px] max-w-[480px] cursor-pointer items-center justify-center overflow-hidden rounded-lg h-10 px-4 bg-primary/10 text-primary text-sm font-bold leading-normal tracking-[0.015em] hover:bg-primary/20">
                              <span className="truncate">Explore</span>
                            </button>
                          </div>
                        </div>
                      ))}
                    </div>
                  </div>
                </section>
              </main>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};
