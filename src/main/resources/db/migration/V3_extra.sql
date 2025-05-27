CREATE TABLE IF NOT EXISTS categories
(
    id          BIGSERIAL PRIMARY KEY,
    title       VARCHAR(255) NOT NULL UNIQUE,
    description TEXT,
    image_url   VARCHAR(255),
    parent_id   BIGINT REFERENCES categories (id)
);

CREATE TABLE IF NOT EXISTS attributes
(
    id    BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS attribute_values
(
    id           BIGSERIAL PRIMARY KEY,
    title        VARCHAR(255) NOT NULL,
    attribute_id BIGINT REFERENCES attributes (id)
);

CREATE TABLE IF NOT EXISTS products
(
    id          BIGSERIAL PRIMARY KEY,
    title       VARCHAR(255) NOT NULL,
    description TEXT,
    image_url   VARCHAR(255),
    price       FLOAT        NOT NULL,
    category_id BIGINT REFERENCES categories (id)
);

CREATE TABLE IF NOT EXISTS product_attributes
(
    product_id         BIGINT REFERENCES products (id),
    attribute_value_id BIGINT REFERENCES attribute_values (id),
    CONSTRAINT product_attributes_unique UNIQUE (product_id, attribute_value_id)
);

CREATE TABLE IF NOT EXISTS orders
(
    id              BIGSERIAL PRIMARY KEY,
    customer_id     BIGINT REFERENCES users (id),
    price           FLOAT        NOT NULL,
    order_status    SMALLINT,
    shipping_method SMALLINT,
    address         TEXT,
    contact_email   VARCHAR(255) NOT NULL,
    creation_time   TIMESTAMP DEFAULT NOW(),
    deliver_time    TIMESTAMP,
    details         TEXT
);

CREATE TABLE IF NOT EXISTS order_items
(
    id         BIGSERIAL PRIMARY KEY,
    order_id  BIGINT REFERENCES orders (id),
    product_id BIGINT REFERENCES products (id),
    price      FLOAT NOT NULL,
    quantity   INTEGER
);

CREATE TABLE IF NOT EXISTS customer_addresses
(
    id          BIGSERIAL PRIMARY KEY,
    address     TEXT NOT NULL,
    customer_id BIGINT REFERENCES users (id)
);

INSERT INTO categories (title)
VALUES ('Электроника'),
       ('Бытовая техника');

INSERT INTO products(title, price, category_id)
VALUES ('Ноутбук Lenovo', 44990, 1),
       ('Телефон iPhone', 66490, 1),
       ('Стиральная машинка LG', 32290, 2),
       ('Телевизор Samsung', 32290, 1);
