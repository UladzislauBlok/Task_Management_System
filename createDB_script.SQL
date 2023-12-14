CREATE DATABASE task_system_DB;

CREATE USER task_system_DB_admin WITH PASSWORD 'pass';

GRANT ALL PRIVILEGES ON DATABASE task_system_DB TO task_system_DB_admin;

CREATE TABLE users
(
    id         SERIAL PRIMARY KEY,
    first_name VARCHAR(124)        NOT NULL,
    last_name  VARCHAR(124)        NOT NULL,
    password   VARCHAR(32)         NOT NULL,
    email      VARCHAR(124) UNIQUE NOT NULL,
    role       VARCHAR(32)         NOT NULL,
    image      VARCHAR(124)        NOT NULL
);

