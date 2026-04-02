-- liquibase formatted sql
-- changeset umeshghimire:07
-- preconditions onFail:CONTINUE onError:HALT
CREATE TABLE IF NOT EXISTS forget_password
(
    id          BIGINT      AUTO_INCREMENT NOT NULL,
    version     BIGINT                     NOT NULL DEFAULT 0,
    email       VARCHAR(100)               NOT NULL,
    otp         VARCHAR(10)                NOT NULL,
    expiry_time DATETIME                   NOT NULL,
    is_used     BOOLEAN                    NOT NULL DEFAULT FALSE,
    created_at  DATETIME                   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  DATETIME                   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT pk_forget_password PRIMARY KEY (id)
);

CREATE INDEX idx_forget_password_email ON forget_password(email);
CREATE INDEX idx_forget_password_otp ON forget_password(otp);
CREATE INDEX idx_forget_password_expiry ON forget_password(expiry_time);
CREATE INDEX idx_forget_password_is_used ON forget_password(is_used);