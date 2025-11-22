import React, { useEffect, useState } from 'react';
import { bookingApi, Booking } from '../../services/bookingApi';
import { Calendar, Check, X as XIcon, Clock } from 'lucide-react';

export const BookingsList: React.FC = () => {
  const [bookings, setBookings] = useState<Booking[]>([]);
  const [loading, setLoading] = useState(true);
  const [page, setPage] = useState(1);
  const [hasMore, setHasMore] = useState(true);
  const [loadingMore, setLoadingMore] = useState(false);

  useEffect(() => {
    loadBookings(1, true);
  }, []);

  const loadBookings = async (pageNum: number, reset: boolean = false) => {
    if (reset) {
      setLoading(true);
      setPage(1);
    } else {
      setLoadingMore(true);
    }

    try {
      const response = await bookingApi.getBookings(pageNum, 20);

      let newData: Booking[] = [];
      let totalPages = 1;

      if ('data' in response && 'pagination' in response) {
        newData = response.data;
        totalPages = response.pagination.totalPages;
      } else if (Array.isArray(response)) {
        newData = response;
        totalPages = 1;
      }

      if (reset) {
        setBookings(newData);
      } else {
        setBookings(prev => [...prev, ...newData]);
      }

      setHasMore(pageNum < totalPages);
      setPage(pageNum);
    } catch (error) {
      console.error('Error loading bookings:', error);
    } finally {
      setLoading(false);
      setLoadingMore(false);
    }
  };

  const handleLoadMore = () => {
    if (!loadingMore && hasMore) {
      loadBookings(page + 1);
    }
  };

  const handleUpdateStatus = async (id: number, status: 'pending' | 'confirmed' | 'cancelled' | 'completed') => {
    await bookingApi.updateBooking(id, { status });
    loadBookings(1, true);
  };

  const getStatusBadge = (status: string) => {
    const styles: Record<string, string> = {
      confirmed: 'bg-green-100 text-green-700',
      pending: 'bg-yellow-100 text-yellow-700',
      cancelled: 'bg-red-100 text-red-700',
      completed: 'bg-blue-100 text-blue-700',
    };
    const labels: Record<string, string> = {
      confirmed: 'Đã xác nhận',
      pending: 'Chờ xác nhận',
      cancelled: 'Đã hủy',
      completed: 'Hoàn thành',
    };
    return (
      <span className={`px-2 py-1 rounded-full text-xs ${styles[status]}`}>
        {labels[status]}
      </span>
    );
  };

  return (
    <div className="space-y-6">
      <div>
        <div className="text-gray-900 text-2xl mb-2">Quản lý đặt phòng</div>
        <p className="text-gray-600">Quản lý và theo dõi các đơn đặt phòng</p>
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
                  <th className="px-6 py-3 text-left text-xs text-gray-500 uppercase tracking-wider">Mã ĐP</th>
                  <th className="px-6 py-3 text-left text-xs text-gray-500 uppercase tracking-wider">Khách hàng</th>
                  <th className="px-6 py-3 text-left text-xs text-gray-500 uppercase tracking-wider">Khách sạn</th>
                  <th className="px-6 py-3 text-left text-xs text-gray-500 uppercase tracking-wider">Loại phòng</th>
                  <th className="px-6 py-3 text-left text-xs text-gray-500 uppercase tracking-wider">Ngày</th>
                  <th className="px-6 py-3 text-left text-xs text-gray-500 uppercase tracking-wider">Tổng tiền</th>
                  <th className="px-6 py-3 text-left text-xs text-gray-500 uppercase tracking-wider">Trạng thái</th>
                  <th className="px-6 py-3 text-left text-xs text-gray-500 uppercase tracking-wider">Thao tác</th>
                </tr>
              </thead>
              <tbody className="divide-y divide-gray-200">
                {bookings.map((booking) => (
                  <tr key={booking.id} className="hover:bg-gray-50">
                    <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900">#{booking.id}</td>
                    <td className="px-6 py-4 text-sm text-gray-900">{booking.userName}</td>
                    <td className="px-6 py-4 text-sm text-gray-900">{booking.hotelName}</td>
                    <td className="px-6 py-4 text-sm text-gray-600">{booking.roomType}</td>
                    <td className="px-6 py-4 whitespace-nowrap">
                      <div className="flex items-center gap-1 text-sm text-gray-900">
                        <Calendar className="w-4 h-4 text-gray-400" />
                        <span>{booking.checkIn}</span>
                        <span className="text-gray-400">→</span>
                        <span>{booking.checkOut}</span>
                      </div>
                    </td>
                    <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900">
                      {new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(booking.totalPrice || 0)}
                    </td>
                    <td className="px-6 py-4 whitespace-nowrap">
                      {getStatusBadge(booking.status)}
                    </td>
                    <td className="px-6 py-4 whitespace-nowrap">
                      <div className="flex items-center gap-1">
                        {booking.status === 'pending' && (
                          <>
                            <button
                              onClick={() => handleUpdateStatus(booking.id, 'confirmed')}
                              className="p-1.5 text-green-600 hover:bg-green-50 rounded transition-colors"
                              title="Xác nhận"
                            >
                              <Check className="w-4 h-4" />
                            </button>
                            <button
                              onClick={() => handleUpdateStatus(booking.id, 'cancelled')}
                              className="p-1.5 text-red-600 hover:bg-red-50 rounded transition-colors"
                              title="Hủy"
                            >
                              <XIcon className="w-4 h-4" />
                            </button>
                          </>
                        )}
                        {booking.status === 'confirmed' && (
                          <button
                            onClick={() => handleUpdateStatus(booking.id, 'completed')}
                            className="p-1.5 text-blue-600 hover:bg-blue-50 rounded transition-colors"
                            title="Hoàn thành"
                          >
                            <Clock className="w-4 h-4" />
                          </button>
                        )}
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
            {bookings.length === 0 && (
              <div className="text-center py-12 text-gray-500">Chưa có đặt phòng nào</div>
            )}
            {hasMore && bookings.length > 0 && (
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
