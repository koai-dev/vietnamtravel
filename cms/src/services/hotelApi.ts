import api from './api';

export interface Hotel {
    id: number;
    nameVi: string;
    nameEn: string;
    slug: string;
    descriptionVi?: string;
    descriptionEn?: string;
    address?: string;
    city?: string;
    latitude?: number;
    longitude?: number;
    addressLink?: string;
    contact?: { phone?: string; email?: string; website?: string };
    images?: string[];
    minPrice?: number;
    maxPrice?: number;
    amenities?: string[];
    checkInTime?: string;
    checkOutTime?: string;
    cancellationPolicy?: string;
    childPolicy?: string;
    petPolicy?: string;
    tags?: string[];
    externalBookingLinks?: string[];
    rating: number;
    reviewCount: number;
    viewsCount: number;
    favoritesCount: number;
    hostId?: number;
    createdAt: string;
    updatedAt: string;
}

export const hotelApi = {
    getHotels: async (page: number = 1, pageSize: number = 20, lang: string = 'vi') => {
        const response = await api.get(`/api/hotels?page=${page}&pageSize=${pageSize}&lang=${lang}`);
        return response.data.data;
    },

    getHotel: async (id: number, lang: string = 'vi') => {
        const response = await api.get(`/api/hotels/${id}?lang=${lang}`);
        return response.data;
    },

    createHotel: async (data: any) => {
        const response = await api.post('/api/hotels', data);
        return response.data;
    },

    updateHotel: async (id: number, data: any) => {
        const response = await api.put(`/api/hotels/${id}`, data);
        return response.data;
    },

    deleteHotel: async (id: number) => {
        const response = await api.delete(`/api/hotels/${id}`);
        return response.data;
    },
};
