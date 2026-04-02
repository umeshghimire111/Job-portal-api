-- liquibase formatted sql
-- changeset umeshghimire:1
-- preconditions onFail:CONTINUE onError:HALT
-- precondition-sql-check expectedResult:0 SELECT COUNT(*) FROM roles

INSERT INTO roles (description, name)
VALUES
    ('ADMIN', 'ADMIN'),
    ('USER','USER'),
    ('CUSTOMER',  'CUSTOMER');
