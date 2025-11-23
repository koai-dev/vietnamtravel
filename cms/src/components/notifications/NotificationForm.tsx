// @ts-ignore
import React, { useState, useEffect } from 'react';
import { notificationApi } from '../../services/notificationApi';
import { userApi } from '../../services/userApi';
import { X, Save } from 'lucide-react';

interface NotificationFormProps {
    onClose: () => void;
}

export const NotificationForm: React.FC<NotificationFormProps> = ({ onClose }) => {
    const [users, setUsers] = useState<Array<{ id: number; email: string; name?: string }>>([]);
    const [formData, setFormData] = useState({
        userId: 0,
        title: '',
        content: '',
        type: 'system' as 'booking' | 'system' | 'promotion',
    });

    const [loading, setLoading] = useState(false);

    useEffect(() => {
        loadUsers();
    }, []);

    const loadUsers = async () => {
        try {
            const response = await userApi.getUsers(1, 100);
            let data: Array<{ id: number; email: string; name?: string }> = [];
            if ('data' in response && 'pagination' in response) {
                data = response.data;
            } else if (Array.isArray(response)) {
                data = response;
            }
            setUsers(data);
            if (data.length > 0) {
                setFormData(prev => ({ ...prev, userId: data[0].id }));
            }
        } catch (error) {
            console.error('Error loading users:', error);
        }
    };

    const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement | HTMLSelectElement>) => {
        const { name, value } = e.target;
        setFormData(prev => ({
            ...prev,
            [name]: name === 'userId' ? Number(value) : value
        }));
    };

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        setLoading(true);

        try {
            await notificationApi.createNotification(formData.userId, {
                title: formData.title,
                content: formData.content,
                type: formData.type,
            });
            onClose();
        } catch (error) {
            console.error('Error creating notification:', error);
            alert('Có lỗi xảy ra khi tạo thông báo');
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="space-y-6">
            <div className="flex items-center justify-between">
                <div>
                    <div className="text-gray-900 text-2xl mb-2">Tạo thông báo mới</div>
                    <p className="text-gray-600">Gửi thông báo đến người dùng</p>
                </div>
                <button
                    onClick={onClose}
                    className="p-2 hover:bg-gray-100 rounded-lg transition-colors"
                >
                    <X className="w-6 h-6 text-gray-600" />
                </button>
            </div>

            <form onSubmit={handleSubmit} className="bg-white rounded-xl shadow-sm border border-gray-200 p-6">
                <div className="grid grid-cols-1 gap-6">
                    <div>
                        <label className="block text-sm text-gray-700 mb-2">Người nhận *</label>
                        <select
                            name="userId"
                            value={formData.userId}
                            onChange={handleChange}
                            className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                            required
                        >
                            <option value="">Chọn người dùng</option>
                            {users.map(user => (
                                <option key={user.id} value={user.id}>
                                    {user.name || user.email}
                                </option>
                            ))}
                        </select>
                    </div>

                    <div>
                        <label className="block text-sm text-gray-700 mb-2">Loại thông báo *</label>
                        <select
                            name="type"
                            value={formData.type}
                            onChange={handleChange}
                            className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                            required
                        >
                            <option value="system">Hệ thống</option>
                            <option value="booking">Đặt phòng</option>
                            <option value="promotion">Khuyến mãi</option>
                        </select>
                    </div>

                    <div>
                        <label className="block text-sm text-gray-700 mb-2">Tiêu đề *</label>
                        <input
                            type="text"
                            name="title"
                            value={formData.title}
                            onChange={handleChange}
                            className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                            required
                        />
                    </div>

                    <div>
                        <label className="block text-sm text-gray-700 mb-2">Nội dung *</label>
                        <textarea
                            name="content"
                            value={formData.content}
                            onChange={handleChange}
                            rows={4}
                            className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                            required
                        />
                    </div>
                </div>

                <div className="flex items-center gap-3 mt-6 pt-6 border-t border-gray-200">
                    <button
                        type="submit"
                        disabled={loading}
                        className="flex items-center gap-2 bg-blue-600 text-white px-6 py-2 rounded-lg hover:bg-blue-700 transition-colors disabled:opacity-50"
                    >
                        <Save className="w-5 h-5" />
                        <span>{loading ? 'Đang gửi...' : 'Tạo thông báo'}</span>
                    </button>
                    <button
                        type="button"
                        onClick={onClose}
                        className="px-6 py-2 border border-gray-300 rounded-lg hover:bg-gray-50 transition-colors"
                    >
                        Hủy
                    </button>
                </div>
            </form>
        </div>
    );
};
