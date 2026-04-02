-- liquibase formatted sql
-- changeset umeshghimire:04
-- preconditions onFail:CONTINUE onError:HALT

CREATE TABLE IF NOT EXISTS roles
(
    id          BIGINT      AUTO_INCREMENT NOT NULL,
    version     BIGINT                     NOT NULL DEFAULT 0,
    name        VARCHAR(50)                NOT NULL,
    description VARCHAR(255)               NOT NULL,
    created_at  DATETIME                   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  DATETIME                   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT pk_roles PRIMARY KEY (id),
    CONSTRAINT uk_roles_name UNIQUE (name)
);