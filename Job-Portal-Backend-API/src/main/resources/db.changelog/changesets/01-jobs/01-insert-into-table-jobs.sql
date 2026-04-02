-- liquibase formatted sql
-- changeset umeshghimire:01
-- preconditions onFail:CONTINUE onError:HALT
-- precondition-sql-check expectedResult:0 SELECT COUNT(*) FROM statusNSERT INTO jobs (job_name, title, description, location, salary, created_at, updated_at)
-- For Integer/Long ID (auto-increment)
INSERT INTO jobs (job_name, title, description, location, salary, created_at, updated_at, version)
VALUES ('mernstack', 'MERN Stack Developer', 'Full stack developer with MongoDB, Express, React, Node.js', 'ktm', 85000.00, NOW(), NOW(), 0);