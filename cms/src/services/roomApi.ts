import api from './api';

export interface Room {
    id: number;
    hotelId: number;
    roomTypeVi?: string;
    roomTypeEn?: string;
    maxGuest?: number;
    pricePerNight?: number;
    totalRooms?: number;
    availableRooms?: number;
    amenities?: string[];
}

export const roomApi = {
    getRooms: async (page: number = 1, pageSize: number = 20) => {
        const response = await api.get(`/api/rooms?page=${page}&pageSize=${pageSize}`);
        return response.data;
    },

    getRoomsByHotel: async (hotelId: number, page: number = 1, pageSize: number = 20) => {
        const response = await api.get(`/api/hotels/${hotelId}/rooms?page=${page}&pageSize=${pageSize}`);
        return response.data;
    },

    getRoom: async (id: number) => {
        const response = await api.get(`/api/rooms/${id}`);
        return response.data;
    },

    createRoom: async (data: {
        hotelId: number;
        roomTypeVi?: string;
        roomTypeEn?: string;
        maxGuest?: number;
        pricePerNight?: number;
        totalRooms?: number;
        availableRooms?: number;
        amenities?: string[];
    }) => {
        const response = await api.post('/api/rooms', data);
        return response.data;
    },

    updateRoom: async (id: number, data: {
        roomTypeVi?: string;
        roomTypeEn?: string;
        maxGuest?: number;
        pricePerNight?: number;
        totalRooms?: number;
        availableRooms?: number;
        amenities?: string[];
    }) => {
        const response = await api.put(`/api/rooms/${id}`, data);
        return response.data;
    },

    deleteRoom: async (id: number) => {
        const response = await api.delete(`/api/rooms/${id}`);
        return response.data;
    },
};
