// @ts-ignore
import React, { useState } from 'react';
import { destinationApi, DestinationDetail } from '../../services/destinationApi';
import { X, Save } from 'lucide-react';
import { ParentDestinationAutocomplete } from './ParentDestinationAutocomplete';
import { ImageInput } from '../common/ImageInput';

interface DestinationFormProps {
  destination: DestinationDetail | null;
  onClose: () => void;
}

export const DestinationForm: React.FC<DestinationFormProps> = ({ destination, onClose }) => {
  const [formData, setFormData] = useState({
    nameVi: destination?.nameVi || '',
    nameEn: destination?.nameEn || '',
    descriptionVi: destination?.descriptionVi || '',
    descriptionEn: destination?.descriptionEn || '',
    type: destination?.type || 'city' as 'region' | 'city' | 'attraction' | 'spot',
    city: destination?.city || '',
    address: destination?.address || '',
    latitude: destination?.latitude || 0,
    longitude: destination?.longitude || 0,
    slug: destination?.slug || '',
    tags: destination?.tags?.join(', ') || '',
    bestTimeToVisit: destination?.bestTimeToVisit || '',
    openingHours: destination?.openingHours || '',
    priceFrom: destination?.priceFrom || 0,
    priceTo: destination?.priceTo || 0,
    status: destination?.status || 'ACTIVE' as 'ACTIVE' | 'INACTIVE' | 'DRAFT',
    sortOrder: destination?.sortOrder || 0,
    images: destination?.images || [],
    parentId: destination?.parentId || null,
    addressLink: destination?.addressLink || '',
    externalLinks: destination?.externalLinks?.join('\n') || '',
  });

  const [errors, setErrors] = useState<Record<string, string>>({});

  const generateSlug = (str: string) => {
    return str
      .toLowerCase()
      .normalize('NFD')
      .replace(/[\u0300-\u036f]/g, '')
      .replace(/[đĐ]/g, 'd')
      .replace(/[^a-z0-9\s-]/g, '')
      .replace(/\s+/g, '-')
      .replace(/-+/g, '-')
      .replace(/^-+|-+$/g, '');
  };

  React.useEffect(() => {
    if (!destination && formData.nameVi) {
      setFormData(prev => ({ ...prev, slug: generateSlug(prev.nameVi) }));
    }
  }, [formData.nameVi, destination]);

  const [loading, setLoading] = useState(false);
  const [initialParent, setInitialParent] = useState<any>(null);

  React.useEffect(() => {
    const fetchParent = async () => {
      if (destination?.parentId) {
        try {
          const parent = await destinationApi.getDestination(destination.parentId);
          setInitialParent(parent);
        } catch (error) {
          console.error('Error loading parent destination:', error);
        }
      }
    };
    fetchParent().then(r => { });
  }, [destination]);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement | HTMLSelectElement>) => {
    const { name, value } = e.target;
    setFormData(prev => ({ ...prev, [name]: value }));
    if (errors[name]) {
      setErrors(prev => {
        const newErrors = { ...prev };
        delete newErrors[name];
        return newErrors;
      });
    }
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    // Validate
    const newErrors: Record<string, string> = {};
    if (!formData.nameVi.trim()) newErrors.nameVi = 'Vui lòng nhập tên Tiếng Việt';
    if (!formData.nameEn.trim()) newErrors.nameEn = 'Vui lòng nhập tên Tiếng Anh';
    if (!formData.type) newErrors.type = 'Vui lòng chọn loại';
    if (!formData.status) newErrors.status = 'Vui lòng chọn trạng thái';

    if (Object.keys(newErrors).length > 0) {
      setErrors(newErrors);
      return;
    }

    setLoading(true);

    try {
      const data = {
        nameVi: formData.nameVi,
        nameEn: formData.nameEn,
        descriptionVi: formData.descriptionVi || undefined,
        descriptionEn: formData.descriptionEn || undefined,
        type: formData.type,
        city: formData.city || undefined,
        address: formData.address || undefined,
        latitude: formData.latitude || undefined,
        longitude: formData.longitude || undefined,
        slug: formData.slug || generateSlug(formData.nameVi),
        tags: formData.tags ? formData.tags.split(',').map(t => t.trim()) : undefined,
        bestTimeToVisit: formData.bestTimeToVisit || undefined,
        openingHours: formData.openingHours || undefined,
        priceFrom: formData.priceFrom || undefined,
        priceTo: formData.priceTo || undefined,
        status: formData.status,
        sortOrder: formData.sortOrder,
        images: formData.images.length > 0 ? formData.images : undefined,
        avgRating: destination?.avgRating || 0,
        reviewCount: destination?.reviewCount || 0,
        viewsCount: destination?.viewsCount || 0,
        favoritesCount: destination?.favoritesCount || 0,
        parentId: formData.parentId || undefined,
        addressLink: formData.addressLink || undefined,
        externalLinks: formData.externalLinks ? formData.externalLinks.split('\n').filter(l => l.trim()) : undefined,
      };

      if (destination) {
        await destinationApi.updateDestination(destination.id, data);
      } else {
        await destinationApi.createDestination(data);
      }

      onClose();
    } catch (error) {
      console.error('Error saving destination:', error);
      alert('Có lỗi xảy ra khi lưu điểm đến');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="space-y-6">
      <div className="flex items-center justify-between">
        <div>
          <div className="text-gray-900 text-2xl mb-2">
            {destination ? 'Chỉnh sửa điểm đến' : 'Thêm điểm đến mới'}
          </div>
          <p className="text-gray-600">Điền thông tin chi tiết về điểm đến</p>
        </div>
        <button
          onClick={onClose}
          className="p-2 hover:bg-gray-100 rounded-lg transition-colors"
        >
          <X className="w-6 h-6 text-gray-600" />
        </button>
      </div>

      <form onSubmit={handleSubmit} className="bg-white rounded-xl shadow-sm border border-gray-200 p-6">
        <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
          <div>
            <label className="block text-sm text-gray-700 mb-2">Tên (Tiếng Việt) *</label>
            <input
              type="text"
              name="nameVi"
              value={formData.nameVi}
              onChange={handleChange}
              className={`w-full px-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 ${errors.nameVi ? 'border-red-500' : 'border-gray-300'}`}
              required
            />
            {errors.nameVi && <p className="text-red-500 text-xs mt-1">{errors.nameVi}</p>}
          </div>

          <div>
            <label className="block text-sm text-gray-700 mb-2">Tên (Tiếng Anh) *</label>
            <input
              type="text"
              name="nameEn"
              value={formData.nameEn}
              onChange={handleChange}
              className={`w-full px-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 ${errors.nameEn ? 'border-red-500' : 'border-gray-300'}`}
              required
            />
            {errors.nameEn && <p className="text-red-500 text-xs mt-1">{errors.nameEn}</p>}
          </div>

          <div className="md:col-span-2">
            <label className="block text-sm text-gray-700 mb-2">Mô tả (Tiếng Việt)</label>
            <textarea
              name="descriptionVi"
              value={formData.descriptionVi}
              onChange={handleChange}
              rows={3}
              className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
          </div>

          <div className="md:col-span-2">
            <label className="block text-sm text-gray-700 mb-2">Mô tả (Tiếng Anh)</label>
            <textarea
              name="descriptionEn"
              value={formData.descriptionEn}
              onChange={handleChange}
              rows={3}
              className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
          </div>

          <div>
            <label className="block text-sm text-gray-700 mb-2">Loại *</label>
            <select
              name="type"
              value={formData.type}
              onChange={handleChange}
              className={`w-full px-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 ${errors.type ? 'border-red-500' : 'border-gray-300'}`}
              required
            >
              <option value="region">Vùng miền</option>
              <option value="city">Thành phố</option>
              <option value="attraction">Điểm tham quan</option>
              <option value="spot">Địa điểm</option>
            </select>
          </div>

          <div>
            <ParentDestinationAutocomplete
              currentType={formData.type}
              value={formData.parentId}
              onChange={(id) => setFormData(prev => ({ ...prev, parentId: id }))}
              initialParent={initialParent}
            />
          </div>

          <div>
            <label className="block text-sm text-gray-700 mb-2">Thành phố</label>
            <input
              type="text"
              name="city"
              value={formData.city}
              onChange={handleChange}
              className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
          </div>

          <div className="md:col-span-2">
            <label className="block text-sm text-gray-700 mb-2">Địa chỉ</label>
            <input
              type="text"
              name="address"
              value={formData.address}
              onChange={handleChange}
              className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
          </div>

          <div className="md:col-span-2">
            <label className="block text-sm text-gray-700 mb-2">Link Google Maps</label>
            <input
              type="text"
              name="addressLink"
              value={formData.addressLink}
              onChange={handleChange}
              placeholder="https://goo.gl/maps/..."
              className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
          </div>

          <div>
            <label className="block text-sm text-gray-700 mb-2">Vĩ độ</label>
            <input
              type="number"
              step="0.000001"
              name="latitude"
              value={formData.latitude}
              onChange={handleChange}
              className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
          </div>

          <div>
            <label className="block text-sm text-gray-700 mb-2">Kinh độ</label>
            <input
              type="number"
              step="0.000001"
              name="longitude"
              value={formData.longitude}
              onChange={handleChange}
              className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
          </div>

          <div>
            <label className="block text-sm text-gray-700 mb-2">Slug</label>
            <input
              type="text"
              name="slug"
              value={formData.slug}
              onChange={handleChange}
              className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
          </div>

          <div>
            <label className="block text-sm text-gray-700 mb-2">Tags (phân cách bằng dấu phẩy)</label>
            <input
              type="text"
              name="tags"
              value={formData.tags}
              onChange={handleChange}
              placeholder="văn hóa, lịch sử, ẩm thực"
              className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
          </div>

          <div>
            <label className="block text-sm text-gray-700 mb-2">Thời gian tốt nhất</label>
            <input
              type="text"
              name="bestTimeToVisit"
              value={formData.bestTimeToVisit}
              onChange={handleChange}
              placeholder="Quanh năm"
              className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
          </div>

          <div>
            <label className="block text-sm text-gray-700 mb-2">Giờ mở cửa</label>
            <input
              type="text"
              name="openingHours"
              value={formData.openingHours}
              onChange={handleChange}
              placeholder="8:00 - 17:00"
              className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
          </div>

          <div>
            <label className="block text-sm text-gray-700 mb-2">Giá từ (VNĐ)</label>
            <input
              type="number"
              name="priceFrom"
              value={formData.priceFrom}
              onChange={handleChange}
              className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
          </div>

          <div>
            <label className="block text-sm text-gray-700 mb-2">Giá đến (VNĐ)</label>
            <input
              type="number"
              name="priceTo"
              value={formData.priceTo}
              onChange={handleChange}
              className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
          </div>

          <div>
            <label className="block text-sm text-gray-700 mb-2">Trạng thái</label>
            <select
              name="status"
              value={formData.status}
              onChange={handleChange}
              className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
            >
              <option value="ACTIVE">Hoạt động</option>
              <option value="INACTIVE">Tạm dừng</option>
              <option value="DRAFT">Nháp</option>
            </select>
          </div>

          <div>
            <label className="block text-sm text-gray-700 mb-2">Thứ tự sắp xếp</label>
            <input
              type="number"
              name="sortOrder"
              value={formData.sortOrder}
              onChange={handleChange}
              className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
          </div>

          <div className="md:col-span-2">
            <ImageInput
              value={formData.images}
              onChange={(urls) => setFormData(prev => ({ ...prev, images: urls }))}
              label="Hình ảnh"
              placeholder="https://example.com/image1.jpg&#10;https://example.com/image2.jpg"
            />
          </div>

          <div className="md:col-span-2">
            <label className="block text-sm text-gray-700 mb-2">Liên kết ngoài (mỗi URL trên 1 dòng)</label>
            <textarea
              name="externalLinks"
              value={formData.externalLinks}
              onChange={handleChange}
              rows={3}
              placeholder="https://wikipedia.org/...&#10;https://tripadvisor.com/..."
              className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
          </div>
        </div>

        <div className="flex items-center gap-3 mt-6 pt-6 border-t border-gray-200">
          <button
            type="submit"
            disabled={loading}
            className="flex items-center gap-2 bg-blue-600 text-white px-6 py-2 rounded-lg hover:bg-blue-700 transition-colors disabled:opacity-50"
          >
            <Save className="w-5 h-5" />
            <span>{loading ? 'Đang lưu...' : 'Lưu điểm đến'}</span>
          </button>
          <button
            type="button"
            onClick={onClose}
            className="px-6 py-2 border border-gray-300 rounded-lg hover:bg-gray-50 transition-colors"
          >
            Hủy
          </button>
        </div>
      </form>
    </div>
  );
};
