CREATE SEQUENCE usuario_id_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE usuario (
    id BIGINT PRIMARY KEY DEFAULT NEXTVAL('usuario_id_seq'),
    username VARCHAR(255),
    password VARCHAR(255)
);

CREATE TABLE usuario_roles (
   usuario_id BIGINT,
    roles_id BIGINT
);

