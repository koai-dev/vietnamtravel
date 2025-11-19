# Travel CMS - Hệ thống quản lý cẩm nang du lịch

CMS (Content Management System) đầy đủ cho website cẩm nang du lịch, được xây dựng với Next.js và Tailwind CSS.

## Tính năng

### Xác thực
- Đăng nhập với email và password
- Quản lý phiên đăng nhập với localStorage
- Bảo mật routes (chỉ admin mới truy cập được)

### Dashboard
- Thống kê tổng quan hệ thống
- Hiển thị các metrics quan trọng (điểm đến, khách sạn, đặt phòng, người dùng, doanh thu)
- Danh sách đặt phòng và đánh giá gần đây

### Quản lý Điểm đến (Destinations)
- Danh sách điểm đến với phân loại (vùng miền, thành phố, điểm tham quan, địa điểm)
- Tạo mới, chỉnh sửa, xóa điểm đến
- Hỗ trợ đa ngôn ngữ (Tiếng Việt, Tiếng Anh)
- Quản lý hình ảnh, tags, giá cả, đánh giá
- Tìm kiếm theo tên, thành phố

### Quản lý Khách sạn (Hotels)
- Danh sách khách sạn với thông tin chi tiết
- Quản lý thông tin: tên, địa chỉ, vị trí GPS, giá, tiện nghi
- Quản lý giờ nhận/trả phòng, chính sách
- Tìm kiếm khách sạn

### Quản lý Phòng (Rooms)
- Quản lý các loại phòng của khách sạn
- Thông tin: loại phòng, số khách, giá, số lượng phòng
- Theo dõi phòng trống

### Quản lý Đặt phòng (Bookings)
- Xem danh sách đặt phòng
- Cập nhật trạng thái: chờ xác nhận, đã xác nhận, đã hủy, hoàn thành
- Theo dõi thông tin khách hàng, khách sạn, ngày, tổng tiền

### Quản lý Tours
- Danh sách tours du lịch
- Thông tin: tên, điểm đến, thời gian, giá
- Tạo mới, chỉnh sửa, xóa tour

### Quản lý Nhà hàng (Restaurants)
- Danh sách nhà hàng và quán ăn
- Thông tin: tên, địa chỉ, vị trí GPS, mô tả
- Liên kết với điểm đến

### Quản lý Món ăn địa phương (Local Foods)
- Danh sách món ăn đặc sản
- Hỗ trợ đa ngôn ngữ
- Hiển thị dạng grid với hình ảnh
- Liên kết với điểm đến

### Quản lý Đánh giá (Reviews)
- Xem danh sách đánh giá của khách hàng
- Hiển thị rating (1-5 sao)
- Xóa đánh giá không phù hợp

### Quản lý Người dùng (Users)
- Danh sách người dùng hệ thống
- Phân quyền: admin, host (chủ khách sạn), user
- Tạo mới, chỉnh sửa, xóa người dùng

### Quản lý Thông báo (Notifications)
- Gửi thông báo đến người dùng
- Phân loại: đặt phòng, đánh giá, hệ thống, khuyến mãi
- Theo dõi trạng thái đã đọc/chưa đọc

## Cấu trúc dự án

```
/
├── App.tsx                          # Component chính
├── services/
│   ├── authContext.tsx              # Context quản lý xác thực
│   └── mockApi.ts                   # Mock API cho tất cả entities
├── components/
│   ├── Sidebar.tsx                  # Sidebar navigation
│   ├── Header.tsx                   # Header với thông tin user
│   ├── Dashboard.tsx                # Dashboard tổng quan
│   ├── auth/
│   │   └── LoginPage.tsx            # Trang đăng nhập
│   ├── destinations/
│   │   ├── DestinationsList.tsx     # Danh sách điểm đến
│   │   └── DestinationForm.tsx      # Form tạo/sửa điểm đến
│   ├── hotels/
│   │   ├── HotelsList.tsx           # Danh sách khách sạn
│   │   └── HotelForm.tsx            # Form tạo/sửa khách sạn
│   ├── rooms/
│   │   ├── RoomsList.tsx            # Danh sách phòng
│   │   └── RoomForm.tsx             # Form tạo/sửa phòng
│   ├── bookings/
│   │   └── BookingsList.tsx         # Danh sách đặt phòng
│   ├── tours/
│   │   └── ToursList.tsx            # Danh sách tours
│   ├── restaurants/
│   │   └── RestaurantsList.tsx      # Danh sách nhà hàng
│   ├── localFoods/
│   │   └── LocalFoodsList.tsx       # Danh sách món ăn
│   ├── reviews/
│   │   └── ReviewsList.tsx          # Danh sách đánh giá
│   ├── users/
│   │   └── UsersList.tsx            # Danh sách người dùng
│   └── notifications/
│       └── NotificationsList.tsx    # Danh sách thông báo
└── styles/
    └── globals.css                  # Styles toàn cục
```

## Database Models

CMS được thiết kế để tương thích với backend Ktor với các models sau:

- **Users**: Người dùng (user, host, admin)
- **Destinations**: Điểm đến (region, city, attraction, spot)
- **Hotels**: Khách sạn
- **Rooms**: Phòng khách sạn
- **Bookings**: Đặt phòng
- **Tours**: Tours du lịch
- **Restaurants**: Nhà hàng
- **LocalFoods**: Món ăn địa phương
- **RestaurantLocalFoods**: Liên kết nhà hàng - món ăn
- **Reviews**: Đánh giá
- **Notifications**: Thông báo
- **Favorites**: Yêu thích
- **HotelImages**: Hình ảnh khách sạn
- **UserTracking**: Theo dõi người dùng

## Hướng dẫn sử dụng

### Đăng nhập
1. Mở ứng dụng
2. Nhập email và password bất kỳ (demo mode)
3. Nhấn "Đăng nhập"

Ví dụ:
- Email: admin@travel.com
- Password: bất kỳ

### Quản lý dữ liệu
1. Chọn menu bên trái để điều hướng
2. Sử dụng nút "Thêm mới" để tạo record
3. Nhấn biểu tượng "Sửa" để chỉnh sửa
4. Nhấn biểu tượng "Xóa" để xóa record
5. Sử dụng thanh tìm kiếm để lọc dữ liệu

## Công nghệ sử dụng

- **React**: UI library
- **Next.js**: React framework
- **Tailwind CSS**: Utility-first CSS framework
- **Lucide React**: Icon library
- **TypeScript**: Type safety

## Mock Data

Hệ thống sử dụng mock data trong `services/mockApi.ts`. Tất cả thao tác CRUD được mô phỏng với setTimeout để giống API thực.

Để kết nối với backend thực:
1. Thay thế các hàm trong `mockApi.ts` bằng fetch/axios calls
2. Cập nhật URL endpoints tương ứng
3. Xử lý authentication tokens
4. Cập nhật error handling

## Ghi chú

- Đây là phiên bản demo với mock data
- Dữ liệu sẽ reset khi refresh trang
- Để sử dụng production, cần kết nối với backend API thực
