CREATE TABLE IF NOT EXISTS products (
    sku VARCHAR(255) PRIMARY KEY,
    price NUMERIC,
    description VARCHAR(255),
    category VARCHAR(255),
    created_at TIMESTAMP
    );