// @ts-ignore
import React, { useState } from 'react';
import { Upload, Link as LinkIcon, X, Loader2 } from 'lucide-react';
import api from '../../services/api';

interface ImageInputProps {
    value: string[]; // Array of image URLs
    onChange: (urls: string[]) => void;
    label?: string;
    placeholder?: string;
}

export const ImageInput: React.FC<ImageInputProps> = ({
    value,
    onChange,
    label = 'Hình ảnh',
    placeholder = 'https://example.com/image1.jpg\nhttps://example.com/image2.jpg',
}) => {
    const [mode, setMode] = useState<'url' | 'upload'>('url');
    const [urlText, setUrlText] = useState(value.join('\n'));
    const [imageErrors, setImageErrors] = useState<Set<string>>(new Set());
    const [uploading, setUploading] = useState(false);
    const [uploadProgress, setUploadProgress] = useState<string>('');

    // Update urlText when value changes from parent
    React.useEffect(() => {
        setUrlText(value.join('\n'));
    }, [value]);

    const handleUrlChange = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
        const text = e.target.value;
        setUrlText(text);

        // Parse URLs and update parent
        const urls = text
            .split('\n')
            .map(url => url.trim())
            .filter(url => url.length > 0);

        onChange(urls);
    };

    const handleFileUpload = async (files: FileList | null) => {
        if (!files || files.length === 0) return;

        setUploading(true);
        const uploadedUrls: string[] = [];

        try {
            for (let i = 0; i < files.length; i++) {
                const file = files[i];
                setUploadProgress(`Đang upload ${i + 1}/${files.length}: ${file.name}`);

                const formData = new FormData();
                formData.append('file', file);

                const response = await api.post('/api/uploads', formData, {
                    headers: {
                        'Content-Type': 'multipart/form-data',
                    },
                });

                if (response.data && response.data.url) {
                    uploadedUrls.push(response.data.url);
                }
            }

            // Add uploaded URLs to existing URLs
            onChange([...value, ...uploadedUrls]);
            setUploadProgress('');
        } catch (error) {
            console.error('Upload error:', error);
            alert('Có lỗi xảy ra khi upload ảnh');
            setUploadProgress('');
        } finally {
            setUploading(false);
        }
    };

    const handleImageLoad = (url: string) => {
        setImageErrors(prev => {
            const newErrors = new Set(prev);
            newErrors.delete(url);
            return newErrors;
        });
    };

    const handleImageError = (url: string) => {
        setImageErrors(prev => new Set(prev).add(url));
    };

    const removeImage = (urlToRemove: string) => {
        const newUrls = value.filter(url => url !== urlToRemove);
        onChange(newUrls);
    };

    return (
        <div className="space-y-3">
            {/* Label and Toggle */}
            <div className="flex items-center justify-between">
                <label className="block text-sm text-gray-700">{label}</label>
                <div className="flex items-center gap-2 bg-gray-100 rounded-lg p-1">
                    <button
                        type="button"
                        onClick={() => setMode('url')}
                        className={`flex items-center gap-1 px-3 py-1.5 rounded-md text-sm transition-colors ${mode === 'url'
                            ? 'bg-white text-blue-600 shadow-sm'
                            : 'text-gray-600 hover:text-gray-900'
                            }`}
                    >
                        <LinkIcon className="w-4 h-4" />
                        <span>URL</span>
                    </button>
                    <button
                        type="button"
                        onClick={() => setMode('upload')}
                        className={`flex items-center gap-1 px-3 py-1.5 rounded-md text-sm transition-colors ${mode === 'upload'
                            ? 'bg-white text-blue-600 shadow-sm'
                            : 'text-gray-600 hover:text-gray-900'
                            }`}
                    >
                        <Upload className="w-4 h-4" />
                        <span>Upload</span>
                    </button>
                </div>
            </div>

            {/* URL Input Mode */}
            {mode === 'url' && (
                <div className="space-y-3">
                    <textarea
                        value={urlText}
                        onChange={handleUrlChange}
                        rows={4}
                        placeholder={placeholder}
                        className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 font-mono text-sm"
                    />
                    <p className="text-xs text-gray-500">Nhập mỗi URL trên một dòng</p>
                </div>
            )}

            {/* Upload Mode */}
            {mode === 'upload' && (
                <div className="space-y-3">
                    <div className="border-2 border-dashed border-gray-300 rounded-lg p-8 text-center bg-gray-50 hover:border-blue-400 transition-colors">
                        <input
                            type="file"
                            accept="image/*"
                            multiple
                            onChange={(e) => handleFileUpload(e.target.files)}
                            className="hidden"
                            id="file-upload"
                            disabled={uploading}
                        />
                        <label
                            htmlFor="file-upload"
                            className={`cursor-pointer ${uploading ? 'opacity-50 cursor-not-allowed' : ''}`}
                        >
                            {uploading ? (
                                <>
                                    <Loader2 className="w-12 h-12 text-blue-500 mx-auto mb-3 animate-spin" />
                                    <p className="text-gray-600 mb-1">{uploadProgress}</p>
                                    <p className="text-sm text-gray-500">Vui lòng đợi...</p>
                                </>
                            ) : (
                                <>
                                    <Upload className="w-12 h-12 text-gray-400 mx-auto mb-3" />
                                    <p className="text-gray-600 mb-1">Click để chọn ảnh hoặc kéo thả vào đây</p>
                                    <p className="text-sm text-gray-500">Hỗ trợ nhiều ảnh cùng lúc</p>
                                </>
                            )}
                        </label>
                    </div>
                </div>
            )}

            {/* Image Preview Grid */}
            {value.length > 0 && (
                <div className="space-y-2">
                    <div className="text-sm text-gray-700 font-medium">
                        Preview ({value.length} {value.length === 1 ? 'ảnh' : 'ảnh'})
                    </div>
                    <div className="grid grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-3">
                        {value.map((url, index) => (
                            <div
                                key={index}
                                className="relative group aspect-square bg-gray-100 rounded-lg overflow-hidden border border-gray-200"
                            >
                                {!imageErrors.has(url) ? (
                                    <>
                                        <img
                                            src={url}
                                            alt={`Preview ${index + 1}`}
                                            className="w-full h-full object-cover"
                                            onLoad={() => handleImageLoad(url)}
                                            onError={() => handleImageError(url)}
                                        />
                                        <button
                                            type="button"
                                            onClick={() => removeImage(url)}
                                            className="absolute top-1 right-1 p-1 bg-red-500 text-white rounded-full opacity-0 group-hover:opacity-100 transition-opacity hover:bg-red-600"
                                            title="Xóa ảnh"
                                        >
                                            <X className="w-4 h-4" />
                                        </button>
                                    </>
                                ) : (
                                    <div className="w-full h-full flex flex-col items-center justify-center p-2">
                                        <div className="text-red-500 text-xs text-center mb-2">
                                            ❌ Không tải được
                                        </div>
                                        <div className="text-xs text-gray-500 break-all line-clamp-3">
                                            {url}
                                        </div>
                                        <button
                                            type="button"
                                            onClick={() => removeImage(url)}
                                            className="mt-2 text-xs text-red-600 hover:text-red-700 underline"
                                        >
                                            Xóa
                                        </button>
                                    </div>
                                )}
                            </div>
                        ))}
                    </div>
                </div>
            )}
        </div>
    );
};
