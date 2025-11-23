import React, { useState, useEffect } from 'react';
import { hotelApi, Hotel } from '../../services/hotelApi';
import { destinationApi } from '../../services/destinationApi';
import { X, Save } from 'lucide-react';
import { ImageInput } from '../common/ImageInput';

interface HotelFormProps {
  hotel: Hotel | null;
  onClose: () => void;
}

export const HotelForm: React.FC<HotelFormProps> = ({ hotel, onClose }) => {
  const [formData, setFormData] = useState({
    nameVi: hotel?.nameVi || '',
    nameEn: hotel?.nameEn || '',
    slug: hotel?.slug || '',
    descriptionVi: hotel?.descriptionVi || '',
    descriptionEn: hotel?.descriptionEn || '',
    address: hotel?.address || '',
    city: hotel?.city || '',
    latitude: hotel?.latitude || 0,
    longitude: hotel?.longitude || 0,
    checkInTime: hotel?.checkInTime || '14:00',
    checkOutTime: hotel?.checkOutTime || '12:00',
    minPrice: hotel?.minPrice || 0,
    maxPrice: hotel?.maxPrice || 0,
    amenities: hotel?.amenities?.join(', ') || '',
    tags: hotel?.tags?.join(', ') || '',
    images: hotel?.images || [],
  });

  const [loading, setLoading] = useState(false);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
    const { name, value } = e.target;
    setFormData(prev => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setLoading(true);

    try {
      const data = {
        nameVi: formData.nameVi,
        nameEn: formData.nameEn,
        slug: formData.slug,
        descriptionVi: formData.descriptionVi || undefined,
        descriptionEn: formData.descriptionEn || undefined,
        address: formData.address || undefined,
        city: formData.city || undefined,
        latitude: formData.latitude || undefined,
        longitude: formData.longitude || undefined,
        checkInTime: formData.checkInTime || undefined,
        checkOutTime: formData.checkOutTime || undefined,
        minPrice: formData.minPrice || undefined,
        maxPrice: formData.maxPrice || undefined,
        amenities: formData.amenities ? formData.amenities.split(',').map(a => a.trim()) : undefined,
        tags: formData.tags ? formData.tags.split(',').map(t => t.trim()) : undefined,
        images: formData.images.length > 0 ? formData.images : undefined,
        rating: hotel?.rating || 0,
        reviewCount: hotel?.reviewCount || 0,
        viewsCount: hotel?.viewsCount || 0,
        favoritesCount: hotel?.favoritesCount || 0,
      };

      if (hotel) {
        await hotelApi.updateHotel(hotel.id, data);
      } else {
        await hotelApi.createHotel(data);
      }

      onClose();
    } catch (error) {
      console.error('Error saving hotel:', error);
      alert('Có lỗi xảy ra khi lưu khách sạn');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="space-y-6">
      <div className="flex items-center justify-between">
        <div>
          <div className="text-gray-900 text-2xl mb-2">
            {hotel ? 'Chỉnh sửa khách sạn' : 'Thêm khách sạn mới'}
          </div>
          <p className="text-gray-600">Điền thông tin chi tiết về khách sạn</p>
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
            <label className="block text-sm text-gray-700 mb-2">Tên (Tiếng Việt) *</label>
            <input
              type="text"
              name="nameVi"
              value={formData.nameVi}
              onChange={handleChange}
              className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
              required
            />
          </div>

          <div>
            <label className="block text-sm text-gray-700 mb-2">Tên (Tiếng Anh) *</label>
            <input
              type="text"
              name="nameEn"
              value={formData.nameEn}
              onChange={handleChange}
              className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
              required
            />
          </div>

          <div className="md:col-span-2">
            <label className="block text-sm text-gray-700 mb-2">Slug *</label>
            <input
              type="text"
              name="slug"
              value={formData.slug}
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
            <label className="block text-sm text-gray-700 mb-2">Thành phố</label>
            <input
              type="text"
              name="city"
              value={formData.city}
              onChange={handleChange}
              className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
          </div>

          <div>
            <label className="block text-sm text-gray-700 mb-2">Địa chỉ</label>
            <input
              type="text"
              name="address"
              value={formData.address}
              onChange={handleChange}
              className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
          </div>

          <div>
            <label className="block text-sm text-gray-700 mb-2">Vĩ độ</label>
            <input
              type="number"
              step="0.000001"
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
              step="0.000001"
              name="longitude"
              value={formData.longitude}
              onChange={handleChange}
              className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
          </div>

          <div>
            <label className="block text-sm text-gray-700 mb-2">Giờ nhận phòng</label>
            <input
              type="time"
              name="checkInTime"
              value={formData.checkInTime}
              onChange={handleChange}
              className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
          </div>

          <div>
            <label className="block text-sm text-gray-700 mb-2">Giờ trả phòng</label>
            <input
              type="time"
              name="checkOutTime"
              value={formData.checkOutTime}
              onChange={handleChange}
              className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
          </div>

          <div>
            <label className="block text-sm text-gray-700 mb-2">Giá thấp nhất (VNĐ)</label>
            <input
              type="number"
              name="minPrice"
              value={formData.minPrice}
              onChange={handleChange}
              className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
          </div>

          <div>
            <label className="block text-sm text-gray-700 mb-2">Giá cao nhất (VNĐ)</label>
            <input
              type="number"
              name="maxPrice"
              value={formData.maxPrice}
              onChange={handleChange}
              className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
          </div>

          <div className="md:col-span-2">
            <label className="block text-sm text-gray-700 mb-2">Tiện nghi (phân cách bằng dấu phẩy)</label>
            <input
              type="text"
              name="amenities"
              value={formData.amenities}
              onChange={handleChange}
              placeholder="Wifi, Hồ bơi, Gym, Spa"
              className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
          </div>

          <div className="md:col-span-2">
            <label className="block text-sm text-gray-700 mb-2">Tags (phân cách bằng dấu phẩy)</label>
            <input
              type="text"
              name="tags"
              value={formData.tags}
              onChange={handleChange}
              placeholder="5 sao, trung tâm, sang trọng"
              className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
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
            <span>{loading ? 'Đang lưu...' : 'Lưu khách sạn'}</span>
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
