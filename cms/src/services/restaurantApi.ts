import api from './api';
import { LocalFood } from './localFoodApi';

export interface Restaurant {
    id: number;
    name: string;
    description: string;
    images: string[];
    address: string;
    latitude: number;
    longitude: number;
    destinationId: number;
    localFoods?: LocalFood[];
    destinationName?: string;
}

export const restaurantApi = {
    getRestaurants: async (page: number = 1, pageSize: number = 20) => {
        const response = await api.get(`/api/restaurants?page=${page}&pageSize=${pageSize}`);
        return response.data;
    },

    getRestaurant: async (id: number) => {
        const response = await api.get(`/api/restaurants/${id}`);
        return response.data;
    },

    createRestaurant: async (data: any) => {
        const response = await api.post('/api/restaurants', data);
        return response.data;
    },

    updateRestaurant: async (id: number, data: any) => {
        const response = await api.put(`/api/restaurants/${id}`, data);
        return response.data;
    },

    deleteRestaurant: async (id: number) => {
        const response = await api.delete(`/api/restaurants/${id}`);
        return response.data;
    },
};
