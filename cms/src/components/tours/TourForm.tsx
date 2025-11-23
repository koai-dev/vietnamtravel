// @ts-ignore
import React, { useState, useEffect } from 'react';
import { tourApi, Tour } from '../../services/tourApi';
import { destinationApi } from '../../services/destinationApi';
import { X, Save } from 'lucide-react';
import { ImageInput } from '../common/ImageInput';

interface Destination {
    id: number;
    nameVi: string;
}

interface TourFormProps {
    tour: Tour | null;
    onClose: () => void;
}

export const TourForm: React.FC<TourFormProps> = ({ tour, onClose }) => {
    const [destinations, setDestinations] = useState<Destination[]>([]);
    const [formData, setFormData] = useState({
        titleVi: tour?.titleVi || '',
        titleEn: tour?.titleEn || '',
        descriptionVi: tour?.descriptionVi || '',
        descriptionEn: tour?.descriptionEn || '',
        price: tour?.price || 0,
        durationHours: tour?.durationHours || 1,
        destinationId: tour?.destinationId || 0,
        images: tour?.images || [],
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
            if (!tour && data.length > 0) {
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
            [name]: name.includes('Id') || name.includes('price') || name.includes('Hours')
                ? Number(value)
                : value
        }));
    };

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        setLoading(true);

        try {
            const data = {
                titleVi: formData.titleVi,
                titleEn: formData.titleEn,
                descriptionVi: formData.descriptionVi,
                descriptionEn: formData.descriptionEn,
                price: formData.price,
                durationHours: formData.durationHours,
                destinationId: formData.destinationId,
                images: formData.images.length > 0 ? formData.images : [],
            };

            if (tour) {
                await tourApi.updateTour(tour.id, data);
            } else {
                await tourApi.createTour(data);
            }

            onClose();
        } catch (error) {
            console.error('Error saving tour:', error);
            alert('Có lỗi xảy ra khi lưu tour');
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="space-y-6">
            <div className="flex items-center justify-between">
                <div>
                    <div className="text-gray-900 text-2xl mb-2">
                        {tour ? 'Chỉnh sửa tour' : 'Thêm tour mới'}
                    </div>
                    <p className="text-gray-600">Điền thông tin chi tiết về tour</p>
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
                    <div>
                        <label className="block text-sm text-gray-700 mb-2">Tiêu đề (Tiếng Việt) *</label>
                        <input
                            type="text"
                            name="titleVi"
                            value={formData.titleVi}
                            onChange={handleChange}
                            className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                            required
                        />
                    </div>

                    <div>
                        <label className="block text-sm text-gray-700 mb-2">Tiêu đề (Tiếng Anh) *</label>
                        <input
                            type="text"
                            name="titleEn"
                            value={formData.titleEn}
                            onChange={handleChange}
                            className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                            required
                        />
                    </div>

                    <div className="md:col-span-2">
                        <label className="block text-sm text-gray-700 mb-2">Mô tả (Tiếng Việt)</label>
                        <textarea
                            name="descriptionVi"
                            value={formData.descriptionVi}
                            onChange={handleChange}
                            rows={3}
                            className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                        />
                    </div>

                    <div className="md:col-span-2">
                        <label className="block text-sm text-gray-700 mb-2">Mô tả (Tiếng Anh)</label>
                        <textarea
                            name="descriptionEn"
                            value={formData.descriptionEn}
                            onChange={handleChange}
                            rows={3}
                            className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                        />
                    </div>

                    <div>
                        <label className="block text-sm text-gray-700 mb-2">Giá (VNĐ) *</label>
                        <input
                            type="number"
                            name="price"
                            value={formData.price}
                            onChange={handleChange}
                            className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                            required
                        />
                    </div>

                    <div>
                        <label className="block text-sm text-gray-700 mb-2">Thời lượng (giờ) *</label>
                        <input
                            type="number"
                            name="durationHours"
                            value={formData.durationHours}
                            onChange={handleChange}
                            className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                            required
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
                        <span>{loading ? 'Đang lưu...' : 'Lưu tour'}</span>
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
