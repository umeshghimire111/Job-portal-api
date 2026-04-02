-- liquibase formatted sql
-- changeset umeshghimire:09
-- preconditions onFail:CONTINUE onError:HALT

CREATE TABLE IF NOT EXISTS upload_profiles
(

    id          BIGINT      AUTO_INCREMENT NOT NULL,

    version     BIGINT                     NOT NULL DEFAULT 0,

    name            VARCHAR(100)           NOT NULL,
    email           VARCHAR(100)           NOT NULL,
    profile_picture VARCHAR(500)           NULL,

    created_at  DATETIME                   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  DATETIME                   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,


    CONSTRAINT pk_upload_profiles PRIMARY KEY (id),
    CONSTRAINT uk_upload_profiles_email UNIQUE (email)
);


CREATE INDEX idx_upload_profiles_email ON upload_profiles(email);
