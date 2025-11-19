import React, { createContext, useContext, useState, useEffect } from 'react';
import * as api from './api';

interface User {
  id: number;
  email: string;
  name: string;
  role: 'user' | 'host' | 'admin';
  avatarUrl?: string;
}

interface AuthContextType {
  user: User | null;
  login: (email: string, password: string) => Promise<boolean>;
  logout: () => void;
  isAuthenticated: boolean;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export const AuthProvider: React.FC<{ children: React.ReactNode }> = ({ children }) => {
  const [user, setUser] = useState<User | null>(null);

  useEffect(() => {
    const initAuth = async () => {
      const token = localStorage.getItem('access_token');
      if (token) {
        try {
          const userData = await api.getMe();
          setUser(userData.data);
        } catch (error) {
          console.error('Failed to fetch user data', error);
          localStorage.removeItem('access_token');
          localStorage.removeItem('refresh_token');
        }
      }
    };
    initAuth();
  }, []);

  const login = async (email: string, password: string): Promise<boolean> => {
    try {
      const response = await api.login(email, password);
      localStorage.setItem('access_token', response.data.accessToken);
      localStorage.setItem('refresh_token', response.data.refreshToken);
      const userData = await api.getMe();
      setUser(userData.data);
      return true;
    } catch (error) {
      console.error('Login failed', error);
      return false;
    }
  };

  const logout = () => {
    setUser(null);
    localStorage.removeItem('access_token');
    localStorage.removeItem('refresh_token');
  };

  return (
    <AuthContext.Provider value={{ user, login, logout, isAuthenticated: !!user }}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => {
  const context = useContext(AuthContext);
  if (context === undefined) {
    throw new Error('useAuth must be used within an AuthProvider');
  }
  return context;
};
