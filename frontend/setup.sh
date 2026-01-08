# # 1. AUTHENTICATION FEATURE
# touch src/features/auth/services/authService.js
# touch src/features/auth/hooks/useAuthStatus.js
# touch src/features/auth/pages/Login/index.jsx
# touch src/features/auth/components/LoginForm.jsx

# # 2. EMPLOYEE PORTAL (Orders & Browsing)
# touch src/features/employee/services/catalogService.js
# touch src/features/employee/services/orderService.js
# touch src/features/employee/hooks/useCart.js
# touch src/features/employee/pages/VendorDetail/index.jsx
# touch src/features/employee/pages/Cart/index.jsx
# touch src/features/employee/pages/Tracking/index.jsx
# touch src/features/employee/components/MenuCard.jsx
# touch src/features/employee/components/CartOverlay.jsx
# touch src/features/employee/components/OrderStepper.jsx

# # 3. VENDOR PORTAL (KDS & Inventory)
# touch src/features/vendor/services/vendorService.js
# touch src/features/vendor/hooks/useLiveOrders.js
# touch src/features/vendor/pages/Dashboard/index.jsx
# touch src/features/vendor/pages/MenuManagement/index.jsx
# touch src/features/vendor/components/OrderCard.jsx
# touch src/features/vendor/components/ToggleAvailability.jsx

# # 4. ADMIN PORTAL (Governance)
# touch src/features/admin/services/adminService.js
# touch src/features/admin/pages/UserManagement/index.jsx
# touch src/features/admin/pages/VendorOnboarding/index.jsx
# touch src/features/admin/components/StatsCard.jsx

# # 5. SHARED UI & LAYOUTS
# touch src/components/ui/Button.jsx
# touch src/components/ui/Input.jsx
# touch src/components/ui/Modal.jsx
# touch src/layouts/MainLayout/index.jsx
# touch src/layouts/VendorLayout/index.jsx
# touch src/routes/ProtectedRoute.jsx

# # 6. UTILS & CONSTANTS
# touch src/utils/constants.js
# touch src/utils/formatCurrency.js

# echo "Successfully created all deeply nested JSX and JS files."



#!/bin/bash

# Navigate to the frontend src directory
cd src

echo "🚀 Starting Frontend Restructuring..."

# 1. Create new UI components for the landing page
mkdir -p components/ui/Toggle
touch components/ui/Toggle/AuthToggle.jsx
touch components/ui/PhoneMockup.jsx

# 2. Create the Home feature (This will be your Landing + Auth page)
mkdir -p features/home/pages
mkdir -p features/home/components
touch features/home/pages/LandingPage.jsx
touch features/home/components/HeroContent.jsx

# 3. Create the Admin Registration Page
mkdir -p features/auth/pages/AdminRegister
touch features/auth/pages/AdminRegister/index.jsx

# 4. Refactor Register Components
# We will create specialized fields for different roles
mkdir -p features/auth/components/forms
touch features/auth/components/forms/EmployeeFields.jsx
touch features/auth/components/forms/VendorFields.jsx

# 5. Add Framer Motion (Optional but recommended for the animations you described)
echo "📦 Adding animation requirements to notes..."
# Note: You will still need to run 'npm install framer-motion' manually

echo "✅ Structure Updated Successfully!"
echo "New paths created:"
echo "- features/home/pages/LandingPage.jsx (The new Master Screen)"
echo "- features/auth/pages/AdminRegister/index.jsx (Hidden Admin Route)"
echo "- components/ui/Toggle/AuthToggle.jsx (The Role Switcher)"