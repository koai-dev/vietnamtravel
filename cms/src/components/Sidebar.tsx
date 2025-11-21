// @ts-ignore
import React from 'react';
import {
  Home,
  MapPin,
  Hotel,
  Calendar,
  Map,
  UtensilsCrossed,
  Star,
  Users,
  Bell,
  DoorOpen,
  ChevronDown,
  Send
} from 'lucide-react';

interface SidebarProps {
  activeTab: string;
  onTabChange: (tab: string) => void;
}

interface MenuItem {
  id: string;
  label: string;
  icon: React.ReactNode;
  children?: { id: string; label: string }[];
}

export const Sidebar: React.FC<SidebarProps> = ({ activeTab, onTabChange }) => {
  const [expandedMenus, setExpandedMenus] = React.useState<string[]>(['hotels']);

  const menuItems: MenuItem[] = [
    { id: 'dashboard', label: 'Tổng quan', icon: <Home className="w-5 h-5" /> },
    { id: 'destinations', label: 'Điểm đến', icon: <MapPin className="w-5 h-5" /> },
    {
      id: 'hotels',
      label: 'Khách sạn',
      icon: <Hotel className="w-5 h-5" />,
      children: [
        { id: 'hotels', label: 'Danh sách khách sạn' },
        { id: 'rooms', label: 'Quản lý phòng' },
      ]
    },
    { id: 'bookings', label: 'Đặt phòng', icon: <Calendar className="w-5 h-5" /> },
    { id: 'tours', label: 'Tours', icon: <Map className="w-5 h-5" /> },
    {
      id: 'food',
      label: 'Ẩm thực',
      icon: <UtensilsCrossed className="w-5 h-5" />,
      children: [
        { id: 'restaurants', label: 'Nhà hàng' },
        { id: 'local-foods', label: 'Món ăn địa phương' },
      ]
    },
    { id: 'reviews', label: 'Đánh giá', icon: <Star className="w-5 h-5" /> },
    { id: 'users', label: 'Người dùng', icon: <Users className="w-5 h-5" /> },
    { id: 'notifications', label: 'Thông báo', icon: <Bell className="w-5 h-5" /> },
    { id: 'postman', label: 'API Tester', icon: <Send className="w-5 h-5" /> },
  ];

  const toggleMenu = (menuId: string) => {
    setExpandedMenus(prev =>
      prev.includes(menuId)
        ? prev.filter(id => id !== menuId)
        : [...prev, menuId]
    );
  };

  const renderMenuItem = (item: MenuItem) => {
    const hasChildren = item.children && item.children.length > 0;
    const isExpanded = expandedMenus.includes(item.id);

    if (hasChildren) {
      return (
        <div key={item.id}>
          <button
            onClick={() => toggleMenu(item.id)}
            className="w-full flex items-center justify-between px-4 py-3 text-gray-700 hover:bg-blue-50 transition-colors"
          >
            <div className="flex items-center gap-3">
              {item.icon}
              <span>{item.label}</span>
            </div>
            <ChevronDown className={`w-4 h-4 transition-transform ${isExpanded ? 'rotate-180' : ''}`} />
          </button>
          {isExpanded && (
            <div className="bg-gray-50">
              {item.children?.map(child => (
                <button
                  key={child.id}
                  onClick={() => onTabChange(child.id)}
                  className={`w-full text-left pl-14 pr-4 py-2.5 text-sm transition-colors ${activeTab === child.id
                    ? 'bg-blue-100 text-blue-600'
                    : 'text-gray-600 hover:bg-blue-50'
                    }`}
                >
                  {child.label}
                </button>
              ))}
            </div>
          )}
        </div>
      );
    }

    return (
      <button
        key={item.id}
        onClick={() => onTabChange(item.id)}
        className={`w-full flex items-center gap-3 px-4 py-3 transition-colors ${activeTab === item.id
          ? 'bg-blue-100 text-blue-600 border-r-4 border-blue-600'
          : 'text-gray-700 hover:bg-blue-50'
          }`}
      >
        {item.icon}
        <span>{item.label}</span>
      </button>
    );
  };

  return (
    <div className="w-64 bg-white border-r border-gray-200 h-screen flex flex-col">
      <div className="p-6 border-b border-gray-200">
        <div className="flex items-center gap-2">
          <MapPin className="w-8 h-8 text-blue-600" />
          <div>
            <div className="text-blue-600">Travel CMS</div>
            <div className="text-xs text-gray-500">Cẩm nang du lịch</div>
          </div>
        </div>
      </div>

      <nav className="flex-1 overflow-y-auto">
        {menuItems.map(item => renderMenuItem(item))}
      </nav>
    </div>
  );
};
