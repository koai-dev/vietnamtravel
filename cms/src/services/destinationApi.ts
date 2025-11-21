import api from './api';

export interface Destination {
    id: number;
    nameVi: string;
    nameEn: string;
    descriptionVi?: string;
    descriptionEn?: string;
    city?: string;
    type: 'region' | 'city' | 'attraction' | 'spot';
    images: string[];
    avgRating: number;
    reviewCount: number;
    viewsCount: number;
    status: 'ACTIVE' | 'INACTIVE' | 'DRAFT';
    parentId?: number | null;
}

export interface DestinationDetail extends Destination {
    address?: string;
    latitude?: number;
    longitude?: number;
    slug?: string;
    tags?: string[];
    bestTimeToVisit?: string;
    openingHours?: string;
    priceFrom?: number;
    priceTo?: number;
    sortOrder: number;
    favoritesCount: number;
    externalLinks?: string[];
    addressLink?: string;
}

export const destinationApi = {
    getDestinations: async (lang: string = 'vi') => {
        const response = await api.get(`/api/destinations?lang=${lang}`);
        return response.data;
    },

    getDestination: async (id: number, lang: string = 'vi') => {
        const response = await api.get(`/api/destinations/${id}?lang=${lang}`);
        return response.data;
    },

    getDestinationDetail: async (id: number, lang: string = 'vi') => {
        const response = await api.get(`/api/destinations/${id}/detail?lang=${lang}`);
        return response.data;
    },

    searchDestinations: async (query: string, types?: string[], lang: string = 'vi') => {
        const typesParam = types ? `&types=${types.join(',')}` : '';
        const response = await api.get(`/api/destinations/search?q=${query}${typesParam}&lang=${lang}`);
        return response.data;
    },

    createDestination: async (data: any) => {
        const response = await api.post('/api/destinations', data);
        return response.data;
    },

    updateDestination: async (id: number, data: any) => {
        const response = await api.put(`/api/destinations/${id}`, data);
        return response.data;
    },

    deleteDestination: async (id: number) => {
        const response = await api.delete(`/api/destinations/${id}`);
        return response.data;
    },
};
