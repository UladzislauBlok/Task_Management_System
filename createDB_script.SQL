/*psql -U postgres*/

CREATE DATABASE task_system_db;

CREATE USER task_system_db_admin WITH PASSWORD 'pass';

ALTER DATABASE task_system_db OWNER TO task_system_db_admin;

/*psql -U task_system_db_admin -d pass*/

CREATE TABLE projects
(
    id          SERIAL PRIMARY KEY,
    name        VARCHAR(64) UNIQUE NOT NULL,
    description TEXT               NOT NULL,
    start_date  DATE               NOT NULL
);

CREATE TABLE users
(
    id            SERIAL PRIMARY KEY,
    first_name    VARCHAR(124)        NOT NULL,
    last_name     VARCHAR(124)        NOT NULL,
    password      VARCHAR(32)         NOT NULL,
    email         VARCHAR(124) UNIQUE NOT NULL,
    role          VARCHAR(32)         NOT NULL,
    image         VARCHAR(124)        NOT NULL,
    project_id_fk INTEGER REFERENCES projects (id)
);

CREATE TABLE tasks
(
    id            SERIAL PRIMARY KEY,
    name          VARCHAR(64)                      NOT NULL,
    description   TEXT                             NOT NULL,
    status        VARCHAR(32)                      NOT NULL,
    project_id_fk INTEGER REFERENCES projects (id) NOT NULL
);

CREATE TABLE task_events
(
    id              SERIAL PRIMARY KEY,
    task_id_fk      INTEGER REFERENCES tasks (id) NOT NULL,
    description     TEXT                          NOT NULL,
    event_user_name VARCHAR(255)                  NOT NULL
);
