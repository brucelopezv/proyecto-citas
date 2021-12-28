INSERT INTO USUARIOS (USUARIO, PASS, ENABLED, NOMBRE, APELLIDO) VALUES ('bruce', '$2a$10$nCzqXTwMfzfEATy05B6IeeA9M7tz/CKd.aV2djW9O/zXT3S7cMdci', 1,'bruce','lopez');
INSERT INTO USUARIOS (USUARIO, PASS, ENABLED, NOMBRE, APELLIDO) VALUES ('admin', '$2a$10$GnpeVTPUzwfhClAJMxty5ueDQXoa29pvjRaotyqOKyE.9iH0SiJte', 1, 'admin','admin');

INSERT INTO ROLES (NOMBRE) VALUES ('ROLE_USUARIO');
INSERT INTO ROLES (NOMBRE) VALUES ('ROLE_ADMIN');

INSERT INTO USUARIOS_ROLES (USUARIO_ID, ROL_ID) VALUES (1,1);
INSERT INTO USUARIOS_ROLES (USUARIO_ID, ROL_ID) VALUES (2,2);
INSERT INTO USUARIOS_ROLES (USUARIO_ID, ROL_ID) VALUES (2,1);

INSERT INTO ESTADOS (DESCRIPCION) VALUES ('PENDIENTE');
INSERT INTO ESTADOS (DESCRIPCION) VALUES ('COMPLETADA');
INSERT INTO ESTADOS (DESCRIPCION) VALUES ('CANCELADA');