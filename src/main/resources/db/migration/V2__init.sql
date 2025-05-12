CREATE TABLE IF NOT EXISTS categories
(
    id    BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS  products
(
    id          BIGSERIAL PRIMARY KEY,
    title       VARCHAR(255) NOT NULL,
    description TEXT,
    price       FLOAT NOT NULL,
    category_id BIGINT REFERENCES categories (id)
);

INSERT INTO categories (title)
VALUES ('Электроника'),
       ('Бытовая техника');

INSERT INTO products(title, price, category_id)
VALUES ('Ноутбук Lenovo', 44990, 1),
       ('Телефон iPhone', 66490, 1),
       ('Стиральная машинка LG', 32290, 2),
       ('Телевизор Samsung', 32290, 1);
