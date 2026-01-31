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
    const storedUser = localStorage.getItem(LOCL_STRG_KEY.USER); // Use constant here
    if (storedUser) {
      setUser(JSON.parse(storedUser));
    }
    setIsInitializing(false);
  }, []);

  const saveLoginDetails = (userData, token) => {
    localStorage.setItem(LOCL_STRG_KEY.USER, JSON.stringify(userData));
    localStorage.setItem(LOCL_STRG_KEY.TOKEN, token);
    setUser(userData);
  };

  const logout = () => {
    localStorage.clear();
    setUser(null);
  };

  // useMemo prevents components from re-rendering unless 'user' or 'isInitializing' actually changes
  const value = useMemo(
    () => ({
      user,
      isInitializing,
      saveLoginDetails,
      logout,
      isAuthenticated: !!user,
    }),
    [user, isInitializing],
  );

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
};

export const useAuth = () => {
  const context = useContext(AuthContext);
  if (!context) throw new Error("useAuth must be used within AuthProvider");
  return context;
};
