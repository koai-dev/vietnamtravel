// @ts-ignore
import React, { useState } from 'react';
import { Send, Plus, Trash2, History, Save } from 'lucide-react';
import api from '../../services/api';

interface Header {
    key: string;
    value: string;
}

interface ResponseData {
    status: number;
    statusText: string;
    data: any;
    headers: any;
    time: number;
    size: number;
}

export const PostmanTool: React.FC = () => {
    const [method, setMethod] = useState('GET');
    const [url, setUrl] = useState('/api/');
    const [headers, setHeaders] = useState<Header[]>([{ key: 'Content-Type', value: 'application/json' }]);
    const [body, setBody] = useState('');
    const [response, setResponse] = useState<ResponseData | null>(null);
    const [loading, setLoading] = useState(false);
    const [activeTab, setActiveTab] = useState<'params' | 'headers' | 'body'>('params');

    const methods = ['GET', 'POST', 'PUT', 'DELETE', 'PATCH'];

    const handleAddHeader = () => {
        setHeaders([...headers, { key: '', value: '' }]);
    };

    const handleRemoveHeader = (index: number) => {
        setHeaders(headers.filter((_, i) => i !== index));
    };

    const handleHeaderChange = (index: number, field: 'key' | 'value', value: string) => {
        const newHeaders = [...headers];
        newHeaders[index][field] = value;
        setHeaders(newHeaders);
    };

    const handleSend = async () => {
        setLoading(true);
        setResponse(null);
        const startTime = Date.now();

        try {
            const headerObj = headers.reduce((acc, curr) => {
                if (curr.key) acc[curr.key] = curr.value;
                return acc;
            }, {} as any);

            let parsedBody = undefined;
            if (body && ['POST', 'PUT', 'PATCH'].includes(method)) {
                try {
                    parsedBody = JSON.parse(body);
                } catch (e) {
                    alert('Invalid JSON body');
                    setLoading(false);
                    return;
                }
            }

            const res = await api.request({
                method,
                url,
                headers: headerObj,
                data: parsedBody,
            });

            const endTime = Date.now();
            setResponse({
                status: res.status,
                statusText: res.statusText,
                data: res.data,
                headers: res.headers,
                time: endTime - startTime,
                size: JSON.stringify(res.data).length,
            });
        } catch (error: any) {
            const endTime = Date.now();
            setResponse({
                status: error.response?.status || 0,
                statusText: error.response?.statusText || error.message,
                data: error.response?.data || null,
                headers: error.response?.headers || {},
                time: endTime - startTime,
                size: 0,
            });
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="h-full flex flex-col gap-4">
            <div className="flex items-center justify-between">
                <h1 className="text-2xl font-bold text-gray-900">API Tester</h1>
            </div>

            <div className="bg-white rounded-xl shadow-sm border border-gray-200 p-4 flex flex-col gap-4">
                {/* Request Bar */}
                <div className="flex gap-2">
                    <select
                        value={method}
                        onChange={(e) => setMethod(e.target.value)}
                        className="px-4 py-2 border border-gray-300 rounded-lg font-semibold min-w-[100px] focus:outline-none focus:ring-2 focus:ring-blue-500"
                    >
                        {methods.map(m => <option key={m} value={m}>{m}</option>)}
                    </select>
                    <input
                        type="text"
                        value={url}
                        onChange={(e) => setUrl(e.target.value)}
                        placeholder="Enter request URL"
                        className="flex-1 px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 font-mono"
                    />
                    <button
                        onClick={handleSend}
                        disabled={loading}
                        className="px-6 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700 transition-colors flex items-center gap-2 disabled:opacity-50"
                    >
                        <Send className="w-4 h-4" />
                        {loading ? 'Sending...' : 'Send'}
                    </button>
                </div>

                {/* Request Details */}
                <div className="border-t border-gray-200 pt-4">
                    <div className="flex gap-4 mb-4 border-b border-gray-200">
                        {['Params', 'Headers', 'Body'].map((tab) => (
                            <button
                                key={tab}
                                onClick={() => setActiveTab(tab.toLowerCase() as any)}
                                className={`pb-2 px-2 ${activeTab === tab.toLowerCase()
                                        ? 'border-b-2 border-blue-600 text-blue-600 font-medium'
                                        : 'text-gray-500 hover:text-gray-700'
                                    }`}
                            >
                                {tab}
                            </button>
                        ))}
                    </div>

                    <div className="min-h-[200px]">
                        {activeTab === 'headers' && (
                            <div className="space-y-2">
                                {headers.map((header, index) => (
                                    <div key={index} className="flex gap-2">
                                        <input
                                            type="text"
                                            placeholder="Key"
                                            value={header.key}
                                            onChange={(e) => handleHeaderChange(index, 'key', e.target.value)}
                                            className="flex-1 px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                                        />
                                        <input
                                            type="text"
                                            placeholder="Value"
                                            value={header.value}
                                            onChange={(e) => handleHeaderChange(index, 'value', e.target.value)}
                                            className="flex-1 px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                                        />
                                        <button
                                            onClick={() => handleRemoveHeader(index)}
                                            className="p-2 text-red-500 hover:bg-red-50 rounded-lg"
                                        >
                                            <Trash2 className="w-4 h-4" />
                                        </button>
                                    </div>
                                ))}
                                <button
                                    onClick={handleAddHeader}
                                    className="flex items-center gap-2 text-blue-600 hover:text-blue-700 font-medium mt-2"
                                >
                                    <Plus className="w-4 h-4" /> Add Header
                                </button>
                            </div>
                        )}

                        {activeTab === 'body' && (
                            <div className="h-full">
                                <textarea
                                    value={body}
                                    onChange={(e) => setBody(e.target.value)}
                                    placeholder="Request body (JSON)"
                                    className="w-full h-[200px] p-4 border border-gray-300 rounded-lg font-mono text-sm focus:outline-none focus:ring-2 focus:ring-blue-500"
                                />
                            </div>
                        )}

                        {activeTab === 'params' && (
                            <div className="text-gray-500 italic p-4 text-center">
                                Query params editing coming soon. Add them directly to the URL for now.
                            </div>
                        )}
                    </div>
                </div>
            </div>

            {/* Response */}
            {response && (
                <div className="bg-white rounded-xl shadow-sm border border-gray-200 p-4 flex-1 flex flex-col min-h-0">
                    <div className="flex items-center justify-between mb-4">
                        <div className="flex items-center gap-4">
                            <span className={`font-bold ${response.status >= 200 && response.status < 300 ? 'text-green-600' : 'text-red-600'}`}>
                                {response.status} {response.statusText}
                            </span>
                            <span className="text-gray-500 text-sm">Time: {response.time}ms</span>
                            <span className="text-gray-500 text-sm">Size: {response.size} B</span>
                        </div>
                    </div>
                    <div className="flex-1 overflow-auto border border-gray-200 rounded-lg bg-gray-50 p-4">
                        <pre className="font-mono text-sm whitespace-pre-wrap break-words">
                            {JSON.stringify(response.data, null, 2)}
                        </pre>
                    </div>
                </div>
            )}
        </div>
    );
};
