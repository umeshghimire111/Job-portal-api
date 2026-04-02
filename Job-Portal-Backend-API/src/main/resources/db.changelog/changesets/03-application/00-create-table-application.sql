-- liquibase formatted sql
-- changesets umeshghimire:03
-- preconditions onFail:CONTINUE onError:HALT

CREATE TABLE IF NOT EXISTS applications
(

    id          BIGINT      AUTO_INCREMENT NOT NULL,
    version     BIGINT                     NOT NULL DEFAULT 0,
    job_id      BIGINT                     NOT NULL,
    candidate_id BIGINT                    NOT NULL,

    resume_path VARCHAR(500)               NOT NULL,
    email       VARCHAR(100)               NOT NULL,
    status      VARCHAR(50)                NOT NULL,
    created_at  DATETIME                   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  DATETIME                   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT pk_applications PRIMARY KEY (id),
    CONSTRAINT fk_applications_job FOREIGN KEY (job_id) REFERENCES jobs(id),
    CONSTRAINT fk_applications_candidate FOREIGN KEY (candidate_id) REFERENCES users(id)
);

CREATE INDEX idx_applications_job_id ON applications(job_id);
CREATE INDEX idx_applications_candidate_id ON applications(candidate_id);
CREATE INDEX idx_applications_email ON applications(email);
CREATE INDEX idx_applications_status ON applications(status);