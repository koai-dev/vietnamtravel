// @ts-ignore
import React, { useState, useEffect, useRef } from 'react';
import { destinationApi, Destination } from '../../services/destinationApi';
import { Search, X } from 'lucide-react';

interface ParentDestinationAutocompleteProps {
    currentType: 'region' | 'city' | 'attraction' | 'spot';
    value: number | null;
    onChange: (id: number | null) => void;
    initialParent?: Destination | null;
}

export const ParentDestinationAutocomplete: React.FC<ParentDestinationAutocompleteProps> = ({
    currentType,
    value,
    onChange,
    initialParent,
}) => {
    const [query, setQuery] = useState('');
    const [suggestions, setSuggestions] = useState<Destination[]>([]);
    const [loading, setLoading] = useState(false);
    const [isOpen, setIsOpen] = useState(false);
    const [selectedParent, setSelectedParent] = useState<Destination | null>(initialParent || null);
    const wrapperRef = useRef<HTMLDivElement>(null);

    useEffect(() => {
        if (initialParent) {
            setSelectedParent(initialParent);
            setQuery(initialParent.nameVi);
        }
    }, [initialParent]);

    useEffect(() => {
        const handleClickOutside = (event: MouseEvent) => {
            if (wrapperRef.current && !wrapperRef.current.contains(event.target as Node)) {
                setIsOpen(false);
            }
        };
        document.addEventListener('mousedown', handleClickOutside);
        return () => document.removeEventListener('mousedown', handleClickOutside);
    }, []);

    const getAllowedParentTypes = () => {
        switch (currentType) {
            case 'region':
                return [];
            case 'city':
                return ['region'];
            case 'attraction':
                return ['city', 'region'];
            case 'spot':
                return ['attraction', 'city', 'region'];
            default:
                return [];
        }
    };

    const handleSearch = async (searchQuery: string) => {
        setQuery(searchQuery);
        if (!searchQuery.trim()) {
            setSuggestions([]);
            return;
        }

        setLoading(true);
        try {
            const types = getAllowedParentTypes();
            if (types.length === 0) return;

            const results = await destinationApi.searchDestinations(searchQuery, types);
            setSuggestions(results.data || []);
            setIsOpen(true);
        } catch (error) {
            console.error('Error searching destinations:', error);
        } finally {
            setLoading(false);
        }
    };

    const handleSelect = (destination: Destination) => {
        setSelectedParent(destination);
        setQuery(destination.nameVi);
        onChange(destination.id);
        setIsOpen(false);
    };

    const handleClear = () => {
        setSelectedParent(null);
        setQuery('');
        onChange(null);
    };

    if (currentType === 'region') {
        return null;
    }

    return (
        <div className="relative" ref={wrapperRef}>
            <label className="block text-sm text-gray-700 mb-2">Điểm đến cha</label>
            <div className="relative">
                <Search className="absolute left-3 top-1/2 -translate-y-1/2 w-5 h-5 text-gray-400" />
                <input
                    type="text"
                    value={query}
                    onChange={(e) => handleSearch(e.target.value)}
                    onFocus={() => query && setIsOpen(true)}
                    placeholder="Tìm kiếm điểm đến cha..."
                    className="w-full pl-10 pr-10 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                />
                {selectedParent && (
                    <button
                        onClick={handleClear}
                        className="absolute right-3 top-1/2 -translate-y-1/2 text-gray-400 hover:text-gray-600"
                    >
                        <X className="w-5 h-5" />
                    </button>
                )}
            </div>

            {isOpen && suggestions.length > 0 && (
                <div className="absolute z-10 w-full mt-1 bg-white border border-gray-200 rounded-lg shadow-lg max-h-60 overflow-y-auto">
                    {suggestions.map((destination) => (
                        <button
                            key={destination.id}
                            onClick={() => handleSelect(destination)}
                            className="w-full px-4 py-2 text-left hover:bg-gray-50 flex items-center justify-between"
                        >
                            <div>
                                <div className="text-sm font-medium text-gray-900">{destination.nameVi}</div>
                                <div className="text-xs text-gray-500">{destination.city || destination.type}</div>
                            </div>
                            {destination.id === value && (
                                <span className="text-blue-600 text-sm">Đã chọn</span>
                            )}
                        </button>
                    ))}
                </div>
            )}
        </div>
    );
};
