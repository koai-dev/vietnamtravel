// @ts-ignore
import React, { useEffect, useState } from 'react';
import { getDashboardStats } from '../services/api';
import { MapPin, Hotel, Calendar, Users, DollarSign, Clock } from 'lucide-react';

export const Dashboard: React.FC = () => {
  const [stats, setStats] = useState<any>(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    loadStats();
  }, []);

  const loadStats = async () => {
    setLoading(true);
    try {
      const data = await getDashboardStats();
      setStats(data);
    } catch (error) {
      console.error('Error loading stats:', error);
    } finally {
      setLoading(false);
    }
  };

  if (loading) {
    return (
      <div className="flex items-center justify-center h-64">
        <div className="text-gray-500">Đang tải...</div>
      </div>
    );
  }

  const statCards = [
    {
      title: 'Tổng điểm đến',
      value: stats?.totalDestinations || 0,
      icon: <MapPin className="w-6 h-6" />,
      color: 'bg-blue-500',
    },
    {
      title: 'Tổng khách sạn',
      value: stats?.totalHotels || 0,
      icon: <Hotel className="w-6 h-6" />,
      color: 'bg-purple-500',
    },
    {
      title: 'Tổng đặt phòng',
      value: stats?.totalBookings || 0,
      icon: <Calendar className="w-6 h-6" />,
      color: 'bg-green-500',
    },
    {
      title: 'Tổng người dùng',
      value: stats?.totalUsers || 0,
      icon: <Users className="w-6 h-6" />,
      color: 'bg-orange-500',
    },
    {
      title: 'Tổng doanh thu',
      value: new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(stats?.totalRevenue || 0),
      icon: <DollarSign className="w-6 h-6" />,
      color: 'bg-emerald-500',
    },
    {
      title: 'Đang chờ xác nhận',
      value: stats?.pendingBookings || 0,
      icon: <Clock className="w-6 h-6" />,
      color: 'bg-yellow-500',
    },
  ];

  return (
    <div className="space-y-6">
      <div>
        <div className="text-gray-900 text-2xl mb-2">Tổng quan hệ thống</div>
        <p className="text-gray-600">Thống kê và dữ liệu tổng quan của hệ thống CMS</p>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
        {statCards.map((card, index) => (
          <div key={index} className="bg-white rounded-xl shadow-sm border border-gray-200 p-6">
            <div className="flex items-start justify-between">
              <div>
                <p className="text-gray-600 text-sm mb-2">{card.title}</p>
                <div className="text-gray-900 text-2xl">{card.value}</div>
              </div>
              <div className={`${card.color} text-white p-3 rounded-lg`}>
                {card.icon}
              </div>
            </div>
          </div>
        ))}
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
        <div className="bg-white rounded-xl shadow-sm border border-gray-200 p-6">
          <div className="text-gray-900 mb-4">Đặt phòng gần đây</div>
          <div className="space-y-3">
            {stats?.recentBookings && stats.recentBookings.length > 0 ? (
              stats.recentBookings.map((booking: any) => (
                <div key={booking.id} className="flex items-center justify-between py-3 border-b border-gray-100 last:border-0">
                  <div className="flex-1">
                    <div className="text-sm text-gray-900">{booking.hotelName || 'Unknown Hotel'}</div>
                    <div className="text-xs text-gray-500">{booking.userName || 'Unknown User'}</div>
                  </div>
                  <div className="text-right">
                    <div className={`text-xs px-2 py-1 rounded-full inline-block ${booking.status === 'confirmed' ? 'bg-green-100 text-green-700' :
                        booking.status === 'pending' ? 'bg-yellow-100 text-yellow-700' :
                          booking.status === 'cancelled' ? 'bg-red-100 text-red-700' :
                            'bg-blue-100 text-blue-700'
                      }`}>
                      {booking.status === 'confirmed' ? 'Đã xác nhận' :
                        booking.status === 'pending' ? 'Chờ xác nhận' :
                          booking.status === 'cancelled' ? 'Đã hủy' : 'Hoàn thành'}
                    </div>
                    <div className="text-xs text-gray-500 mt-1">
                      {new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(booking.totalPrice || 0)}
                    </div>
                  </div>
                </div>
              ))
            ) : (
              <div className="text-center py-8 text-gray-500">Chưa có đặt phòng nào</div>
            )}
          </div>
        </div>

        <div className="bg-white rounded-xl shadow-sm border border-gray-200 p-6">
          <div className="text-gray-900 mb-4">Đánh giá gần đây</div>
          <div className="space-y-3">
            {stats?.recentReviews && stats.recentReviews.length > 0 ? (
              stats.recentReviews.map((review: any) => (
                <div key={review.id} className="py-3 border-b border-gray-100 last:border-0">
                  <div className="flex items-start justify-between mb-2">
                    <div className="text-sm text-gray-900">{review.userName || 'Ẩn danh'}</div>
                    <div className="flex items-center gap-1">
                      {[...Array(5)].map((_, i) => (
                        <span key={i} className={i < (review.rating || 0) ? 'text-yellow-400' : 'text-gray-300'}>★</span>
                      ))}
                    </div>
                  </div>
                  <div className="text-xs text-gray-600 mb-1">{review.hotelName}</div>
                  <div className="text-xs text-gray-500">{review.comment}</div>
                </div>
              ))
            ) : (
              <div className="text-center py-8 text-gray-500">Chưa có đánh giá nào</div>
            )}
          </div>
        </div>
      </div>
    </div>
  );
};
