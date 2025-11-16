import React from 'react';
import { render, screen } from '@testing-library/react';
import { TrackingChart } from '@/components/dashboard/TrackingChart';
import { useTrackingSummary } from '@/hooks/useDashboard';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';

jest.mock('@/hooks/useDashboard');

const useTrackingSummaryMock = useTrackingSummary as jest.Mock;

const queryClient = new QueryClient();

const wrapper = ({ children }: { children: React.ReactNode }) => (
    <QueryClientProvider client={queryClient}>{children}</QueryClientProvider>
);

describe('TrackingChart', () => {
    it('should display loading state', () => {
        useTrackingSummaryMock.mockReturnValue({ isLoading: true });
        render(<TrackingChart />, { wrapper });
        expect(screen.getByRole('progressbar')).toBeInTheDocument();
    });

    it('should display error state', () => {
        useTrackingSummaryMock.mockReturnValue({ isError: true });
        render(<TrackingChart />, { wrapper });
        expect(screen.getByText('Failed to load tracking data.')).toBeInTheDocument();
    });

    it('should display the chart', () => {
        useTrackingSummaryMock.mockReturnValue({
            data: {
                range: 'day',
                data: [
                    { timestamp: '2025-01-01', visits: 100 },
                    { timestamp: '2025-01-02', visits: 200 },
                ],
            },
        });
        render(<TrackingChart />, { wrapper });
        expect(screen.getByText('100')).toBeInTheDocument();
        expect(screen.getByText('200')).toBeInTheDocument();
    });
});
