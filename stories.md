Epic 1: Identity & Access Management
Goal: Secure the platform and differentiate between Employees, Vendors, and Super Admins.

US 1.1: User Registration (Multi-Role)
As a new user, I want to register as either an Employee (with Company ID) or a Vendor (with Shop Details), So that I can access the platform according to my role.

Acceptance Criteria:

Validation for email and unique username.

Conditional fields: Employee needs Company Name, Vendor needs GST Number.

Password must be BCrypt encoded.

US 1.2: Secure Authentication
As a registered user, I want to login with my credentials and receive a JWT token, So that I can access protected cafeteria features.

Acceptance Criteria:

Token contains role and userId.

API Gateway filters unauthorized requests (except /auth/**).

Epic 2: Vendor & Menu Discovery
Goal: Allow employees to see what food is available.

US 2.1: Cafeteria/Vendor Listing
As an employee, I want to see a list of vendors available in my specific cafeteria, So that I can choose where to order from.

Acceptance Criteria:

Fetch vendors filtered by cafeteria_id.

Display vendor status (Open/Closed).

US 2.2: Menu Browsing
As an employee, I want to view a vendor's menu categorized by meal type (Breakfast, Lunch, etc.), So that I can see item prices and descriptions.

Acceptance Criteria:

Items marked as is_available = false should be greyed out or hidden.

Display "Veg/Non-Veg" indicators.

Epic 3: Ordering Workflow
Goal: The core "HungerBox" experience—ordering without cash.

US 3.1: Cart Management
As an employee, I want to add multiple items from a single vendor to a cart, So that I can review my total before paying.

Acceptance Criteria:

Prevent adding items from two different vendors in the same order (standard food court logic).

Calculate subtotal and taxes dynamically.

US 3.2: Wallet Payment & Order Placement
As an employee, I want to pay for my order using my internal wallet balance, So that I don't have to use cash or external cards.

Acceptance Criteria:

Check if wallet_balance >= order_total.

Deduct balance and create Order with status PLACED.

Generate a unique 4-digit Pickup OTP.

Epic 4: Vendor Dashboard
Goal: Tools for the kitchen staff to manage orders.

US 4.1: Live Order Management
As a vendor, I want to see incoming orders in real-time, So that I can start preparing the food.

Acceptance Criteria:

Dashboard showing Order ID, Items, and Timer.

Button to mark order as READY.

US 4.2: Order Completion (OTP Verification)
As a vendor, I want to verify the customer's OTP before handing over the food, So that the order is marked as DELIVERED securely.

Acceptance Criteria:

Input field for OTP.

If OTP matches, status updates to DELIVERED.

Epic 5: Wallet & Credits
Goal: Managing the digital currency.

US 5.1: Wallet Top-up
As an employee, I want to add money to my wallet, So that I have sufficient funds for future meals.

Acceptance Criteria:

Transaction record created in wallet_transactions.

Balance updated in user_wallets.

Technical Tasks (Developer Focus)
Task 1: Implement BaseEntity and AuditListener to ensure every action in the stories above is logged in the audit_log table.

Task 2: Setup Feign Clients for inter-service communication (e.g., Order Service calling Wallet Service to check balance).

US 5.2: Wallet Top-up via Razorpay As an Employee,

I want to add funds to my wallet using UPI/Cards,

So that I can use my wallet for quick food checkout.

Step 1 (Backend): Create a Razorpay Order via payment-service and return rzp_order_id to Frontend.

Step 2 (Frontend): Open Razorpay Checkout Modal.

Step 3 (Backend): Verify rzp_signature upon successful payment callback.

Step 4 (Inter-service): payment-service sends a message (RabbitMQ) or Feign Call to wallet-service to increment balance.


US 6.1: Real-time Inventory Sync As a Vendor, I want the system to automatically decrease the item count when an order is placed, So that I don't receive orders for items that are out of stock.

Acceptance Criteria:

On ORDER_PLACED event, available_quantity decreases.

If available_quantity reaches 0, the item status in Menu Service is updated to Unavailable.