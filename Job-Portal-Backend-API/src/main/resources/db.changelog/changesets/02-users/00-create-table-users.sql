-- liquibase formatted sql
-- changeset umeshghimire:02
-- preconditions onFail:CONTINUE onError:HALT
CREATE TABLE IF NOT EXISTS users
(
    id          BIGINT      AUTO_INCREMENT NOT NULL,
    version     BIGINT                     NOT NULL DEFAULT 0,

    full_name   VARCHAR(100)               NOT NULL,
    email       VARCHAR(100)               NOT NULL,
    phone_number VARCHAR(20)               NULL,
    password    VARCHAR(255)               NULL,
    unique_id   VARCHAR(50)                NOT NULL,

    status_id   TINYINT                    NOT NULL,
    role_id     BIGINT                     NOT NULL,


    registered_date      DATETIME          NOT NULL,
    password_changed_date DATETIME         NULL,
    last_logged_in_time  DATETIME          NULL,

    wrong_password_attempt_count INT DEFAULT 0,
    profile_picture_link VARCHAR(500)      NULL,

    created_at  DATETIME                   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  DATETIME                   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,


    CONSTRAINT pk_users PRIMARY KEY (id),
    CONSTRAINT uk_users_email UNIQUE (email),
    CONSTRAINT uk_users_unique_id UNIQUE (unique_id),
    CONSTRAINT fk_users_status FOREIGN KEY (status_id) REFERENCES status(id),
    CONSTRAINT fk_users_role FOREIGN KEY (role_id) REFERENCES roles(id)
);


CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_users_unique_id ON users(unique_id);
CREATE INDEX idx_users_status_id ON users(status_id);
CREATE INDEX idx_users_role_id ON users(role_id);