'use client';

import React, { useState } from 'react';
import { OnboardingStep1 } from './onboarding-step-1';
import { OnboardingStep2 } from './onboarding-step-2';
import { OnboardingStep3 } from './onboarding-step-3';

export const Onboarding = ({ onComplete }: { onComplete: () => void }) => {
  const [step, setStep] = useState(1);

  const nextStep = () => setStep((prev) => prev + 1);

  if (step === 1) {
    return <OnboardingStep1 onNext={nextStep} onSkip={onComplete} />;
  }
  if (step === 2) {
    return <OnboardingStep2 onNext={nextStep} onSkip={onComplete} />;
  }
  if (step === 3) {
    return <OnboardingStep3 onAllow={onComplete} onSkip={onComplete} />;
  }

  return null;
};
