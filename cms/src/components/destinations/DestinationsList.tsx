// @ts-ignore
import React, { useEffect, useState } from 'react';
import { destinationApi, Destination, DestinationDetail } from '../../services/destinationApi';
import { Plus, Edit, Trash2, Search, MapPin } from 'lucide-react';
import { DestinationForm } from './DestinationForm';

export const DestinationsList: React.FC = () => {
  const [destinations, setDestinations] = useState<Destination[]>([]);
  const [loading, setLoading] = useState(true);
  const [searchTerm, setSearchTerm] = useState('');
  const [showForm, setShowForm] = useState(false);
  const [editingDestination, setEditingDestination] = useState<DestinationDetail | null>(null);
  const [page, setPage] = useState(1);
  const [hasMore, setHasMore] = useState(true);
  const [loadingMore, setLoadingMore] = useState(false);

  useEffect(() => {
    const delayDebounceFn = setTimeout(() => {
      loadDestinations(1, true);
    }, 500);

    return () => clearTimeout(delayDebounceFn);
  }, [searchTerm]);

  const loadDestinations = async (pageNum: number, reset: boolean = false) => {
    if (reset) {
      setLoading(true);
      setPage(1);
    } else {
      setLoadingMore(true);
    }

    try {
      const response = searchTerm
        ? await destinationApi.searchDestinations(searchTerm, undefined, pageNum, 20)
        : await destinationApi.getDestinations(pageNum, 20);

      // Check if response has data and pagination property (PaginatedResponse)
      // or if it's just an array (backward compatibility if API not fully updated or mock)
      let newData: Destination[] = [];
      let totalPages = 1;

      if ('data' in response && 'pagination' in response) {
        newData = response.data;
        totalPages = response.pagination.totalPages;
      } else if (Array.isArray(response)) {
        newData = response;
        totalPages = 1;
      }

      if (reset) {
        setDestinations(newData);
      } else {
        setDestinations(prev => [...prev, ...newData]);
      }

      setHasMore(pageNum < totalPages);
      setPage(pageNum);
    } catch (error) {
      console.error('Error loading destinations:', error);
    } finally {
      setLoading(false);
      setLoadingMore(false);
    }
  };

  const handleLoadMore = () => {
    if (!loadingMore && hasMore) {
      loadDestinations(page + 1);
    }
  };

  const handleDelete = async (id: number) => {
    if (confirm('Bạn có chắc chắn muốn xóa điểm đến này?')) {
      await destinationApi.deleteDestination(id);
      await loadDestinations(1, true);
    }
  };

  const handleEdit = async (destination: Destination) => {
    try {
      const detail = await destinationApi.getDestinationDetail(destination.id);
      setEditingDestination(detail.data);
      setShowForm(true);
    } catch (error) {
      console.error('Error loading destination detail:', error);
      alert('Không thể tải thông tin chi tiết điểm đến');
    }
  };

  const handleCloseForm = () => {
    setShowForm(false);
    setEditingDestination(null);
    loadDestinations(1, true);
  };

  const getTypeLabel = (type: string) => {
    const labels: Record<string, string> = {
      region: 'Vùng miền',
      city: 'Thành phố',
      attraction: 'Điểm tham quan',
      spot: 'Địa điểm',
    };
    return labels[type] || type;
  };

  const getStatusBadge = (status: string) => {
    const styles: Record<string, string> = {
      ACTIVE: 'bg-green-100 text-green-700',
      INACTIVE: 'bg-gray-100 text-gray-700',
      DRAFT: 'bg-yellow-100 text-yellow-700',
    };
    const labels: Record<string, string> = {
      ACTIVE: 'Hoạt động',
      INACTIVE: 'Tạm dừng',
      DRAFT: 'Nháp',
    };
    return (
      <span className={`px-2 py-1 rounded-full text-xs ${styles[status]}`}>
        {labels[status]}
      </span>
    );
  };

  if (showForm) {
    return <DestinationForm destination={editingDestination} onClose={handleCloseForm} />;
  }

  return (
    <div className="space-y-6">
      <div className="flex items-center justify-between">
        <div>
          <div className="text-gray-900 text-2xl mb-2">Quản lý điểm đến</div>
          <p className="text-gray-600">Quản lý các điểm đến du lịch</p>
        </div>
        <button
          onClick={() => setShowForm(true)}
          className="flex items-center gap-2 bg-blue-600 text-white px-4 py-2 rounded-lg hover:bg-blue-700 transition-colors"
        >
          <Plus className="w-5 h-5" />
          <span>Thêm điểm đến</span>
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
              placeholder="Tìm kiếm theo tên, thành phố..."
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
                  <th className="px-6 py-3 text-left text-xs text-gray-500 uppercase tracking-wider">Tên</th>
                  <th className="px-6 py-3 text-left text-xs text-gray-500 uppercase tracking-wider">Loại</th>
                  <th className="px-6 py-3 text-left text-xs text-gray-500 uppercase tracking-wider">Thành phố</th>
                  <th className="px-6 py-3 text-left text-xs text-gray-500 uppercase tracking-wider">Đánh giá</th>
                  <th className="px-6 py-3 text-left text-xs text-gray-500 uppercase tracking-wider">Lượt xem</th>
                  <th className="px-6 py-3 text-left text-xs text-gray-500 uppercase tracking-wider">Trạng thái</th>
                  <th className="px-6 py-3 text-left text-xs text-gray-500 uppercase tracking-wider">Thao tác</th>
                </tr>
              </thead>
              <tbody className="divide-y divide-gray-200">
                {destinations.map((destination) => (
                  <tr key={destination.id} className="hover:bg-gray-50">
                    <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900">{destination.id}</td>
                    <td className="px-6 py-4">
                      <div className="flex items-center gap-3">
                        {destination.images && destination.images.length > 0 ? (
                          <img src={destination.images[0]} alt="" className="w-12 h-12 rounded-lg object-cover" />
                        ) : (
                          <div className="w-12 h-12 bg-gray-100 rounded-lg flex items-center justify-center">
                            <MapPin className="w-6 h-6 text-gray-400" />
                          </div>
                        )}
                        <div>
                          <div className="text-sm text-gray-900">{destination.nameVi}</div>
                          <div className="text-xs text-gray-500">{destination.nameEn}</div>
                        </div>
                      </div>
                    </td>
                    <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-600">
                      {getTypeLabel(destination.type)}
                    </td>
                    <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-600">
                      {destination.city || '-'}
                    </td>
                    <td className="px-6 py-4 whitespace-nowrap">
                      <div className="flex items-center gap-1">
                        <span className="text-yellow-400">★</span>
                        <span className="text-sm text-gray-900">{destination.avgRating.toFixed(1)}</span>
                        <span className="text-xs text-gray-500">({destination.reviewCount})</span>
                      </div>
                    </td>
                    <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-600">
                      {(destination.viewsCount ?? 0).toLocaleString()}
                    </td>
                    <td className="px-6 py-4 whitespace-nowrap">
                      {getStatusBadge(destination.status)}
                    </td>
                    <td className="px-6 py-4 whitespace-nowrap text-sm">
                      <div className="flex items-center gap-2">
                        <button
                          onClick={() => handleEdit(destination)}
                          className="p-2 text-blue-600 hover:bg-blue-50 rounded-lg transition-colors"
                        >
                          <Edit className="w-4 h-4" />
                        </button>
                        <button
                          onClick={() => handleDelete(destination.id)}
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
            {destinations.length === 0 && (
              <div className="text-center py-12 text-gray-500">
                {searchTerm ? 'Không tìm thấy kết quả' : 'Chưa có điểm đến nào'}
              </div>
            )}
            {hasMore && destinations.length > 0 && (
              <div className="flex justify-center p-4 border-t border-gray-200">
                <button
                  onClick={handleLoadMore}
                  disabled={loadingMore}
                  className="px-4 py-2 text-sm font-medium text-blue-600 bg-blue-50 rounded-lg hover:bg-blue-100 disabled:opacity-50"
                >
                  {loadingMore ? 'Đang tải...' : 'Xem thêm'}
                </button>
              </div>
            )}
          </div>
        )}
      </div>
    </div>
  );
};
