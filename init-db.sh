#!/bin/bash
set -e

if [ -n "$POSTGRES_MULTIPLE_DATABASES" ]; then
    for db in $(echo $POSTGRES_MULTIPLE_DATABASES | tr ',' ' '); do
        echo "Creating database: $db"
        psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "postgres" <<-EOSQL
            CREATE DATABASE $db;
EOSQL

        echo "Applying full schema to: $db"
        psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$db" <<-EOSQL
            
            -- Central Audit Log (Populated via JPA)
            CREATE TABLE IF NOT EXISTS audit_log (
                id BIGSERIAL PRIMARY KEY,
                entity_name VARCHAR(100),
                entity_id VARCHAR(100),
                action VARCHAR(50),
                changed_by VARCHAR(255),
                timestamp TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                details TEXT
            );

            -- IDENTITY SERVICE
            if [ "$db" == "hungerbox_identity" ]; then
                CREATE TABLE IF NOT EXISTS users (
                    id BIGSERIAL PRIMARY KEY,
                    username VARCHAR(255) NOT NULL UNIQUE,
                    email VARCHAR(255) NOT NULL UNIQUE,
                    password VARCHAR(255) NOT NULL,
                    role VARCHAR(50),
                    employee_id VARCHAR(255),
                    company_name VARCHAR(255),
                    created_at TIMESTAMP NOT NULL,
                    updated_at TIMESTAMP,
                    created_by VARCHAR(255) NOT NULL,
                    updated_by VARCHAR(255),
                    deleted BOOLEAN DEFAULT FALSE
                );
            fi

            -- VENDOR SERVICE
            if [ "$db" == "hungerbox_vendor" ]; then
                CREATE TABLE IF NOT EXISTS cafeterias (
                    id BIGSERIAL PRIMARY KEY,
                    name VARCHAR(255) NOT NULL,
                    location_code VARCHAR(100),
                    created_at TIMESTAMP NOT NULL, created_by VARCHAR(255) NOT NULL,
                    updated_at TIMESTAMP, updated_by VARCHAR(255), deleted BOOLEAN DEFAULT FALSE
                );
                CREATE TABLE IF NOT EXISTS vendors (
                    id BIGSERIAL PRIMARY KEY,
                    cafeteria_id BIGINT REFERENCES cafeterias(id),
                    name VARCHAR(255) NOT NULL,
                    is_active BOOLEAN DEFAULT TRUE,
                    created_at TIMESTAMP NOT NULL, created_by VARCHAR(255) NOT NULL,
                    updated_at TIMESTAMP, updated_by VARCHAR(255), deleted BOOLEAN DEFAULT FALSE
                );
                CREATE TABLE IF NOT EXISTS vendor_operating_hours (
                    id BIGSERIAL PRIMARY KEY,
                    vendor_id BIGINT REFERENCES vendors(id),
                    day_of_week INT, -- 1 (Mon) to 7 (Sun)
                    open_time TIME,
                    close_time TIME,
                    is_closed BOOLEAN DEFAULT FALSE
                );
            fi

            -- MENU SERVICE
            if [ "$db" == "hungerbox_menu" ]; then
                CREATE TABLE IF NOT EXISTS menu_items (
                    id BIGSERIAL PRIMARY KEY,
                    vendor_id BIGINT NOT NULL,
                    name VARCHAR(255) NOT NULL,
                    price DECIMAL(10,2) NOT NULL,
                    is_available BOOLEAN DEFAULT TRUE,
                    -- BaseEntity fields
                    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
                    updated_at TIMESTAMP WITHOUT TIME ZONE,
                    created_by VARCHAR(255) NOT NULL,
                    updated_by VARCHAR(255),
                    deleted BOOLEAN NOT NULL DEFAULT FALSE
                
                );
                CREATE TABLE IF NOT EXISTS menu_item_addons (
                    id BIGSERIAL PRIMARY KEY,
                    menu_item_id BIGINT REFERENCES menu_items(id),
                    addon_name VARCHAR(255),
                    extra_price DECIMAL(10,2)
                    -- BaseEntity fields
                    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
                    updated_at TIMESTAMP WITHOUT TIME ZONE,
                    created_by VARCHAR(255) NOT NULL,
                    updated_by VARCHAR(255),
                    deleted BOOLEAN NOT NULL DEFAULT FALSE
                );
            fi

            -- ORDER SERVICE
            if [ "$db" == "hungerbox_orders" ]; then
                CREATE TABLE IF NOT EXISTS orders (
                    id BIGSERIAL PRIMARY KEY,
                    user_id BIGINT NOT NULL,
                    vendor_id BIGINT NOT NULL,
                    total_amount DECIMAL(10,2) NOT NULL,
                    status VARCHAR(50), -- PLACED, PREPARING, READY, PICKED_UP
                    pickup_otp VARCHAR(6),
                    -- BaseEntity fields
                    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
                    updated_at TIMESTAMP WITHOUT TIME ZONE,
                    created_by VARCHAR(255) NOT NULL,
                    updated_by VARCHAR(255),
                    deleted BOOLEAN NOT NULL DEFAULT FALSE
                );
            fi

            -- WALLET SERVICE
            if [ "$db" == "hungerbox_wallet" ]; then
                CREATE TABLE IF NOT EXISTS user_wallets (
                    id BIGSERIAL PRIMARY KEY,
                    user_id BIGINT UNIQUE NOT NULL,
                    balance DECIMAL(10,2) DEFAULT 0.0,
                    updated_at TIMESTAMP, updated_by VARCHAR(255)
                );
                CREATE TABLE IF NOT EXISTS wallet_transactions (
                    id BIGSERIAL PRIMARY KEY,
                    wallet_id BIGINT REFERENCES user_wallets(id),
                    amount DECIMAL(10,2),
                    txn_type VARCHAR(20), -- CREDIT, DEBIT
                    provider_reference VARCHAR(255), -- Razorpay Order ID
                    status VARCHAR(50), -- SUCCESS, PENDING, FAILED
                    -- BaseEntity fields
                    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
                    updated_at TIMESTAMP WITHOUT TIME ZONE,
                    created_by VARCHAR(255) NOT NULL,
                    updated_by VARCHAR(255),
                    deleted BOOLEAN NOT NULL DEFAULT FALSE
                );
            fi

            -- 7. INVENTORY SERVICE TABLES
            if [ "$db" == "hungerbox_inventory" ]; then
                echo "Applying Inventory schema to: $db"
                CREATE TABLE IF NOT EXISTS inventory_items (
                    id BIGSERIAL PRIMARY KEY,
                    menu_item_id BIGINT UNIQUE NOT NULL, -- Links to Menu Service ID
                    vendor_id BIGINT NOT NULL,
                    available_quantity INTEGER DEFAULT 0,
                    reserved_quantity INTEGER DEFAULT 0, -- Items in 'Pending' orders
                    threshold_limit INTEGER DEFAULT 10,  -- For low stock alerts
                    
                    -- BaseEntity fields
                    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
                    updated_at TIMESTAMP WITHOUT TIME ZONE,
                    created_by VARCHAR(255) NOT NULL,
                    updated_by VARCHAR(255),
                    deleted BOOLEAN NOT NULL DEFAULT FALSE
                );

                CREATE TABLE IF NOT EXISTS stock_log (
                    id BIGSERIAL PRIMARY KEY,
                    menu_item_id BIGINT NOT NULL,
                    change_amount INTEGER NOT NULL, -- Positive for restock, negative for sales
                    reason VARCHAR(255),            -- 'RESTOCK', 'ORDER_PLACED', 'WASTAGE'
                    -- BaseEntity fields
                    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
                    updated_at TIMESTAMP WITHOUT TIME ZONE,
                    created_by VARCHAR(255) NOT NULL,
                    updated_by VARCHAR(255),
                    deleted BOOLEAN NOT NULL DEFAULT FALSE
                );
            fi

            -- 8. PAYMENT SERVICE TABLES (Razorpay focused)
            if [ "$db" == "hungerbox_payment" ]; then
                echo "Applying Payment schema to: $db"
                CREATE TABLE IF NOT EXISTS transactions (
                    id BIGSERIAL PRIMARY KEY,
                    user_id BIGINT NOT NULL,
                    amount DECIMAL(10,2) NOT NULL,
                    currency VARCHAR(10) DEFAULT 'INR',
                    
                    -- Razorpay Specifics
                    razorpay_order_id VARCHAR(255) UNIQUE,
                    razorpay_payment_id VARCHAR(255),
                    razorpay_signature TEXT,
                    
                    status VARCHAR(50), -- CREATED, CAPTURED, FAILED
                    
                    -- BaseEntity fields
                    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
                    updated_at TIMESTAMP WITHOUT TIME ZONE,
                    created_by VARCHAR(255) NOT NULL,
                    updated_by VARCHAR(255),
                    deleted BOOLEAN NOT NULL DEFAULT FALSE
                );
            fi
EOSQL
    done
fi