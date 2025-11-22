import api from './api';

export interface Review {
    id: number;
    userId?: number;
    hotelId: number;
    rating?: number;
    comment?: string;
    createdAt: string;
    userName?: string;
    hotelName?: string;
}

export const reviewApi = {
    getReviews: async (page: number = 1, pageSize: number = 20) => {
        const response = await api.get(`/api/reviews?page=${page}&pageSize=${pageSize}`);
        return response.data;
    },

    getReviewsByHotel: async (hotelId: number, page: number = 1, pageSize: number = 20) => {
        const response = await api.get(`/api/hotels/${hotelId}/reviews?page=${page}&pageSize=${pageSize}`);
        return response.data;
    },

    getReview: async (id: number) => {
        const response = await api.get(`/api/reviews/${id}`);
        return response.data;
    },

    createReview: async (data: {
        userId?: number;
        hotelId: number;
        rating?: number;
        comment?: string;
    }) => {
        const response = await api.post('/api/reviews', data);
        return response.data;
    },

    updateReview: async (id: number, data: {
        rating?: number;
        comment?: string;
    }) => {
        const response = await api.put(`/api/reviews/${id}`, data);
        return response.data;
    },

    deleteReview: async (id: number) => {
        const response = await api.delete(`/api/reviews/${id}`);
        return response.data;
    },
};
