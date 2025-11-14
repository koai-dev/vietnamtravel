'use client';
import { HomePage } from "@/components/home";
import { Onboarding } from "@/components/onboarding";
import { useState, useEffect } from "react";

export default function Home() {
  const [showOnboarding, setShowOnboarding] = useState(false);

  useEffect(() => {
    const hasSeenOnboarding = localStorage.getItem("hasSeenOnboarding");
    if (!hasSeenOnboarding) {
      setShowOnboarding(true);
    }
  }, []);

  const handleOnboardingComplete = () => {
    localStorage.setItem("hasSeenOnboarding", "true");
    setShowOnboarding(false);
  };

  return (
    <main>
      {showOnboarding ? <Onboarding onComplete={handleOnboardingComplete} /> : <HomePage />}
    </main>
  );
}
