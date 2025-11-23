import api from './api';

export interface Notification {
    id: number;
    userId: number;
    title: string;
    content: string;
    type: 'booking' | 'system' | 'promotion';
    isRead: boolean;
    createdAt: string;
}

export const notificationApi = {
    getNotifications: async (page: number = 1, pageSize: number = 20) => {
        const response = await api.get(`/api/notifications?page=${page}&pageSize=${pageSize}`);
        return response.data;
    },

    createNotification: async (userId: number, data: { title: string; content: string; type: 'booking' | 'system' | 'promotion' }) => {
        const response = await api.post(`/api/users/${userId}/notifications`, data);
        return response.data;
    },

    markAsRead: async (id: number) => {
        const response = await api.put(`/api/notifications/${id}/read`);
        return response.data;
    },

    deleteNotification: async (id: number) => {
        const response = await api.delete(`/api/notifications/${id}`);
        return response.data;
    },
};

