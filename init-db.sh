#!/bin/bash
set -e

# Create multiple databases separated by commas
if [ -n "$POSTGRES_MULTIPLE_DATABASES" ]; then
    for db in $(echo $POSTGRES_MULTIPLE_DATABASES | tr ',' ' '); do
        echo "Creating database: $db"
        psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "postgres" <<-EOSQL
            CREATE DATABASE $db;
EOSQL

        # Only create the Identity tables if the database name is 'hungerbox_identity'
        if [ "$db" == "hungerbox_identity" ]; then
            echo "Applying Identity schema to database: $db"
            psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$db" <<-EOSQL
                -- Audit Log Table
                CREATE TABLE IF NOT EXISTS identity_audit_log (
                    id BIGSERIAL PRIMARY KEY,
                    entity_name VARCHAR(255),
                    entity_id VARCHAR(255),
                    action VARCHAR(50),
                    changed_by VARCHAR(255),
                    timestamp TIMESTAMP WITHOUT TIME ZONE,
                    details TEXT
                );

                -- Users Table (including BaseEntity fields)
                CREATE TABLE IF NOT EXISTS users (
                    id BIGSERIAL PRIMARY KEY,
                    username VARCHAR(255) NOT NULL UNIQUE,
                    email VARCHAR(255) NOT NULL UNIQUE,
                    password VARCHAR(255) NOT NULL,
                    role VARCHAR(50),
                    employee_id VARCHAR(255),
                    company_name VARCHAR(255),
                    shop_name VARCHAR(255),
                    gst_number VARCHAR(255),
                    contact_number VARCHAR(255),
                    
                    -- BaseEntity fields
                    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
                    updated_at TIMESTAMP WITHOUT TIME ZONE,
                    created_by VARCHAR(255) NOT NULL,
                    updated_by VARCHAR(255),
                    deleted BOOLEAN NOT NULL DEFAULT FALSE
                );
EOSQL
        fi
    done
fi