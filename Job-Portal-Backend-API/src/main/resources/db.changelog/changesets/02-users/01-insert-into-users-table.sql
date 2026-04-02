-- liquibase formatted sql
-- changeset umeshghimire:01
-- preconditions onFail:CONTINUE onError:HALT
-- precondition-sql-check expectedResult:0 SELECT COUNT(*) from users where email ="umeshghimire443@gmail.com"
INSERT INTO users (full_name,email,password,phone_number,role_id,unique_id,status_id,version)
VALUES
    (
        'Umesh Ghimire','umeshghimire443@gmail.com','$2a$10$/mlFHXo18RdKa6ynokj0..TwO5OnAtpQqIkVhFPK2ifNNNq5E1vBy','9807017037',1,UUID(),1,0
    );



