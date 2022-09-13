INSERT INTO clientes(nombre, apellido, email, foto_url, created_at) VALUES('Alan', 'Brito', 'alan@mail.com', '', '2022-09-08');
INSERT INTO clientes(nombre, apellido, email, foto_url, created_at) VALUES('Aquiles', 'Traigo', 'aquiles.t@mail.com', '', '2022-09-08');
INSERT INTO clientes(nombre, apellido, email, foto_url, created_at) VALUES('Aquiles', 'Caigo', 'aquiles.c@mail.com', '', '2022-09-09');
INSERT INTO clientes(nombre, apellido, email, foto_url, created_at) VALUES('Fulano', 'Detal', 'fulano@mail.com', '', '2022-09-09');
INSERT INTO clientes(nombre, apellido, email, foto_url, created_at) VALUES('Fulana', 'Detal', 'fulana@mail.com', '', '2022-09-09');
INSERT INTO clientes(nombre, apellido, email, foto_url, created_at) VALUES('John', 'Doe', 'fulano@mail.com', '', '2022-09-09');
INSERT INTO clientes(nombre, apellido, email, foto_url, created_at) VALUES('Linus', 'Torvals', 'linux@mail.com', '', '2022-09-09');
INSERT INTO clientes(nombre, apellido, email, foto_url, created_at) VALUES('Richard', 'Stallman', 'gnu@mail.com', '', '2022-09-09');
INSERT INTO clientes(nombre, apellido, email, foto_url, created_at) VALUES('Doctor', 'Manhatan', 'dr.mt@mail.com', '', '2022-09-09');
INSERT INTO clientes(nombre, apellido, email, foto_url, created_at) VALUES('Alan', 'Moore', 'alan.m@mail.com', '', '2022-09-09');

INSERT INTO productos(nombre, precio, created_at) VALUES('Producto#1', 150.5, '2022-09-10');
INSERT INTO productos(nombre, precio, created_at) VALUES('Producto#2', 79.99, '2022-09-10');
INSERT INTO productos(nombre, precio, created_at) VALUES('Producto#3', 10.5, '2022-09-10');
INSERT INTO productos(nombre, precio, created_at) VALUES('Producto#4', 45.0, '2022-09-10');
INSERT INTO productos(nombre, precio, created_at) VALUES('Producto#5', 699.99, '2022-09-10');

INSERT INTO facturas(cliente_id, descripcion, created_at) VALUES(1, 'Esto es una factura.', '2022-09-10');

INSERT INTO factura_items(cantidad, factura_id, producto_id) VALUES(2, 1, 5);
INSERT INTO factura_items(cantidad, factura_id, producto_id) VALUES(4, 1, 3);
INSERT INTO factura_items(cantidad, factura_id, producto_id) VALUES(6, 1, 1);

INSERT INTO `users` (`id`, `username`, `password`, `enabled`) VALUES (1, 'frank', '$2a$10$kki9MQyfLqgcSTXyqc0W4ODW0vQsVgmDeeNzFV.99F2v.Est90mm.', 1);
INSERT INTO `users` (`id`, `username`, `password`, `enabled`) VALUES (2, 'admin', '$2a$10$t2p7nzbYU1hVKHyeZBE.Yef2oBWT832LQrS1MBOxsre337rG5lR4C', 1);

INSERT INTO `authorities` (`id`, `user_id`, `authority`) VALUES (1, 1, 'ROLE_USER');
INSERT INTO `authorities` (`id`, `user_id`, `authority`) VALUES (3, 2, 'ROLE_ADMIN');
INSERT INTO `authorities` (`id`, `user_id`, `authority`) VALUES (2, 2, 'ROLE_USER');