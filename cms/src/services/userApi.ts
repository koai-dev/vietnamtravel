import api from './api';

export interface User {
    id: number;
    email: string;
    name?: string;
    avatarUrl?: string;
    phone?: string;
    role: 'admin' | 'user';
    status: 'active' | 'blocked';
    createdAt: string;
    lastLogin?: string;
}

export const userApi = {
    getUsers: async (page: number = 1, pageSize: number = 20) => {
        const response = await api.get(`/api/users?page=${page}&pageSize=${pageSize}`);
        return response.data;
    },

    updateUser: async (id: number, data: Partial<User>) => {
        const response = await api.put(`/api/users/${id}`, data);
        return response.data;
    },

    deleteUser: async (id: number) => {
        const response = await api.delete(`/api/users/${id}`);
        return response.data;
    },
};
