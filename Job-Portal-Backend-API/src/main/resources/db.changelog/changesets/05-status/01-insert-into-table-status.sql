-- liquibase formatted sql
-- changeset umeshghimire:1
-- preconditions onFail:CONTINUE onError:HALT
-- precondition-sql-check expectedResult:0 SELECT COUNT(*) FROM status

INSERT INTO status (description, color, name)
VALUES
    ('ACTIVE', '#14A44D', 'ACTIVE'),
    ('DELETED', '#DC4C64', 'DELETED'),
    ('PENDING', '#E4A11B', 'PENDING'),
    ('BLOCKED', '#3B71CA', 'BLOCKED');
