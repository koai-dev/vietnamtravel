import React, { useEffect, useState } from 'react';
import { mockApi, LocalFood } from '../../services/mockApi';
import { Plus, Edit, Trash2, Coffee } from 'lucide-react';

export const LocalFoodsList: React.FC = () => {
  const [localFoods, setLocalFoods] = useState<LocalFood[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    loadLocalFoods();
  }, []);

  const loadLocalFoods = async () => {
    setLoading(true);
    try {
      const data = await mockApi.getLocalFoods();
      setLocalFoods(data);
    } finally {
      setLoading(false);
    }
  };

  const handleDelete = async (id: number) => {
    if (confirm('Bạn có chắc chắn muốn xóa món ăn này?')) {
      await mockApi.deleteLocalFood(id);
      loadLocalFoods();
    }
  };

  return (
    <div className="space-y-6">
      <div className="flex items-center justify-between">
        <div>
          <div className="text-gray-900 text-2xl mb-2">Quản lý món ăn địa phương</div>
          <p className="text-gray-600">Quản lý các món ăn đặc sản địa phương</p>
        </div>
        <button className="flex items-center gap-2 bg-blue-600 text-white px-4 py-2 rounded-lg hover:bg-blue-700 transition-colors">
          <Plus className="w-5 h-5" />
          <span>Thêm món ăn</span>
        </button>
      </div>

      <div className="bg-white rounded-xl shadow-sm border border-gray-200">
        {loading ? (
          <div className="flex items-center justify-center py-12">
            <div className="text-gray-500">Đang tải...</div>
          </div>
        ) : (
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6 p-6">
            {localFoods.map((food) => (
              <div key={food.id} className="border border-gray-200 rounded-xl overflow-hidden hover:shadow-lg transition-shadow">
                {food.images && food.images.length > 0 ? (
                  <img src={food.images[0]} alt={food.nameVi} className="w-full h-48 object-cover" />
                ) : (
                  <div className="w-full h-48 bg-gray-100 flex items-center justify-center">
                    <Coffee className="w-12 h-12 text-gray-400" />
                  </div>
                )}
                <div className="p-4">
                  <div className="text-gray-900 mb-1">{food.nameVi}</div>
                  <div className="text-sm text-gray-500 mb-2">{food.nameEn}</div>
                  <div className="text-xs text-gray-600 mb-3 line-clamp-2">{food.descriptionVi}</div>
                  <div className="text-xs text-blue-600 mb-4">{food.destinationName}</div>
                  <div className="flex items-center gap-2">
                    <button className="flex-1 px-3 py-1.5 text-blue-600 border border-blue-600 rounded-lg hover:bg-blue-50 transition-colors text-sm">
                      <Edit className="w-3.5 h-3.5 inline mr-1" />
                      Sửa
                    </button>
                    <button
                      onClick={() => handleDelete(food.id)}
                      className="flex-1 px-3 py-1.5 text-red-600 border border-red-600 rounded-lg hover:bg-red-50 transition-colors text-sm"
                    >
                      <Trash2 className="w-3.5 h-3.5 inline mr-1" />
                      Xóa
                    </button>
                  </div>
                </div>
              </div>
            ))}
          </div>
        )}
        {!loading && localFoods.length === 0 && (
          <div className="text-center py-12 text-gray-500">Chưa có món ăn nào</div>
        )}
      </div>
    </div>
  );
};
