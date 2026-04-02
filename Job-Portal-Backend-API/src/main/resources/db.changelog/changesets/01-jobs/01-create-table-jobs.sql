-- liquibase formatted sql
-- changeset umeshghimire:01
-- preConditions onFail:CONTINUE onError:HALT

CREATE TABLE IF NOT EXISTS jobs
(
    id          BIGINT      AUTO_INCREMENT NOT NULL,
    version     BIGINT                     NOT NULL DEFAULT 0,
    job_name    VARCHAR(100)               NOT NULL,
    title       VARCHAR(150)               NOT NULL,
    description TEXT                       NOT NULL,
    location    VARCHAR(100)               NOT NULL,
    salary      DECIMAL(10,2)              NOT NULL,
    created_at  DATETIME                   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  DATETIME                   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT pk_jobs PRIMARY KEY (id),
    CONSTRAINT uk_jobs_job_name UNIQUE (job_name)
);

