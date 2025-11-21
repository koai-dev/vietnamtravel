// @ts-ignore
import React, { useState } from 'react';
import { AuthProvider, useAuth } from './services/authContext';
import { LoginPage } from './components/auth/LoginPage';
import { Sidebar } from './components/Sidebar';
import { Header } from './components/Header';
import { Dashboard } from './components/Dashboard';
import { DestinationsList } from './components/destinations/DestinationsList';
import { HotelsList } from './components/hotels/HotelsList';
import { RoomsList } from './components/rooms/RoomsList';
import { BookingsList } from './components/bookings/BookingsList';
import { ToursList } from './components/tours/ToursList';
import { RestaurantsList } from './components/restaurants/RestaurantsList';
import { LocalFoodsList } from './components/localFoods/LocalFoodsList';
import { ReviewsList } from './components/reviews/ReviewsList';
import { UsersList } from './components/users/UsersList';
import { NotificationsList } from './components/notifications/NotificationsList';
import { PostmanTool } from './components/tools/PostmanTool';

function AppContent() {
  const { isAuthenticated } = useAuth();
  const [activeTab, setActiveTab] = useState('dashboard');

  if (!isAuthenticated) {
    return <LoginPage />;
  }

  const renderContent = () => {
    switch (activeTab) {
      case 'dashboard':
        return <Dashboard />;
      case 'destinations':
        return <DestinationsList />;
      case 'hotels':
        return <HotelsList />;
      case 'rooms':
        return <RoomsList />;
      case 'bookings':
        return <BookingsList />;
      case 'tours':
        return <ToursList />;
      case 'restaurants':
        return <RestaurantsList />;
      case 'local-foods':
        return <LocalFoodsList />;
      case 'reviews':
        return <ReviewsList />;
      case 'users':
        return <UsersList />;
      case 'notifications':
        return <NotificationsList />;
      case 'postman':
        return <PostmanTool />;
      default:
        return <Dashboard />;
    }
  };

  return (
    <div className="flex h-screen bg-gray-50">
      <Sidebar activeTab={activeTab} onTabChange={setActiveTab} />
      <div className="flex-1 flex flex-col overflow-hidden">
        <Header />
        <main className="flex-1 overflow-y-auto p-6">
          {renderContent()}
        </main>
      </div>
    </div>
  );
}

export default function App() {
  return (
    <AuthProvider>
      <AppContent />
    </AuthProvider>
  );
}
