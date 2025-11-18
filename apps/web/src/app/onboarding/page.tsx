"use client";

import { useEffect, useState } from "react";
import { useRouter } from "next/navigation";
import Step1 from "./components/step-1";
import Step2 from "./components/step-2";

const ONBOARDING_KEY = "onboarding_completed";

export default function OnboardingPage() {
  const [step, setStep] = useState(1);
  const router = useRouter();

  useEffect(() => {
    // Check if the user has already completed the onboarding
    const hasCompletedOnboarding = localStorage.getItem(ONBOARDING_KEY);
    if (hasCompletedOnboarding === "true") {
      router.replace("/"); // Redirect to homepage if onboarding is done
    }
  }, [router]);

  const handleNext = () => {
    setStep((prev) => prev + 1);
  };

  const handleComplete = () => {
    // Mark onboarding as completed and redirect
    localStorage.setItem(ONBOARDING_KEY, "true");
    router.replace("/");
  };

  return (
    <div className="relative flex min-h-screen w-full flex-col bg-background-light dark:bg-background-dark">
      {step === 1 && <Step1 onNext={handleNext} onSkip={handleComplete} />}
      {step === 2 && <Step2 onComplete={handleComplete} onSkip={handleComplete} />}
    </div>
  );
}
