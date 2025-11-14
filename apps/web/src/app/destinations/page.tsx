'use client';

import React, { useEffect, useState } from 'react';
import { Button } from '@/components/ui/button';

export default function DestinationsPage() {
  const [destinations, setDestinations] = useState([]);

  useEffect(() => {
    fetch('http://localhost:8080/api/destinations')
      .then((response) => response.json())
      .then((data) => setDestinations(data));
  }, []);

  return (
    <div className="font-display bg-background-light dark:bg-background-dark text-text-light dark:text-text-dark">
      <div className="relative flex h-auto min-h-screen w-full flex-col group/design-root overflow-x-hidden">
        <div className="layout-container flex h-full grow flex-col">
          <header className="sticky top-0 z-50 bg-background-light/80 dark:bg-background-dark/80 backdrop-blur-sm border-b border-gray-200 dark:border-gray-700">
            <div className="mx-auto max-w-7xl px-4 sm:px-6 lg:px-8">
              <div className="flex items-center justify-between whitespace-nowrap h-16">
                <div className="flex items-center gap-8">
                  <div className="flex items-center gap-3 text-text-light dark:text-text-dark">
                    <span className="material-symbols-outlined text-primary text-3xl">explore</span>
                    <h2 className="text-xl font-bold leading-tight tracking-[-0.015em]">Explore Vietnam</h2>
                  </div>
                  <nav className="hidden md:flex items-center gap-8">
                    <a className="text-sm font-medium leading-normal hover:text-primary dark:hover:text-primary" href="#">Home</a>
                    <a className="text-sm font-medium leading-normal text-primary" href="#">Destinations</a>
                    <a className="text-sm font-medium leading-normal hover:text-primary dark:hover:text-primary" href="#">Blog</a>
                  </nav>
                </div>
                <div className="flex flex-1 justify-end items-center gap-4">
                  <label className="hidden sm:flex flex-col min-w-40 !h-10 max-w-64">
                    <div className="flex w-full flex-1 items-stretch rounded-lg h-full">
                      <div className="text-gray-500 dark:text-gray-400 flex bg-gray-200 dark:bg-card-dark items-center justify-center pl-3 rounded-l-lg border-r-0">
                        <span className="material-symbols-outlined">search</span>
                      </div>
                      <input className="form-input flex w-full min-w-0 flex-1 resize-none overflow-hidden rounded-r-lg text-text-light dark:text-text-dark focus:outline-0 focus:ring-0 border-none bg-gray-200 dark:bg-card-dark focus:border-none h-full placeholder:text-gray-500 dark:placeholder:text-gray-400 pl-2 text-base font-normal leading-normal" placeholder="Search" value="" />
                    </div>
                  </label>
                  <button className="flex md:hidden min-w-[40px] max-w-[40px] h-10 cursor-pointer items-center justify-center overflow-hidden rounded-lg bg-gray-200 dark:bg-card-dark text-text-light dark:text-text-dark">
                    <span className="material-symbols-outlined">search</span>
                  </button>
                  <button className="flex md:hidden min-w-[40px] max-w-[40px] h-10 cursor-pointer items-center justify-center overflow-hidden rounded-lg bg-gray-200 dark:bg-card-dark text-text-light dark:text-text-dark">
                    <span className="material-symbols-outlined">menu</span>
                  </button>
                </div>
              </div>
            </div>
          </header>
          <main className="mx-auto w-full max-w-7xl px-4 sm:px-6 lg:px-8 py-8 md:py-12">
            <div className="flex flex-col gap-8">
              <div className="flex flex-wrap justify-between items-start gap-4">
                <div className="flex flex-col gap-2">
                  <p className="text-4xl font-black leading-tight tracking-[-0.033em] text-text-light dark:text-text-dark">Destinations</p>
                  <p className="text-base font-normal leading-normal text-gray-600 dark:text-gray-400">Discover the beauty and culture of Vietnam, from bustling cities to serene landscapes.</p>
                </div>
              </div>
              <div className="flex flex-wrap gap-3">
                <Button>All Regions</Button>
                <Button variant="outline">North Vietnam</Button>
                <Button variant="outline">Central Vietnam</Button>
                <Button variant="outline">South Vietnam</Button>
              </div>
              <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6 md:gap-8">
                {destinations.map((destination: any) => (
                  <div key={destination.id} className="group flex flex-col overflow-hidden rounded-xl bg-card-light dark:bg-card-dark shadow-md transition-all duration-300 hover:shadow-xl hover:-translate-y-1">
                    <div className="relative">
                      <img className="aspect-[4/3] w-full object-cover" src={destination.imageUrl} />
                      <div className="absolute inset-0 bg-gradient-to-t from-black/60 via-black/20 to-transparent"></div>
                      <div className="absolute top-4 left-4">
                        <span className="inline-block rounded-full bg-accent px-3 py-1 text-xs font-semibold text-text-light">{destination.region}</span>
                      </div>
                    </div>
                    <div className="p-6 flex flex-col flex-grow">
                      <h3 className="text-2xl font-bold tracking-tight text-text-light dark:text-text-dark">{destination.name}</h3>
                      <p className="mt-2 text-base font-normal leading-normal text-gray-600 dark:text-gray-400 flex-grow">{destination.description}</p>
                      <a className="mt-4 flex items-center font-bold text-primary group-hover:underline" href={`/destinations/${destination.id}`}>
                        Read More <span className="material-symbols-outlined ml-1 transition-transform duration-300 group-hover:translate-x-1">arrow_forward</span>
                      </a>
                    </div>
                  </div>
                ))}
              </div>
            </div>
          </main>
        </div>
      </div>
    </div>
  );
}
