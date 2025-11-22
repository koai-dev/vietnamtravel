import api from './api';

export interface Tour {
    id: number;
    titleVi?: string;
    titleEn?: string;
    descriptionVi?: string;
    descriptionEn?: string;
    price?: number;
    durationHours?: number;
    destinationId?: number;
    images?: string[];
    destinationName?: string;
}

export const tourApi = {
    getTours: async (page: number = 1, pageSize: number = 20, lang: string = 'vi') => {
        const response = await api.get(`/api/tours?page=${page}&pageSize=${pageSize}&lang=${lang}`);
        return response.data;
    },

    getTour: async (id: number, lang: string = 'vi') => {
        const response = await api.get(`/api/tours/${id}?lang=${lang}`);
        return response.data;
    },

    createTour: async (data: any) => {
        const response = await api.post('/api/tours', data);
        return response.data;
    },

    updateTour: async (id: number, data: any) => {
        const response = await api.put(`/api/tours/${id}`, data);
        return response.data;
    },

    deleteTour: async (id: number) => {
        const response = await api.delete(`/api/tours/${id}`);
        return response.data;
    },
};
