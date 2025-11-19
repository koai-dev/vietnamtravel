import React, { useEffect, useState } from 'react';
import { mockApi, Room, Hotel } from '../../services/mockApi';
import { Plus, Edit, Trash2, DoorOpen } from 'lucide-react';
import { RoomForm } from './RoomForm';

export const RoomsList: React.FC = () => {
  const [rooms, setRooms] = useState<Room[]>([]);
  const [hotels, setHotels] = useState<Hotel[]>([]);
  const [loading, setLoading] = useState(true);
  const [showForm, setShowForm] = useState(false);
  const [editingRoom, setEditingRoom] = useState<Room | null>(null);

  useEffect(() => {
    loadData();
  }, []);

  const loadData = async () => {
    setLoading(true);
    try {
      const [roomsData, hotelsData] = await Promise.all([
        mockApi.getRooms(),
        mockApi.getHotels()
      ]);
      setRooms(roomsData);
      setHotels(hotelsData);
    } catch (error) {
      console.error('Error loading data:', error);
    } finally {
      setLoading(false);
    }
  };

  const handleDelete = async (id: number) => {
    if (confirm('Bạn có chắc chắn muốn xóa phòng này?')) {
      await mockApi.deleteRoom(id);
      loadData();
    }
  };

  const handleEdit = (room: Room) => {
    setEditingRoom(room);
    setShowForm(true);
  };

  const handleCloseForm = () => {
    setShowForm(false);
    setEditingRoom(null);
    loadData();
  };

  const getHotelName = (hotelId: number) => {
    return hotels.find(h => h.id === hotelId)?.nameVi || 'N/A';
  };

  if (showForm) {
    return <RoomForm room={editingRoom} hotels={hotels} onClose={handleCloseForm} />;
  }

  return (
    <div className="space-y-6">
      <div className="flex items-center justify-between">
        <div>
          <div className="text-gray-900 text-2xl mb-2">Quản lý phòng</div>
          <p className="text-gray-600">Quản lý các loại phòng của khách sạn</p>
        </div>
        <button
          onClick={() => setShowForm(true)}
          className="flex items-center gap-2 bg-blue-600 text-white px-4 py-2 rounded-lg hover:bg-blue-700 transition-colors"
        >
          <Plus className="w-5 h-5" />
          <span>Thêm phòng</span>
        </button>
      </div>

      <div className="bg-white rounded-xl shadow-sm border border-gray-200">
        {loading ? (
          <div className="flex items-center justify-center py-12">
            <div className="text-gray-500">Đang tải...</div>
          </div>
        ) : (
          <div className="overflow-x-auto">
            <table className="w-full">
              <thead className="bg-gray-50 border-b border-gray-200">
                <tr>
                  <th className="px-6 py-3 text-left text-xs text-gray-500 uppercase tracking-wider">ID</th>
                  <th className="px-6 py-3 text-left text-xs text-gray-500 uppercase tracking-wider">Khách sạn</th>
                  <th className="px-6 py-3 text-left text-xs text-gray-500 uppercase tracking-wider">Loại phòng</th>
                  <th className="px-6 py-3 text-left text-xs text-gray-500 uppercase tracking-wider">Số khách</th>
                  <th className="px-6 py-3 text-left text-xs text-gray-500 uppercase tracking-wider">Giá/đêm</th>
                  <th className="px-6 py-3 text-left text-xs text-gray-500 uppercase tracking-wider">Số phòng</th>
                  <th className="px-6 py-3 text-left text-xs text-gray-500 uppercase tracking-wider">Còn trống</th>
                  <th className="px-6 py-3 text-left text-xs text-gray-500 uppercase tracking-wider">Thao tác</th>
                </tr>
              </thead>
              <tbody className="divide-y divide-gray-200">
                {rooms.map((room) => (
                  <tr key={room.id} className="hover:bg-gray-50">
                    <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900">{room.id}</td>
                    <td className="px-6 py-4 text-sm text-gray-900">{getHotelName(room.hotelId)}</td>
                    <td className="px-6 py-4">
                      <div className="flex items-center gap-2">
                        <DoorOpen className="w-5 h-5 text-gray-400" />
                        <div>
                          <div className="text-sm text-gray-900">{room.roomTypeVi}</div>
                          <div className="text-xs text-gray-500">{room.roomTypeEn}</div>
                        </div>
                      </div>
                    </td>
                    <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-600">
                      {room.maxGuest || '-'} người
                    </td>
                    <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900">
                      {room.pricePerNight ? new Intl.NumberFormat('vi-VN').format(room.pricePerNight) + ' đ' : '-'}
                    </td>
                    <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-600">
                      {room.totalRooms || '-'}
                    </td>
                    <td className="px-6 py-4 whitespace-nowrap text-sm">
                      <span className={`px-2 py-1 rounded-full ${
                        (room.availableRooms || 0) > 0 ? 'bg-green-100 text-green-700' : 'bg-red-100 text-red-700'
                      }`}>
                        {room.availableRooms || 0}
                      </span>
                    </td>
                    <td className="px-6 py-4 whitespace-nowrap text-sm">
                      <div className="flex items-center gap-2">
                        <button
                          onClick={() => handleEdit(room)}
                          className="p-2 text-blue-600 hover:bg-blue-50 rounded-lg transition-colors"
                        >
                          <Edit className="w-4 h-4" />
                        </button>
                        <button
                          onClick={() => handleDelete(room.id)}
                          className="p-2 text-red-600 hover:bg-red-50 rounded-lg transition-colors"
                        >
                          <Trash2 className="w-4 h-4" />
                        </button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
            {rooms.length === 0 && (
              <div className="text-center py-12 text-gray-500">Chưa có phòng nào</div>
            )}
          </div>
        )}
      </div>
    </div>
  );
};
