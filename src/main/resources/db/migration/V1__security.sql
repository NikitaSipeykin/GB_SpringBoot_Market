CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    enabled BOOLEAN NOT NULL DEFAULT false
);

CREATE TABLE IF NOT EXISTS registration_tokens (
     id BIGSERIAL PRIMARY KEY,
     token VARCHAR(255) NOT NULL,
     expired_at TIMESTAMP NOT NULL,
     user_id BIGINT REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS authorities (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS users_authorities (
    user_id BIGINT REFERENCES users (id),
    authority_id BIGINT REFERENCES authorities (id),
    primary key (user_id, authority_id)
);

INSERT INTO users (password, email, enabled)
VALUES
('$2a$12$fIxG7VKFdJw9HriHgNyuNu.DitJytiDsERb25YAvhUEicllt37m0O', 'admin@admin.ru', true),
('$2a$12$.z4y.gN6zGcUMjU/USKMEedIinnVn.4xGonlD1.M2213psnAWqYW.', 'manager@manager.ru', true),
('$2a$12$.z4y.gN6zGcUMjU/USKMEedIinnVn.4xGonlD1.M2213psnAWqYW.', 'user@user.ru', true);

INSERT INTO authorities (name)
VALUES
('ROLE_ADMIN'),
('ROLE_MANAGER'),
('ROLE_USER');

INSERT INTO users_authorities (user_id, authority_id)
VALUES (1, 1),
       (2, 2),
       (3, 3);
