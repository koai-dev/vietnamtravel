import React, { useEffect, useState } from 'react';
import { mockApi, Hotel } from '../../services/mockApi';
import { Plus, Edit, Trash2, Search, Hotel as HotelIcon } from 'lucide-react';
import { HotelForm } from './HotelForm';

export const HotelsList: React.FC = () => {
  const [hotels, setHotels] = useState<Hotel[]>([]);
  const [filteredHotels, setFilteredHotels] = useState<Hotel[]>([]);
  const [loading, setLoading] = useState(true);
  const [searchTerm, setSearchTerm] = useState('');
  const [showForm, setShowForm] = useState(false);
  const [editingHotel, setEditingHotel] = useState<Hotel | null>(null);

  useEffect(() => {
    loadHotels();
  }, []);

  useEffect(() => {
    if (searchTerm) {
      setFilteredHotels(
        hotels.filter(h => 
          h.nameVi.toLowerCase().includes(searchTerm.toLowerCase()) ||
          h.nameEn.toLowerCase().includes(searchTerm.toLowerCase()) ||
          h.city?.toLowerCase().includes(searchTerm.toLowerCase())
        )
      );
    } else {
      setFilteredHotels(hotels);
    }
  }, [searchTerm, hotels]);

  const loadHotels = async () => {
    setLoading(true);
    try {
      const data = await mockApi.getHotels();
      setHotels(data);
      setFilteredHotels(data);
    } catch (error) {
      console.error('Error loading hotels:', error);
    } finally {
      setLoading(false);
    }
  };

  const handleDelete = async (id: number) => {
    if (confirm('Bạn có chắc chắn muốn xóa khách sạn này?')) {
      await mockApi.deleteHotel(id);
      loadHotels();
    }
  };

  const handleEdit = (hotel: Hotel) => {
    setEditingHotel(hotel);
    setShowForm(true);
  };

  const handleCloseForm = () => {
    setShowForm(false);
    setEditingHotel(null);
    loadHotels();
  };

  if (showForm) {
    return <HotelForm hotel={editingHotel} onClose={handleCloseForm} />;
  }

  return (
    <div className="space-y-6">
      <div className="flex items-center justify-between">
        <div>
          <div className="text-gray-900 text-2xl mb-2">Quản lý khách sạn</div>
          <p className="text-gray-600">Quản lý thông tin khách sạn và chỗ nghỉ</p>
        </div>
        <button
          onClick={() => setShowForm(true)}
          className="flex items-center gap-2 bg-blue-600 text-white px-4 py-2 rounded-lg hover:bg-blue-700 transition-colors"
        >
          <Plus className="w-5 h-5" />
          <span>Thêm khách sạn</span>
        </button>
      </div>

      <div className="bg-white rounded-xl shadow-sm border border-gray-200">
        <div className="p-4 border-b border-gray-200">
          <div className="relative">
            <Search className="absolute left-3 top-1/2 -translate-y-1/2 w-5 h-5 text-gray-400" />
            <input
              type="text"
              value={searchTerm}
              onChange={(e) => setSearchTerm(e.target.value)}
              placeholder="Tìm kiếm khách sạn..."
              className="w-full pl-10 pr-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
          </div>
        </div>

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
                  <th className="px-6 py-3 text-left text-xs text-gray-500 uppercase tracking-wider">Thành phố</th>
                  <th className="px-6 py-3 text-left text-xs text-gray-500 uppercase tracking-wider">Giá</th>
                  <th className="px-6 py-3 text-left text-xs text-gray-500 uppercase tracking-wider">Đánh giá</th>
                  <th className="px-6 py-3 text-left text-xs text-gray-500 uppercase tracking-wider">Lượt xem</th>
                  <th className="px-6 py-3 text-left text-xs text-gray-500 uppercase tracking-wider">Thao tác</th>
                </tr>
              </thead>
              <tbody className="divide-y divide-gray-200">
                {filteredHotels.map((hotel) => (
                  <tr key={hotel.id} className="hover:bg-gray-50">
                    <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900">{hotel.id}</td>
                    <td className="px-6 py-4">
                      <div className="flex items-center gap-3">
                        {hotel.images && hotel.images.length > 0 ? (
                          <img src={hotel.images[0]} alt="" className="w-12 h-12 rounded-lg object-cover" />
                        ) : (
                          <div className="w-12 h-12 bg-gray-100 rounded-lg flex items-center justify-center">
                            <HotelIcon className="w-6 h-6 text-gray-400" />
                          </div>
                        )}
                        <div>
                          <div className="text-sm text-gray-900">{hotel.nameVi}</div>
                          <div className="text-xs text-gray-500">{hotel.nameEn}</div>
                        </div>
                      </div>
                    </td>
                    <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-600">
                      {hotel.city || '-'}
                    </td>
                    <td className="px-6 py-4 whitespace-nowrap">
                      <div className="text-sm text-gray-900">
                        {hotel.minPrice && hotel.maxPrice ? (
                          <>
                            {new Intl.NumberFormat('vi-VN').format(hotel.minPrice)} - {new Intl.NumberFormat('vi-VN').format(hotel.maxPrice)} đ
                          </>
                        ) : '-'}
                      </div>
                    </td>
                    <td className="px-6 py-4 whitespace-nowrap">
                      <div className="flex items-center gap-1">
                        <span className="text-yellow-400">★</span>
                        <span className="text-sm text-gray-900">{hotel.rating.toFixed(1)}</span>
                        <span className="text-xs text-gray-500">({hotel.reviewCount})</span>
                      </div>
                    </td>
                    <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-600">
                      {hotel.viewsCount.toLocaleString()}
                    </td>
                    <td className="px-6 py-4 whitespace-nowrap text-sm">
                      <div className="flex items-center gap-2">
                        <button
                          onClick={() => handleEdit(hotel)}
                          className="p-2 text-blue-600 hover:bg-blue-50 rounded-lg transition-colors"
                        >
                          <Edit className="w-4 h-4" />
                        </button>
                        <button
                          onClick={() => handleDelete(hotel.id)}
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
            {filteredHotels.length === 0 && (
              <div className="text-center py-12 text-gray-500">
                {searchTerm ? 'Không tìm thấy kết quả' : 'Chưa có khách sạn nào'}
              </div>
            )}
          </div>
        )}
      </div>
    </div>
  );
};
