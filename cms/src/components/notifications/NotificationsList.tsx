import React, { useEffect, useState } from 'react';
import { mockApi, Notification } from '../../services/mockApi';
import { Plus, Bell } from 'lucide-react';

export const NotificationsList: React.FC = () => {
  const [notifications, setNotifications] = useState<Notification[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    loadNotifications();
  }, []);

  const loadNotifications = async () => {
    setLoading(true);
    try {
      const data = await mockApi.getNotifications();
      setNotifications(data);
    } finally {
      setLoading(false);
    }
  };

  const getTypeBadge = (type: string) => {
    const styles: Record<string, string> = {
      booking: 'bg-blue-100 text-blue-700',
      review: 'bg-green-100 text-green-700',
      system: 'bg-gray-100 text-gray-700',
      promotion: 'bg-purple-100 text-purple-700',
    };
    const labels: Record<string, string> = {
      booking: 'Đặt phòng',
      review: 'Đánh giá',
      system: 'Hệ thống',
      promotion: 'Khuyến mãi',
    };
    return (
      <span className={`px-2 py-1 rounded-full text-xs ${styles[type]}`}>
        {labels[type]}
      </span>
    );
  };

  return (
    <div className="space-y-6">
      <div className="flex items-center justify-between">
        <div>
          <div className="text-gray-900 text-2xl mb-2">Quản lý thông báo</div>
          <p className="text-gray-600">Quản lý thông báo gửi đến người dùng</p>
        </div>
        <button className="flex items-center gap-2 bg-blue-600 text-white px-4 py-2 rounded-lg hover:bg-blue-700 transition-colors">
          <Plus className="w-5 h-5" />
          <span>Tạo thông báo</span>
        </button>
      </div>

      <div className="bg-white rounded-xl shadow-sm border border-gray-200">
        {loading ? (
          <div className="flex items-center justify-center py-12">
            <div className="text-gray-500">Đang tải...</div>
          </div>
        ) : (
          <div className="divide-y divide-gray-200">
            {notifications.map((notification) => (
              <div key={notification.id} className="p-6 hover:bg-gray-50 transition-colors">
                <div className="flex items-start gap-4">
                  <div className={`p-3 rounded-full ${notification.isRead ? 'bg-gray-100' : 'bg-blue-100'}`}>
                    <Bell className={`w-5 h-5 ${notification.isRead ? 'text-gray-400' : 'text-blue-600'}`} />
                  </div>
                  <div className="flex-1">
                    <div className="flex items-start justify-between mb-2">
                      <div>
                        <div className={`${notification.isRead ? 'text-gray-600' : 'text-gray-900'} mb-1`}>
                          {notification.title}
                        </div>
                        <div className="text-sm text-gray-500">{notification.content}</div>
                      </div>
                      {getTypeBadge(notification.type)}
                    </div>
                    <div className="flex items-center gap-4 mt-3">
                      <div className="text-xs text-gray-500">
                        {new Date(notification.createdAt).toLocaleString('vi-VN')}
                      </div>
                      {!notification.isRead && (
                        <div className="text-xs bg-blue-600 text-white px-2 py-0.5 rounded">Chưa đọc</div>
                      )}
                    </div>
                  </div>
                </div>
              </div>
            ))}
          </div>
        )}
        {!loading && notifications.length === 0 && (
          <div className="text-center py-12 text-gray-500">Chưa có thông báo nào</div>
        )}
      </div>
    </div>
  );
};
