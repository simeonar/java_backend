-- Schema and demo data for sage_erp

CREATE TABLE IF NOT EXISTS category (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT
);

CREATE TABLE IF NOT EXISTS warehouse (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    location VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS product (
    id SERIAL PRIMARY KEY,
    sku VARCHAR(100) NOT NULL,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    price NUMERIC(15,2),
    unit VARCHAR(50),
    category_id INTEGER REFERENCES category(id)
);

CREATE TABLE IF NOT EXISTS inventory (
    id SERIAL PRIMARY KEY,
    product_id INTEGER REFERENCES product(id),
    warehouse_id INTEGER REFERENCES warehouse(id),
    quantity INTEGER,
    reserved_quantity INTEGER,
    last_updated TIMESTAMP
);

-- Demo data
INSERT INTO category (name, description) VALUES
  ('Electronics', 'Electronic devices'),
  ('Office', 'Office supplies')
ON CONFLICT DO NOTHING;

INSERT INTO warehouse (name, location) VALUES
  ('Main Warehouse', 'Berlin'),
  ('Backup Warehouse', 'Munich')
ON CONFLICT DO NOTHING;

INSERT INTO product (sku, name, description, price, unit, category_id) VALUES
  ('SKU-001', 'Laptop', 'Demo laptop', 1200.00, 'pcs', 1),
  ('SKU-002', 'Printer', 'Demo printer', 300.00, 'pcs', 2)
ON CONFLICT DO NOTHING;

INSERT INTO inventory (product_id, warehouse_id, quantity, reserved_quantity, last_updated) VALUES
  (1, 1, 10, 2, NOW()),
  (2, 2, 5, 0, NOW())
ON CONFLICT DO NOTHING;
