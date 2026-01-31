#!/bin/bash
set -e

# Function to check if a database exists
db_exists() {
  psql -U "$POSTGRES_USER" -d postgres -tAc "SELECT 1 FROM pg_database WHERE datname='$1'" | grep -q 1
}

if [ -n "$POSTGRES_MULTIPLE_DATABASES" ]; then
    for db in $(echo $POSTGRES_MULTIPLE_DATABASES | tr ',' ' '); do
        
        # 1. Create the database if it doesn't exist
        if db_exists "$db"; then
            echo "Database '$db' already exists, skipping creation."
        else
            echo "Creating database: $db"
            psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "postgres" <<-EOSQL
                CREATE DATABASE $db;
EOSQL
        fi

        # 2. Apply Audit Log (ONLY for HungerBox services, SKIP for SonarQube)
        if [ "$db" != "sonarqube" ]; then
            echo "Applying Audit Log schema to: $db"
            psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$db" <<-EOSQL
                CREATE TABLE IF NOT EXISTS audit_log (
                    id BIGSERIAL PRIMARY KEY,
                    entity_name VARCHAR(100),
                    entity_id VARCHAR(100),
                    action VARCHAR(50),
                    changed_by VARCHAR(255),
                    timestamp TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                    details TEXT
                );
EOSQL
        fi

        # --- IDENTITY SERVICE ---
        if [ "$db" == "hungerbox_identity" ]; then
            echo "Applying specific tables for Identity Service..."
            psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$db" <<-EOSQL
                CREATE TABLE IF NOT EXISTS users (
                    id BIGSERIAL PRIMARY KEY,
                    username VARCHAR(255) NOT NULL UNIQUE,
                    email VARCHAR(255) NOT NULL UNIQUE,
                    password VARCHAR(255) NOT NULL,
                    role VARCHAR(50), 
                    employee_id VARCHAR(255),
                    organization_id BIGINT,
                    office_id BIGINT,
                    shop_name VARCHAR(255),
                    gst_number VARCHAR(255),
                    phone_number VARCHAR(20) UNIQUE,
                    is_email_verified BOOLEAN DEFAULT FALSE,
                    is_phone_verified BOOLEAN DEFAULT FALSE,
                    is_profile_complete BOOLEAN DEFAULT FALSE,
                    status VARCHAR(255) DEFAULT 'ACTIVE',
                    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                    updated_at TIMESTAMP,
                    created_by VARCHAR(255) NOT NULL,
                    updated_by VARCHAR(255),
                    deleted BOOLEAN DEFAULT FALSE
                );
                CREATE INDEX IF NOT EXISTS idx_users_username ON users(username);
                CREATE INDEX IF NOT EXISTS idx_users_email ON users(email);
EOSQL
        fi

        # --- ORDERS SERVICE ---
        if [ "$db" == "hungerbox_orders" ]; then
            echo "Applying specific tables for Order Service..."
            psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$db" <<-EOSQL
                CREATE TABLE IF NOT EXISTS orders (
                    id BIGSERIAL PRIMARY KEY,
                    user_id BIGINT NOT NULL,
                    vendor_id BIGINT NOT NULL,
                    cafeteria_id BIGINT,
                    office_id BIGINT,
                    total_amount DECIMAL(10,2) NOT NULL,
                    status VARCHAR(50) DEFAULT 'PENDING',
                    pickup_otp VARCHAR(6),
                    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                    updated_at TIMESTAMP,
                    created_by VARCHAR(255) NOT NULL,
                    updated_by VARCHAR(255),
                    deleted BOOLEAN NOT NULL DEFAULT FALSE
                );
                CREATE TABLE IF NOT EXISTS order_items (
                    id BIGSERIAL PRIMARY KEY,
                    order_id BIGINT NOT NULL REFERENCES orders(id) ON DELETE CASCADE,
                    menu_item_id BIGINT NOT NULL,
                    quantity INTEGER NOT NULL DEFAULT 1,
                    unit_price DECIMAL(10,2) NOT NULL,
                    notes TEXT,
                    addon_ids TEXT,
                    total_price DECIMAL(10,2),
                    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                    updated_at TIMESTAMP,
                    created_by VARCHAR(255) NOT NULL,
                    updated_by VARCHAR(255),
                    deleted BOOLEAN NOT NULL DEFAULT FALSE
                );
                CREATE INDEX IF NOT EXISTS idx_orders_user_id ON orders(user_id);
                CREATE INDEX IF NOT EXISTS idx_orders_vendor_id ON orders(vendor_id);
                CREATE INDEX IF NOT EXISTS idx_order_items_order_id ON order_items(order_id);
EOSQL
        fi

        # --- VENDOR SERVICE ---
        if [ "$db" == "hungerbox_vendor" ]; then
            echo "Applying specific tables for Vendor Service..."
            psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$db" <<-EOSQL
                CREATE TABLE IF NOT EXISTS organizations (
                    id BIGSERIAL PRIMARY KEY,
                    name VARCHAR(255) NOT NULL,
                    domain VARCHAR(100),
                    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                    updated_at TIMESTAMP,
                    created_by VARCHAR(255) NOT NULL,
                    updated_by VARCHAR(255),
                    deleted BOOLEAN DEFAULT FALSE
                );
                CREATE TABLE IF NOT EXISTS locations (
                    id BIGSERIAL PRIMARY KEY,
                    org_id BIGINT REFERENCES organizations(id),
                    city_name VARCHAR(100) NOT NULL,
                    state VARCHAR(100),
                    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
                    updated_at TIMESTAMP WITHOUT TIME ZONE,
                    created_by VARCHAR(255) NOT NULL,
                    updated_by VARCHAR(255),
                    deleted BOOLEAN NOT NULL DEFAULT FALSE
                );
                CREATE TABLE IF NOT EXISTS offices (
                    id BIGSERIAL PRIMARY KEY,
                    location_id BIGINT REFERENCES locations(id),
                    office_name VARCHAR(255) NOT NULL,
                    address TEXT,
                    city VARCHAR(100),
                    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                    updated_at TIMESTAMP,
                    created_by VARCHAR(255) NOT NULL,
                    updated_by VARCHAR(255),
                    deleted BOOLEAN DEFAULT FALSE
                );
                CREATE TABLE IF NOT EXISTS cafeterias (
                    id BIGSERIAL PRIMARY KEY,
                    office_id BIGINT REFERENCES offices(id),
                    name VARCHAR(255) NOT NULL,
                    floor_number INT,
                    is_active BOOLEAN DEFAULT TRUE,
                    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                    updated_at TIMESTAMP,
                    created_by VARCHAR(255) NOT NULL,
                    updated_by VARCHAR(255),
                    deleted BOOLEAN DEFAULT FALSE
                );
                CREATE TABLE IF NOT EXISTS vendors (
                    id BIGSERIAL PRIMARY KEY,
                    cafeteria_id BIGINT REFERENCES cafeterias(id),
                    name VARCHAR(255) NOT NULL,
                    stall_number VARCHAR(50),
                    contact_person VARCHAR(255),
                    contact_number VARCHAR(20),
                    owner_user_id BIGINT UNIQUE,
                    is_active BOOLEAN DEFAULT TRUE,
                    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                    updated_at TIMESTAMP,
                    created_by VARCHAR(255) NOT NULL,
                    updated_by VARCHAR(255),
                    deleted BOOLEAN DEFAULT FALSE
                );
                
                CREATE TABLE IF NOT EXISTS vendor_operating_hours (
                    id BIGSERIAL PRIMARY KEY,
                    vendor_id BIGINT REFERENCES vendors(id),
                    day_of_week INT,
                    open_time TIME,
                    close_time TIME,
                    is_closed BOOLEAN DEFAULT FALSE,
                    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                    updated_at TIMESTAMP,
                    created_by VARCHAR(255) NOT NULL,
                    updated_by VARCHAR(255),
                    deleted BOOLEAN DEFAULT FALSE
                );
                CREATE INDEX IF NOT EXISTS idx_vendors_cafeteria ON vendors(cafeteria_id);
                CREATE UNIQUE INDEX IF NOT EXISTS uq_vendors_owner_user ON vendors(owner_user_id);
EOSQL
        fi

        # --- PAYMENT SERVICE ---
        if [ "$db" == "hungerbox_payment" ]; then
            echo "Applying specific tables for Payment Service..."
            psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$db" <<-EOSQL
                CREATE TABLE IF NOT EXISTS transactions (
                    id BIGSERIAL PRIMARY KEY,
                    user_id BIGINT NOT NULL,
                    order_id BIGINT NOT NULL,
                    amount DECIMAL(10,2) NOT NULL,
                    currency VARCHAR(10) DEFAULT 'INR',
                    razorpay_order_id VARCHAR(255) UNIQUE,
                    razorpay_payment_id VARCHAR(255),
                    razorpay_signature TEXT,
                    payment_method VARCHAR(50),
                    status VARCHAR(50) DEFAULT 'PENDING',
                    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                    updated_at TIMESTAMP,
                    created_by VARCHAR(255) NOT NULL,
                    updated_by VARCHAR(255),
                    deleted BOOLEAN NOT NULL DEFAULT FALSE
                );
                CREATE INDEX IF NOT EXISTS idx_txn_order_id ON transactions(order_id);
                CREATE INDEX IF NOT EXISTS idx_txn_user_id ON transactions(user_id);
                CREATE INDEX IF NOT EXISTS idx_txn_razorpay_id ON transactions(razorpay_order_id);
EOSQL
        fi

        # --- WALLET SERVICE ---
        if [ "$db" == "hungerbox_wallet" ]; then
            echo "Applying specific tables for Wallet Service..."
            psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$db" <<-EOSQL
                CREATE TABLE IF NOT EXISTS user_wallets (
                    id BIGSERIAL PRIMARY KEY,
                    user_id BIGINT UNIQUE NOT NULL,
                    balance DECIMAL(15,2) DEFAULT 0.00,
                    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                    updated_at TIMESTAMP,
                    created_by VARCHAR(255) NOT NULL,
                    updated_by VARCHAR(255),
                    deleted BOOLEAN DEFAULT FALSE
                );
                CREATE TABLE IF NOT EXISTS wallet_transactions (
                    id BIGSERIAL PRIMARY KEY,
                    wallet_id BIGINT REFERENCES user_wallets(id),
                    amount DECIMAL(15,2) NOT NULL,
                    txn_type VARCHAR(20) NOT NULL,
                    status VARCHAR(50) DEFAULT 'SUCCESS',
                    description VARCHAR(255),
                    provider_reference_id VARCHAR(255),
                    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                    updated_at TIMESTAMP,
                    created_by VARCHAR(255) NOT NULL,
                    updated_by VARCHAR(255),
                    deleted BOOLEAN NOT NULL DEFAULT FALSE
                );
                CREATE INDEX IF NOT EXISTS idx_wallet_user_id ON user_wallets(user_id);
                CREATE INDEX IF NOT EXISTS idx_wallet_txn_wallet_id ON wallet_transactions(wallet_id);
EOSQL
        fi

        # --- INVENTORY SERVICE ---
        if [ "$db" == "hungerbox_inventory" ]; then
            echo "Applying specific tables for Inventory Service..."
            psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$db" <<-EOSQL
                CREATE TABLE IF NOT EXISTS inventory_items (
                    id BIGSERIAL PRIMARY KEY,
                    menu_item_id BIGINT UNIQUE NOT NULL,
                    vendor_id BIGINT NOT NULL,
                    available_quantity INTEGER DEFAULT 0,
                    reserved_quantity INTEGER DEFAULT 0,
                    threshold_limit INTEGER DEFAULT 10,
                    is_auto_replenish BOOLEAN DEFAULT FALSE,
                    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                    updated_at TIMESTAMP,
                    created_by VARCHAR(255) NOT NULL,
                    updated_by VARCHAR(255),
                    deleted BOOLEAN DEFAULT FALSE
                );
                CREATE TABLE IF NOT EXISTS stock_log (
                    id BIGSERIAL PRIMARY KEY,
                    inventory_item_id BIGINT REFERENCES inventory_items(id),
                    change_amount INTEGER NOT NULL,
                    action_type VARCHAR(50),
                    reason TEXT,
                    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                    updated_at TIMESTAMP,
                    created_by VARCHAR(255) NOT NULL,
                    updated_by VARCHAR(255),
                    deleted BOOLEAN DEFAULT FALSE
                );
                CREATE INDEX IF NOT EXISTS idx_inventory_menu_item ON inventory_items(menu_item_id);
                CREATE INDEX IF NOT EXISTS idx_inventory_vendor ON inventory_items(vendor_id);
EOSQL
        fi

        # --- MENU SERVICE ---
        if [ "$db" == "hungerbox_menu" ]; then
            echo "Applying specific tables for Menu Service..."
            psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$db" <<-EOSQL
                CREATE TABLE IF NOT EXISTS categories (
                    id BIGSERIAL PRIMARY KEY,
                    vendor_id BIGINT NOT NULL,
                    name VARCHAR(100) NOT NULL,
                    description TEXT,
                    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                    updated_at TIMESTAMP,
                    created_by VARCHAR(255) NOT NULL,
                    updated_by VARCHAR(255),
                    deleted BOOLEAN DEFAULT FALSE
                );
                CREATE TABLE IF NOT EXISTS menu_items (
                    id BIGSERIAL PRIMARY KEY,
                    vendor_id BIGINT NOT NULL,
                    category_id BIGINT REFERENCES categories(id),
                    name VARCHAR(255) NOT NULL,
                    description TEXT,
                    price DECIMAL(10,2) NOT NULL,
                    is_available BOOLEAN DEFAULT TRUE,
                    is_veg BOOLEAN DEFAULT TRUE,
                    calories INT,
                    preparation_time_minutes INT,
                    image_url VARCHAR(500),
                    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                    updated_at TIMESTAMP,
                    created_by VARCHAR(255) NOT NULL,
                    updated_by VARCHAR(255),
                    deleted BOOLEAN NOT NULL DEFAULT FALSE
                );
                CREATE TABLE IF NOT EXISTS menu_item_addons (
                    id BIGSERIAL PRIMARY KEY,
                    menu_item_id BIGINT REFERENCES menu_items(id) ON DELETE CASCADE,
                    addon_name VARCHAR(255) NOT NULL,
                    extra_price DECIMAL(10,2) DEFAULT 0.00,
                    is_available BOOLEAN DEFAULT TRUE,
                    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                    updated_at TIMESTAMP,
                    created_by VARCHAR(255) NOT NULL,
                    updated_by VARCHAR(255),
                    deleted BOOLEAN NOT NULL DEFAULT FALSE
                );
                CREATE INDEX IF NOT EXISTS idx_menu_vendor_id ON menu_items(vendor_id);
                CREATE INDEX IF NOT EXISTS idx_menu_category_id ON menu_items(category_id);
                CREATE INDEX IF NOT EXISTS idx_addons_menu_item ON menu_item_addons(menu_item_id);
EOSQL
        fi
        
    done
fi