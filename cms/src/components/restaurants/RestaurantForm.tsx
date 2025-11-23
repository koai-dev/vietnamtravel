// @ts-ignore
import React, { useState, useEffect } from 'react';
import { restaurantApi, Restaurant } from '../../services/restaurantApi';
import { destinationApi } from '../../services/destinationApi';
import { X, Save } from 'lucide-react';
import { ImageInput } from '../common/ImageInput';

interface Destination {
    id: number;
    nameVi: string;
}

interface RestaurantFormProps {
    restaurant: Restaurant | null;
    onClose: () => void;
}

export const RestaurantForm: React.FC<RestaurantFormProps> = ({ restaurant, onClose }) => {
    const [destinations, setDestinations] = useState<Destination[]>([]);
    const [formData, setFormData] = useState({
        name: restaurant?.name || '',
        description: restaurant?.description || '',
        address: restaurant?.address || '',
        latitude: restaurant?.latitude || 0,
        longitude: restaurant?.longitude || 0,
        destinationId: restaurant?.destinationId || 0,
        images: restaurant?.images || [],
    });

    const [loading, setLoading] = useState(false);

    useEffect(() => {
        loadDestinations();
    }, []);

    const loadDestinations = async () => {
        try {
            const response = await destinationApi.getDestinations(1, 100);
            let data: Destination[] = [];
            if ('data' in response && 'pagination' in response) {
                data = response.data;
            } else if (Array.isArray(response)) {
                data = response;
            }
            setDestinations(data);
            if (!restaurant && data.length > 0) {
                setFormData(prev => ({ ...prev, destinationId: data[0].id }));
            }
        } catch (error) {
            console.error('Error loading destinations:', error);
        }
    };

    const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement | HTMLSelectElement>) => {
        const { name, value } = e.target;
        setFormData(prev => ({
            ...prev,
            [name]: name.includes('Id') || name === 'latitude' || name === 'longitude'
                ? Number(value)
                : value
        }));
    };

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        setLoading(true);

        try {
            const data = {
                name: formData.name,
                description: formData.description,
                address: formData.address,
                latitude: formData.latitude,
                longitude: formData.longitude,
                destinationId: formData.destinationId,
                images: formData.images.length > 0 ? formData.images : [],
            };

            if (restaurant) {
                await restaurantApi.updateRestaurant(restaurant.id, data);
            } else {
                await restaurantApi.createRestaurant(data);
            }

            onClose();
        } catch (error) {
            console.error('Error saving restaurant:', error);
            alert('Có lỗi xảy ra khi lưu nhà hàng');
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="space-y-6">
            <div className="flex items-center justify-between">
                <div>
                    <div className="text-gray-900 text-2xl mb-2">
                        {restaurant ? 'Chỉnh sửa nhà hàng' : 'Thêm nhà hàng mới'}
                    </div>
                    <p className="text-gray-600">Điền thông tin chi tiết về nhà hàng</p>
                </div>
                <button
                    onClick={onClose}
                    className="p-2 hover:bg-gray-100 rounded-lg transition-colors"
                >
                    <X className="w-6 h-6 text-gray-600" />
                </button>
            </div>

            <form onSubmit={handleSubmit} className="bg-white rounded-xl shadow-sm border border-gray-200 p-6">
                <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
                    <div className="md:col-span-2">
                        <label className="block text-sm text-gray-700 mb-2">Tên nhà hàng *</label>
                        <input
                            type="text"
                            name="name"
                            value={formData.name}
                            onChange={handleChange}
                            className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                            required
                        />
                    </div>

                    <div className="md:col-span-2">
                        <label className="block text-sm text-gray-700 mb-2">Mô tả</label>
                        <textarea
                            name="description"
                            value={formData.description}
                            onChange={handleChange}
                            rows={3}
                            className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                        />
                    </div>

                    <div className="md:col-span-2">
                        <label className="block text-sm text-gray-700 mb-2">Địa chỉ *</label>
                        <input
                            type="text"
                            name="address"
                            value={formData.address}
                            onChange={handleChange}
                            className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                            required
                        />
                    </div>

                    <div>
                        <label className="block text-sm text-gray-700 mb-2">Vĩ độ</label>
                        <input
                            type="number"
                            step="any"
                            name="latitude"
                            value={formData.latitude}
                            onChange={handleChange}
                            className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                        />
                    </div>

                    <div>
                        <label className="block text-sm text-gray-700 mb-2">Kinh độ</label>
                        <input
                            type="number"
                            step="any"
                            name="longitude"
                            value={formData.longitude}
                            onChange={handleChange}
                            className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                        />
                    </div>

                    <div className="md:col-span-2">
                        <label className="block text-sm text-gray-700 mb-2">Điểm đến *</label>
                        <select
                            name="destinationId"
                            value={formData.destinationId}
                            onChange={handleChange}
                            className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                            required
                        >
                            <option value="">Chọn điểm đến</option>
                            {destinations.map(dest => (
                                <option key={dest.id} value={dest.id}>{dest.nameVi}</option>
                            ))}
                        </select>
                    </div>

                    <div className="md:col-span-2">
                        <ImageInput
                            value={formData.images}
                            onChange={(urls) => setFormData(prev => ({ ...prev, images: urls }))}
                            label="Hình ảnh"
                            placeholder="https://example.com/image1.jpg&#10;https://example.com/image2.jpg"
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
                        <span>{loading ? 'Đang lưu...' : 'Lưu nhà hàng'}</span>
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
