import api from './api';

export interface Booking {
    id: number;
    userId: number;
    hotelId: number;
    roomId: number;
    checkIn: string;
    checkOut: string;
    totalPrice?: number;
    status: 'pending' | 'confirmed' | 'cancelled' | 'completed';
    createdAt: string;
    updatedAt: string;
    userName?: string;
    hotelName?: string;
    roomType?: string;
}

export const bookingApi = {
    getBookings: async (page: number = 1, pageSize: number = 20) => {
        const response = await api.get(`/api/bookings?page=${page}&pageSize=${pageSize}`);
        return response.data;
    },

    updateBooking: async (id: number, data: Partial<Booking>) => {
        const response = await api.put(`/api/bookings/${id}`, data);
        return response.data;
    },
};
