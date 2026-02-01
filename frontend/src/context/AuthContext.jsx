import React, {
  createContext,
  useContext,
  useState,
  useMemo,
  useEffect,
} from "react";
import { LOCL_STRG_KEY } from "../config/constants";

const AuthContext = createContext(null);

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(null);
  const [isInitializing, setIsInitializing] = useState(true);

  useEffect(() => {
    const initializeAuth = () => {
      try {
        const storedUser = localStorage.getItem(LOCL_STRG_KEY.USER);
        const storedToken = localStorage.getItem(LOCL_STRG_KEY.TOKEN);

        // Only set the user if both user data and token exist
        if (storedUser && storedToken) {
          setUser(JSON.parse(storedUser));
        }
      } catch (error) {
        console.error("Failed to parse stored user:", error);
        localStorage.clear(); // Clear corrupt data
      } finally {
        setIsInitializing(false);
      }
    };

    initializeAuth();
  }, []);

  const saveLoginDetails = (userData, token) => {
    localStorage.setItem(LOCL_STRG_KEY.USER, JSON.stringify(userData));
    localStorage.setItem(LOCL_STRG_KEY.TOKEN, token);
    setUser(userData);
  };

  const logout = () => {
    localStorage.removeItem(LOCL_STRG_KEY.USER);
    localStorage.removeItem(LOCL_STRG_KEY.TOKEN);
    setUser(null);
  };

  const value = useMemo(
    () => ({
      user,
      isInitializing,
      saveLoginDetails,
      logout,
      isAuthenticated: !!user,
    }),
    [user, isInitializing]
  );

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
};

export const useAuth = () => {
  const context = useContext(AuthContext);
  if (!context) throw new Error("useAuth must be used within AuthProvider");
  return context;
};