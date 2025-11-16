import { useQuery } from '@tanstack/react-query';
import axios from 'axios';

// Types
export interface TrackingDataPoint {
  timestamp: string;
  visits: number;
}

export interface TrackingSummary {
  range: string;
  data: TrackingDataPoint[];
}

// Types for New Users
export interface User {
    id: string;
    name: string;
    email: string;
    createdAt: string;
}

export interface NewUsersResponse {
    total: number;
    limit: number;
    offset: number;
    items: User[];
}

// API Hooks
const fetchTrackingSummary = async (range: string): Promise<TrackingSummary> => {
  const { data } = await axios.get(`/api/dashboard/tracking/summary?range=${range}`);
  return data;
};

export const useTrackingSummary = (range: string) => {
  return useQuery({
    queryKey: ['trackingSummary', range],
    queryFn: () => fetchTrackingSummary(range),
  });
};

const fetchNewUsers = async (limit: number, offset: number): Promise<NewUsersResponse> => {
    const { data } = await axios.get(`/api/dashboard/users/new?limit=${limit}&offset=${offset}`);
    return data;
};

export const useNewUsers = (limit: number, offset: number) => {
    return useQuery({
        queryKey: ['newUsers', limit, offset],
        queryFn: () => fetchNewUsers(limit, offset),
    });
}
