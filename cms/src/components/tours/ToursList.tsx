import React, { useEffect, useState } from 'react';
import { mockApi, Tour } from '../../services/mockApi';
import { Plus, Edit, Trash2, Map } from 'lucide-react';

export const ToursList: React.FC = () => {
  const [tours, setTours] = useState<Tour[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    loadTours();
  }, []);

  const loadTours = async () => {
    setLoading(true);
    try {
      const data = await mockApi.getTours();
      setTours(data);
    } finally {
      setLoading(false);
    }
  };

  const handleDelete = async (id: number) => {
    if (confirm('Bạn có chắc chắn muốn xóa tour này?')) {
      await mockApi.deleteTour(id);
      loadTours();
    }
  };

  return (
    <div className="space-y-6">
      <div className="flex items-center justify-between">
        <div>
          <div className="text-gray-900 text-2xl mb-2">Quản lý Tours</div>
          <p className="text-gray-600">Quản lý các tour du lịch</p>
        </div>
        <button className="flex items-center gap-2 bg-blue-600 text-white px-4 py-2 rounded-lg hover:bg-blue-700 transition-colors">
          <Plus className="w-5 h-5" />
          <span>Thêm tour</span>
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
                  <th className="px-6 py-3 text-left text-xs text-gray-500 uppercase tracking-wider">Tên tour</th>
                  <th className="px-6 py-3 text-left text-xs text-gray-500 uppercase tracking-wider">Điểm đến</th>
                  <th className="px-6 py-3 text-left text-xs text-gray-500 uppercase tracking-wider">Thời gian</th>
                  <th className="px-6 py-3 text-left text-xs text-gray-500 uppercase tracking-wider">Giá</th>
                  <th className="px-6 py-3 text-left text-xs text-gray-500 uppercase tracking-wider">Thao tác</th>
                </tr>
              </thead>
              <tbody className="divide-y divide-gray-200">
                {tours.map((tour) => (
                  <tr key={tour.id} className="hover:bg-gray-50">
                    <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900">{tour.id}</td>
                    <td className="px-6 py-4">
                      <div className="flex items-center gap-3">
                        {tour.images && tour.images.length > 0 ? (
                          <img src={tour.images[0]} alt="" className="w-12 h-12 rounded-lg object-cover" />
                        ) : (
                          <div className="w-12 h-12 bg-gray-100 rounded-lg flex items-center justify-center">
                            <Map className="w-6 h-6 text-gray-400" />
                          </div>
                        )}
                        <div>
                          <div className="text-sm text-gray-900">{tour.titleVi}</div>
                          <div className="text-xs text-gray-500">{tour.titleEn}</div>
                        </div>
                      </div>
                    </td>
                    <td className="px-6 py-4 text-sm text-gray-600">{tour.destinationName || '-'}</td>
                    <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-600">{tour.durationHours} giờ</td>
                    <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900">
                      {tour.price ? new Intl.NumberFormat('vi-VN').format(tour.price) + ' đ' : '-'}
                    </td>
                    <td className="px-6 py-4 whitespace-nowrap">
                      <div className="flex items-center gap-2">
                        <button className="p-2 text-blue-600 hover:bg-blue-50 rounded-lg transition-colors">
                          <Edit className="w-4 h-4" />
                        </button>
                        <button
                          onClick={() => handleDelete(tour.id)}
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
            {tours.length === 0 && (
              <div className="text-center py-12 text-gray-500">Chưa có tour nào</div>
            )}
          </div>
        )}
      </div>
    </div>
  );
};
