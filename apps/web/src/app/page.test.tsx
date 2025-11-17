import { render } from '@testing-library/react';
import Page from './page';

jest.mock('next/navigation', () => ({
  redirect: jest.fn(),
}));

describe('Page', () => {
  it('should redirect to /onboarding1', () => {
    const { redirect } = require('next/navigation');
    render(<Page />);
    expect(redirect).toHaveBeenCalledWith('/onboarding1');
  });
});
