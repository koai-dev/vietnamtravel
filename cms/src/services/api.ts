import axios from "axios";

const api = axios.create({
  baseURL: "/api", // This will be the Next.js API route
});

// Request interceptor to add token to headers
api.interceptors.request.use(
  (config) => {
    // In a real application, you would get the token from a secure place
    // For now, we'll assume it's handled by the BFF
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

// Response interceptor to handle token refresh
api.interceptors.response.use(
  (response) => {
    return response;
  },
  async (error) => {
    const originalRequest = error.config;
    if (error.response.status === 401 && !originalRequest._retry) {
      originalRequest._retry = true;
      // In a real application, you would call the refresh token endpoint
      // and then retry the original request
      // For now, we'll just reject the promise
      return Promise.reject(error);
    }
    return Promise.reject(error);
  }
);

export default api;
