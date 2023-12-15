/*psql -U postgres*/

CREATE DATABASE task_system_db;

CREATE USER task_system_db_admin WITH PASSWORD 'pass';

ALTER DATABASE task_system_db OWNER TO task_system_db_admin;

/*psql -U task_system_db_admin -d pass*/

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

