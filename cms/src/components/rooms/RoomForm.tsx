// @ts-ignore
import React, { useState } from 'react';
import { roomApi, Room } from '../../services/roomApi';
import { Hotel } from '../../services/hotelApi';
import { X, Save } from 'lucide-react';

interface RoomFormProps {
  room: Room | null;
  hotels: Hotel[];
  onClose: () => void;
}

export const RoomForm: React.FC<RoomFormProps> = ({ room, hotels, onClose }) => {
  const [formData, setFormData] = useState({
    hotelId: room?.hotelId || (hotels[0]?.id || 0),
    roomTypeVi: room?.roomTypeVi || '',
    roomTypeEn: room?.roomTypeEn || '',
    maxGuest: room?.maxGuest || 2,
    pricePerNight: room?.pricePerNight || 0,
    totalRooms: room?.totalRooms || 1,
    availableRooms: room?.availableRooms || 1,
    amenities: room?.amenities?.join(', ') || '',
  });

  const [loading, setLoading] = useState(false);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
    const { name, value } = e.target;
    setFormData(prev => ({ ...prev, [name]: name.includes('Id') || name.includes('Guest') || name.includes('Rooms') || name.includes('Price') ? Number(value) : value }));
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setLoading(true);

    try {
      const data = {
        hotelId: formData.hotelId,
        roomTypeVi: formData.roomTypeVi || undefined,
        roomTypeEn: formData.roomTypeEn || undefined,
        maxGuest: formData.maxGuest || undefined,
        pricePerNight: formData.pricePerNight || undefined,
        totalRooms: formData.totalRooms || undefined,
        availableRooms: formData.availableRooms || undefined,
        amenities: formData.amenities ? formData.amenities.split(',').map(a => a.trim()) : undefined,
      };

      if (room) {
        await roomApi.updateRoom(room.id, data);
      } else {
        await roomApi.createRoom(data);
      }

      onClose();
    } catch (error) {
      console.error('Error saving room:', error);
      alert('Có lỗi xảy ra khi lưu phòng');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="space-y-6">
      <div className="flex items-center justify-between">
        <div>
          <div className="text-gray-900 text-2xl mb-2">
            {room ? 'Chỉnh sửa phòng' : 'Thêm phòng mới'}
          </div>
          <p className="text-gray-600">Điền thông tin chi tiết về phòng</p>
        </div>
        <button onClick={onClose} className="p-2 hover:bg-gray-100 rounded-lg transition-colors">
          <X className="w-6 h-6 text-gray-600" />
        </button>
      </div>

      <form onSubmit={handleSubmit} className="bg-white rounded-xl shadow-sm border border-gray-200 p-6">
        <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
          <div className="md:col-span-2">
            <label className="block text-sm text-gray-700 mb-2">Khách sạn *</label>
            <select
              name="hotelId"
              value={formData.hotelId}
              onChange={handleChange}
              className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
              required
            >
              {hotels.map(hotel => (
                <option key={hotel.id} value={hotel.id}>{hotel.nameVi}</option>
              ))}
            </select>
          </div>

          <div>
            <label className="block text-sm text-gray-700 mb-2">Loại phòng (Tiếng Việt)</label>
            <input
              type="text"
              name="roomTypeVi"
              value={formData.roomTypeVi}
              onChange={handleChange}
              className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
          </div>

          <div>
            <label className="block text-sm text-gray-700 mb-2">Loại phòng (Tiếng Anh)</label>
            <input
              type="text"
              name="roomTypeEn"
              value={formData.roomTypeEn}
              onChange={handleChange}
              className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
          </div>

          <div>
            <label className="block text-sm text-gray-700 mb-2">Số khách tối đa</label>
            <input
              type="number"
              name="maxGuest"
              value={formData.maxGuest}
              onChange={handleChange}
              className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
          </div>

          <div>
            <label className="block text-sm text-gray-700 mb-2">Giá/đêm (VNĐ)</label>
            <input
              type="number"
              name="pricePerNight"
              value={formData.pricePerNight}
              onChange={handleChange}
              className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
          </div>

          <div>
            <label className="block text-sm text-gray-700 mb-2">Tổng số phòng</label>
            <input
              type="number"
              name="totalRooms"
              value={formData.totalRooms}
              onChange={handleChange}
              className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
          </div>

          <div>
            <label className="block text-sm text-gray-700 mb-2">Số phòng còn trống</label>
            <input
              type="number"
              name="availableRooms"
              value={formData.availableRooms}
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
              placeholder="TV, Minibar, Két sắt"
              className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
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
            <span>{loading ? 'Đang lưu...' : 'Lưu phòng'}</span>
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
