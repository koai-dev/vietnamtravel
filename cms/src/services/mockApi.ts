// Mock API service for CMS

export interface Destination {
  id: number;
  nameVi: string;
  nameEn: string;
  descriptionVi?: string;
  descriptionEn?: string;
  latitude?: number;
  longitude?: number;
  type: 'region' | 'city' | 'attraction' | 'spot';
  images?: string[];
  parentId?: number;
  slug?: string;
  address?: string;
  city?: string;
  tags?: string[];
  bestTimeToVisit?: string;
  openingHours?: string;
  priceFrom?: number;
  priceTo?: number;
  externalLinks?: string[];
  addressLink?: string;
  avgRating: number;
  reviewCount: number;
  viewsCount: number;
  favoritesCount: number;
  status: 'ACTIVE' | 'INACTIVE' | 'DRAFT';
  sortOrder: number;
  createdAt: string;
  updatedAt: string;
}

export interface Hotel {
  id: number;
  nameVi: string;
  nameEn: string;
  slug: string;
  descriptionVi?: string;
  descriptionEn?: string;
  address?: string;
  city?: string;
  latitude?: number;
  longitude?: number;
  addressLink?: string;
  contact?: { phone?: string; email?: string; website?: string };
  images?: string[];
  minPrice?: number;
  maxPrice?: number;
  amenities?: string[];
  checkInTime?: string;
  checkOutTime?: string;
  cancellationPolicy?: string;
  childPolicy?: string;
  petPolicy?: string;
  tags?: string[];
  externalBookingLinks?: string[];
  rating: number;
  reviewCount: number;
  viewsCount: number;
  favoritesCount: number;
  hostId?: number;
  createdAt: string;
  updatedAt: string;
}

export interface Room {
  id: number;
  hotelId: number;
  roomTypeVi?: string;
  roomTypeEn?: string;
  maxGuest?: number;
  pricePerNight?: number;
  totalRooms?: number;
  availableRooms?: number;
  amenities?: string[];
}

export interface Booking {
  id: number;
  userId: number;
  hotelId: number;
  roomId: number;
  checkIn: string;
  checkOut: string;
  totalPrice?: number;
  status: 'pending' | 'confirmed' | 'cancelled' | 'completed';
  createdAt: string;
  updatedAt: string;
  userName?: string;
  hotelName?: string;
  roomType?: string;
}

export interface Tour {
  id: number;
  titleVi?: string;
  titleEn?: string;
  descriptionVi?: string;
  descriptionEn?: string;
  price?: number;
  durationHours?: number;
  destinationId?: number;
  images?: string[];
  destinationName?: string;
}

export interface Restaurant {
  id: number;
  name: string;
  description: string;
  images: string[];
  address: string;
  latitude: number;
  longitude: number;
  destinationId: number;
  destinationName?: string;
}

export interface LocalFood {
  id: number;
  destinationId: number;
  nameVi: string;
  nameEn?: string;
  descriptionVi?: string;
  descriptionEn?: string;
  images?: string[];
  destinationName?: string;
}

export interface Review {
  id: number;
  userId?: number;
  hotelId: number;
  rating?: number;
  comment?: string;
  createdAt: string;
  userName?: string;
  hotelName?: string;
}

export interface User {
  id: number;
  email: string;
  name?: string;
  avatarUrl?: string;
  phone?: string;
  role: 'user' | 'host' | 'admin';
  createdAt: string;
  updatedAt: string;
}

export interface Notification {
  id: number;
  userId: number;
  title: string;
  content: string;
  image?: string;
  type: 'booking' | 'review' | 'system' | 'promotion';
  isRead: boolean;
  metadata?: string;
  createdAt: string;
}

// Mock data storage

let mockHotels: Hotel[] = [
  {
    id: 1,
    nameVi: 'Khách sạn Melia Hà Nội',
    nameEn: 'Melia Hanoi Hotel',
    slug: 'melia-hanoi',
    descriptionVi: 'Khách sạn 5 sao sang trọng',
    descriptionEn: 'Luxury 5-star hotel',
    address: '44 Lý Thường Kiệt, Hoàn Kiếm, Hà Nội',
    city: 'Hà Nội',
    latitude: 21.0227,
    longitude: 105.8491,
    contact: { phone: '024-3934-3343', email: 'info@melia-hanoi.com' },
    images: ['https://images.unsplash.com/photo-1566073771259-6a8506099945?w=800'],
    minPrice: 2000000,
    maxPrice: 5000000,
    amenities: ['Wifi miễn phí', 'Hồ bơi', 'Gym', 'Spa', 'Nhà hàng'],
    checkInTime: '14:00',
    checkOutTime: '12:00',
    tags: ['5 sao', 'trung tâm', 'sang trọng'],
    rating: 4.6,
    reviewCount: 342,
    viewsCount: 8920,
    favoritesCount: 234,
    createdAt: '2024-01-01T00:00:00',
    updatedAt: '2024-01-01T00:00:00',
  },
];

let mockRooms: Room[] = [
  {
    id: 1,
    hotelId: 1,
    roomTypeVi: 'Phòng Deluxe',
    roomTypeEn: 'Deluxe Room',
    maxGuest: 2,
    pricePerNight: 2500000,
    totalRooms: 50,
    availableRooms: 35,
    amenities: ['TV', 'Minibar', 'Két sắt', 'Máy sấy tóc'],
  },
  {
    id: 2,
    hotelId: 1,
    roomTypeVi: 'Phòng Suite',
    roomTypeEn: 'Suite Room',
    maxGuest: 4,
    pricePerNight: 4500000,
    totalRooms: 20,
    availableRooms: 12,
    amenities: ['TV', 'Minibar', 'Két sắt', 'Máy sấy tóc', 'Phòng khách riêng'],
  },
];

let mockBookings: Booking[] = [
  {
    id: 1,
    userId: 2,
    hotelId: 1,
    roomId: 1,
    checkIn: '2024-12-25',
    checkOut: '2024-12-27',
    totalPrice: 5000000,
    status: 'confirmed',
    createdAt: '2024-12-01T10:00:00',
    updatedAt: '2024-12-01T10:00:00',
    userName: 'Nguyễn Văn A',
    hotelName: 'Khách sạn Melia Hà Nội',
    roomType: 'Phòng Deluxe',
  },
  {
    id: 2,
    userId: 3,
    hotelId: 1,
    roomId: 2,
    checkIn: '2024-12-30',
    checkOut: '2025-01-02',
    totalPrice: 13500000,
    status: 'pending',
    createdAt: '2024-12-15T14:30:00',
    updatedAt: '2024-12-15T14:30:00',
    userName: 'Trần Thị B',
    hotelName: 'Khách sạn Melia Hà Nội',
    roomType: 'Phòng Suite',
  },
];

let mockTours: Tour[] = [
  {
    id: 1,
    titleVi: 'Tour Hà Nội 1 ngày',
    titleEn: 'Hanoi 1 Day Tour',
    descriptionVi: 'Khám phá Hà Nội trong 1 ngày',
    descriptionEn: 'Explore Hanoi in 1 day',
    price: 500000,
    durationHours: 8,
    destinationId: 1,
    images: ['https://images.unsplash.com/photo-1583417319070-4a69db38a482?w=800'],
    destinationName: 'Hà Nội',
  },
];

let mockRestaurants: Restaurant[] = [
  {
    id: 1,
    name: 'Nhà hàng Ngon',
    description: 'Nhà hàng ẩm thực Việt Nam truyền thống',
    images: ['https://images.unsplash.com/photo-1552566626-52f8b828add9?w=800'],
    address: '79 Phan Bội Châu, Hoàn Kiếm, Hà Nội',
    latitude: 21.0230,
    longitude: 105.8530,
    destinationId: 1,
    destinationName: 'Hà Nội',
  },
];

let mockLocalFoods: LocalFood[] = [
  {
    id: 1,
    destinationId: 1,
    nameVi: 'Phở',
    nameEn: 'Pho',
    descriptionVi: 'Món ăn truyền thống của Việt Nam',
    descriptionEn: 'Traditional Vietnamese dish',
    images: ['https://images.unsplash.com/photo-1582878826629-29b7ad1cdc43?w=800'],
    destinationName: 'Hà Nội',
  },
  {
    id: 2,
    destinationId: 1,
    nameVi: 'Bún chả',
    nameEn: 'Bun Cha',
    descriptionVi: 'Đặc sản Hà Nội',
    descriptionEn: 'Hanoi specialty',
    images: ['https://images.unsplash.com/photo-1559847844-5315695dadae?w=800'],
    destinationName: 'Hà Nội',
  },
];

let mockReviews: Review[] = [
  {
    id: 1,
    userId: 2,
    hotelId: 1,
    rating: 5,
    comment: 'Khách sạn tuyệt vời, dịch vụ tốt!',
    createdAt: '2024-11-20T10:00:00',
    userName: 'Nguyễn Văn A',
    hotelName: 'Khách sạn Melia Hà Nội',
  },
];

let mockUsers: User[] = [
  {
    id: 1,
    email: 'admin@travel.com',
    name: 'Admin User',
    role: 'admin',
    avatarUrl: 'https://ui-avatars.com/api/?name=Admin+User&background=3b82f6&color=fff',
    createdAt: '2024-01-01T00:00:00',
    updatedAt: '2024-01-01T00:00:00',
  },
  {
    id: 2,
    email: 'user1@gmail.com',
    name: 'Nguyễn Văn A',
    phone: '0123456789',
    role: 'user',
    createdAt: '2024-02-01T00:00:00',
    updatedAt: '2024-02-01T00:00:00',
  },
  {
    id: 3,
    email: 'user2@gmail.com',
    name: 'Trần Thị B',
    phone: '0987654321',
    role: 'user',
    createdAt: '2024-03-01T00:00:00',
    updatedAt: '2024-03-01T00:00:00',
  },
];

let mockNotifications: Notification[] = [
  {
    id: 1,
    userId: 2,
    title: 'Đặt phòng thành công',
    content: 'Đặt phòng tại Khách sạn Melia Hà Nội đã được xác nhận',
    type: 'booking',
    isRead: false,
    createdAt: '2024-12-01T10:05:00',
  },
];

// Helper function to generate ID
const generateId = (arr: any[]) => {
  return arr.length > 0 ? Math.max(...arr.map(item => item.id)) + 1 : 1;
};

// API functions
export const mockApi = {
  // Hotels
  getHotels: async (): Promise<Hotel[]> => {
    return new Promise((resolve) => {
      setTimeout(() => resolve([...mockHotels]), 300);
    });
  },

  getHotel: async (id: number): Promise<Hotel | undefined> => {
    return new Promise((resolve) => {
      setTimeout(() => resolve(mockHotels.find(h => h.id === id)), 300);
    });
  },

  createHotel: async (data: Omit<Hotel, 'id' | 'createdAt' | 'updatedAt'>): Promise<Hotel> => {
    return new Promise((resolve) => {
      setTimeout(() => {
        const newHotel: Hotel = {
          ...data,
          id: generateId(mockHotels),
          createdAt: new Date().toISOString(),
          updatedAt: new Date().toISOString(),
        };
        mockHotels.push(newHotel);
        resolve(newHotel);
      }, 300);
    });
  },

  updateHotel: async (id: number, data: Partial<Hotel>): Promise<Hotel | undefined> => {
    return new Promise((resolve) => {
      setTimeout(() => {
        const index = mockHotels.findIndex(h => h.id === id);
        if (index !== -1) {
          mockHotels[index] = {
            ...mockHotels[index],
            ...data,
            updatedAt: new Date().toISOString(),
          };
          resolve(mockHotels[index]);
        } else {
          resolve(undefined);
        }
      }, 300);
    });
  },

  deleteHotel: async (id: number): Promise<boolean> => {
    return new Promise((resolve) => {
      setTimeout(() => {
        const index = mockHotels.findIndex(h => h.id === id);
        if (index !== -1) {
          mockHotels.splice(index, 1);
          resolve(true);
        } else {
          resolve(false);
        }
      }, 300);
    });
  },

  // Rooms
  getRooms: async (): Promise<Room[]> => {
    return new Promise((resolve) => {
      setTimeout(() => resolve([...mockRooms]), 300);
    });
  },

  getRoomsByHotel: async (hotelId: number): Promise<Room[]> => {
    return new Promise((resolve) => {
      setTimeout(() => resolve(mockRooms.filter(r => r.hotelId === hotelId)), 300);
    });
  },

  createRoom: async (data: Omit<Room, 'id'>): Promise<Room> => {
    return new Promise((resolve) => {
      setTimeout(() => {
        const newRoom: Room = {
          ...data,
          id: generateId(mockRooms),
        };
        mockRooms.push(newRoom);
        resolve(newRoom);
      }, 300);
    });
  },

  updateRoom: async (id: number, data: Partial<Room>): Promise<Room | undefined> => {
    return new Promise((resolve) => {
      setTimeout(() => {
        const index = mockRooms.findIndex(r => r.id === id);
        if (index !== -1) {
          mockRooms[index] = { ...mockRooms[index], ...data };
          resolve(mockRooms[index]);
        } else {
          resolve(undefined);
        }
      }, 300);
    });
  },

  deleteRoom: async (id: number): Promise<boolean> => {
    return new Promise((resolve) => {
      setTimeout(() => {
        const index = mockRooms.findIndex(r => r.id === id);
        if (index !== -1) {
          mockRooms.splice(index, 1);
          resolve(true);
        } else {
          resolve(false);
        }
      }, 300);
    });
  },

  // Bookings
  getBookings: async (): Promise<Booking[]> => {
    return new Promise((resolve) => {
      setTimeout(() => resolve([...mockBookings]), 300);
    });
  },

  updateBooking: async (id: number, data: Partial<Booking>): Promise<Booking | undefined> => {
    return new Promise((resolve) => {
      setTimeout(() => {
        const index = mockBookings.findIndex(b => b.id === id);
        if (index !== -1) {
          mockBookings[index] = {
            ...mockBookings[index],
            ...data,
            updatedAt: new Date().toISOString(),
          };
          resolve(mockBookings[index]);
        } else {
          resolve(undefined);
        }
      }, 300);
    });
  },

  // Tours
  getTours: async (): Promise<Tour[]> => {
    return new Promise((resolve) => {
      setTimeout(() => resolve([...mockTours]), 300);
    });
  },

  createTour: async (data: Omit<Tour, 'id'>): Promise<Tour> => {
    return new Promise((resolve) => {
      setTimeout(() => {
        const newTour: Tour = {
          ...data,
          id: generateId(mockTours),
        };
        mockTours.push(newTour);
        resolve(newTour);
      }, 300);
    });
  },

  updateTour: async (id: number, data: Partial<Tour>): Promise<Tour | undefined> => {
    return new Promise((resolve) => {
      setTimeout(() => {
        const index = mockTours.findIndex(t => t.id === id);
        if (index !== -1) {
          mockTours[index] = { ...mockTours[index], ...data };
          resolve(mockTours[index]);
        } else {
          resolve(undefined);
        }
      }, 300);
    });
  },

  deleteTour: async (id: number): Promise<boolean> => {
    return new Promise((resolve) => {
      setTimeout(() => {
        const index = mockTours.findIndex(t => t.id === id);
        if (index !== -1) {
          mockTours.splice(index, 1);
          resolve(true);
        } else {
          resolve(false);
        }
      }, 300);
    });
  },

  // Restaurants
  getRestaurants: async (): Promise<Restaurant[]> => {
    return new Promise((resolve) => {
      setTimeout(() => resolve([...mockRestaurants]), 300);
    });
  },

  createRestaurant: async (data: Omit<Restaurant, 'id'>): Promise<Restaurant> => {
    return new Promise((resolve) => {
      setTimeout(() => {
        const newRestaurant: Restaurant = {
          ...data,
          id: generateId(mockRestaurants),
        };
        mockRestaurants.push(newRestaurant);
        resolve(newRestaurant);
      }, 300);
    });
  },

  updateRestaurant: async (id: number, data: Partial<Restaurant>): Promise<Restaurant | undefined> => {
    return new Promise((resolve) => {
      setTimeout(() => {
        const index = mockRestaurants.findIndex(r => r.id === id);
        if (index !== -1) {
          mockRestaurants[index] = { ...mockRestaurants[index], ...data };
          resolve(mockRestaurants[index]);
        } else {
          resolve(undefined);
        }
      }, 300);
    });
  },

  deleteRestaurant: async (id: number): Promise<boolean> => {
    return new Promise((resolve) => {
      setTimeout(() => {
        const index = mockRestaurants.findIndex(r => r.id === id);
        if (index !== -1) {
          mockRestaurants.splice(index, 1);
          resolve(true);
        } else {
          resolve(false);
        }
      }, 300);
    });
  },

  // Local Foods
  getLocalFoods: async (): Promise<LocalFood[]> => {
    return new Promise((resolve) => {
      setTimeout(() => resolve([...mockLocalFoods]), 300);
    });
  },

  createLocalFood: async (data: Omit<LocalFood, 'id'>): Promise<LocalFood> => {
    return new Promise((resolve) => {
      setTimeout(() => {
        const newFood: LocalFood = {
          ...data,
          id: generateId(mockLocalFoods),
        };
        mockLocalFoods.push(newFood);
        resolve(newFood);
      }, 300);
    });
  },

  updateLocalFood: async (id: number, data: Partial<LocalFood>): Promise<LocalFood | undefined> => {
    return new Promise((resolve) => {
      setTimeout(() => {
        const index = mockLocalFoods.findIndex(f => f.id === id);
        if (index !== -1) {
          mockLocalFoods[index] = { ...mockLocalFoods[index], ...data };
          resolve(mockLocalFoods[index]);
        } else {
          resolve(undefined);
        }
      }, 300);
    });
  },

  deleteLocalFood: async (id: number): Promise<boolean> => {
    return new Promise((resolve) => {
      setTimeout(() => {
        const index = mockLocalFoods.findIndex(f => f.id === id);
        if (index !== -1) {
          mockLocalFoods.splice(index, 1);
          resolve(true);
        } else {
          resolve(false);
        }
      }, 300);
    });
  },

  // Reviews
  getReviews: async (): Promise<Review[]> => {
    return new Promise((resolve) => {
      setTimeout(() => resolve([...mockReviews]), 300);
    });
  },

  deleteReview: async (id: number): Promise<boolean> => {
    return new Promise((resolve) => {
      setTimeout(() => {
        const index = mockReviews.findIndex(r => r.id === id);
        if (index !== -1) {
          mockReviews.splice(index, 1);
          resolve(true);
        } else {
          resolve(false);
        }
      }, 300);
    });
  },

  // Users
  getUsers: async (): Promise<User[]> => {
    return new Promise((resolve) => {
      setTimeout(() => resolve([...mockUsers]), 300);
    });
  },

  createUser: async (data: Omit<User, 'id' | 'createdAt' | 'updatedAt'>): Promise<User> => {
    return new Promise((resolve) => {
      setTimeout(() => {
        const newUser: User = {
          ...data,
          id: generateId(mockUsers),
          createdAt: new Date().toISOString(),
          updatedAt: new Date().toISOString(),
        };
        mockUsers.push(newUser);
        resolve(newUser);
      }, 300);
    });
  },

  updateUser: async (id: number, data: Partial<User>): Promise<User | undefined> => {
    return new Promise((resolve) => {
      setTimeout(() => {
        const index = mockUsers.findIndex(u => u.id === id);
        if (index !== -1) {
          mockUsers[index] = {
            ...mockUsers[index],
            ...data,
            updatedAt: new Date().toISOString(),
          };
          resolve(mockUsers[index]);
        } else {
          resolve(undefined);
        }
      }, 300);
    });
  },

  deleteUser: async (id: number): Promise<boolean> => {
    return new Promise((resolve) => {
      setTimeout(() => {
        const index = mockUsers.findIndex(u => u.id === id);
        if (index !== -1) {
          mockUsers.splice(index, 1);
          resolve(true);
        } else {
          resolve(false);
        }
      }, 300);
    });
  },

  // Notifications
  getNotifications: async (): Promise<Notification[]> => {
    return new Promise((resolve) => {
      setTimeout(() => resolve([...mockNotifications]), 300);
    });
  },

  createNotification: async (data: Omit<Notification, 'id' | 'createdAt'>): Promise<Notification> => {
    return new Promise((resolve) => {
      setTimeout(() => {
        const newNotification: Notification = {
          ...data,
          id: generateId(mockNotifications),
          createdAt: new Date().toISOString(),
        };
        mockNotifications.push(newNotification);
        resolve(newNotification);
      }, 300);
    });
  },
};
