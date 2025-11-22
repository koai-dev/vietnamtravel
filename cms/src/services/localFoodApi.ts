import api from './api';

export interface LocalFood {
    id: number;
    nameVi: string;
    nameEn?: string;
    descriptionVi?: string;
    descriptionEn?: string;
    destinationId: number;
    images: string[];
    destinationName?: string;
}

export const localFoodApi = {
    getLocalFoods: async (page: number = 1, pageSize: number = 20) => {
        const response = await api.get(`/api/local-foods?page=${page}&pageSize=${pageSize}`);
        return response.data;
    },

    getLocalFood: async (id: number) => {
        const response = await api.get(`/api/local-foods/${id}`);
        return response.data;
    },

    createLocalFood: async (data: any) => {
        const response = await api.post('/api/local-foods', data);
        return response.data;
    },

    updateLocalFood: async (id: number, data: any) => {
        const response = await api.put(`/api/local-foods/${id}`, data);
        return response.data;
    },

    deleteLocalFood: async (id: number) => {
        const response = await api.delete(`/api/local-foods/${id}`);
        return response.data;
    },
};
