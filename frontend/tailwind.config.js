export default {
  content: ["./index.html", "./src/**/*.{js,ts,jsx,tsx}"],
  theme: {
    extend: {
      colors: {
        brand: {
          primary: "#ff5200",    // Hungerbox Orange
          secondary: "#ff7a33",
          accent: "#fff5f0",
        },
        status: {
          success: "#22c55e",
          error: "#ef4444",
          pending: "#f59e0b",
        },
        surface: {
          card: "#ffffff",
          bg: "#f8fafc",         // Soft gray-blue background
          border: "#e2e8f0",     // Consistent border color
        }
      },
      borderRadius: {
        'hb': '2.5rem',          // The HungerBox signature rounded corners
      },
      fontFamily: {
        sans: ['Inter', 'system-ui', 'sans-serif'], // Professional corporate look
      }
    },
  },
  plugins: [],
}