-- liquibase formatted sql
-- changeset umeshghimire:05
-- preconditions onFail:CONTINUE onError:HALT
CREATE TABLE IF NOT EXISTS status
(
    id          TINYINT     AUTO_INCREMENT NOT NULL,
    version     BIGINT                     NOT NULL DEFAULT 0,
    name        VARCHAR(50)                NOT NULL,
    description VARCHAR(255)               NOT NULL,
    color       VARCHAR(20)                NULL,
    created_at  DATETIME                   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  DATETIME                   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT pk_status PRIMARY KEY (id),
    CONSTRAINT uk_status_name UNIQUE (name)
);