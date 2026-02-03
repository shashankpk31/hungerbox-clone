import AppRoutes from "./routes/AppRoutes";
import { AuthProvider } from "./context/AuthContext";
import { Toaster } from "react-hot-toast";
import ReloadPrompt from "./components/ui/ReloadPrompt";

function App() {
  return (
    <AuthProvider>
      <AppRoutes />
      <Toaster
        position="top-right"
        toastOptions={{
          duration: 4000,
          style: {
            background: '#333',
            color: '#fff',
            borderRadius: '1rem',
            padding: '16px',
          },
          success: {
            iconTheme: {
              primary: '#ff5200', // HungerBox Orange
              secondary: '#fff',
            },
          },
        }}
      />
      <ReloadPrompt />
    </AuthProvider>
  );
}

export default App;