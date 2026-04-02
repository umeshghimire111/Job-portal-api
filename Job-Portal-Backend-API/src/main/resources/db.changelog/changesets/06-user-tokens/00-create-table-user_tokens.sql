-- liquibase formatted sql
-- changeset amritkthapa:06
-- preconditions onFail:CONTINUE onError:HALT

CREATE TABLE IF NOT EXISTS user_tokens
(

    id          BIGINT      AUTO_INCREMENT NOT NULL,
    version     BIGINT                     NOT NULL DEFAULT 0,

    access_token  VARCHAR(500)             NULL,
    refresh_token VARCHAR(500)             NULL,
    logged_out    BOOLEAN                  NOT NULL DEFAULT FALSE,
    user_id     BIGINT                     NULL,
    created_at  DATETIME                   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  DATETIME                   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT pk_user_tokens PRIMARY KEY (id),
    CONSTRAINT fk_user_tokens_user FOREIGN KEY (user_id) REFERENCES users(id)
);


CREATE INDEX idx_user_tokens_user_id ON user_tokens(user_id);
CREATE INDEX idx_user_tokens_access_token ON user_tokens(access_token);
CREATE INDEX idx_user_tokens_refresh_token ON user_tokens(refresh_token);
CREATE INDEX idx_user_tokens_logged_out ON user_tokens(logged_out);