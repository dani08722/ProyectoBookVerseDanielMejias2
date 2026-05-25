insert into app_user (id, username, password, role) values (1,'daniel','{noop}1234','ADMIN');
insert into app_user (id, username, password, role) values (2,'lucia','{noop}abcd','USER');
insert into app_user (id, username, password, role) values (3,'carlos','{noop}pass123','USER');
alter sequence app_user_seq restart with 4;

insert into cliente (id_cliente, nombre, apellidos, email, telefono, direccion, ciudad, codigo_postal, pais, fecha_registro, activo, user_id) values (1,'Daniel','Mejias','daniel@gmail.com','600111222','Calle Real 12','Sevilla','41001','Espana','2026-05-01',true,1);
insert into cliente (id_cliente, nombre, apellidos, email, telefono, direccion, ciudad, codigo_postal, pais, fecha_registro, activo, user_id) values (2,'Lucia','Fernandez','lucia@gmail.com','611222333','Avenida Sol 45','Madrid','28001','Espana','2026-05-02',true,2);
insert into cliente (id_cliente, nombre, apellidos, email, telefono, direccion, ciudad, codigo_postal, pais, fecha_registro, activo, user_id) values (3,'Carlos','Ruiz','carlos@gmail.com','622333444','Plaza Espana 7','Valencia','46001','Espana','2026-05-03',false,3);
alter sequence cliente_seq restart with 4;

insert into pedido (id_pedido, codigo, fecha, total, estado, metodo_pago, direccion_envio, cliente_id) values (1,'PED-001','2026-05-10',59.99,'PENDIENTE','Tarjeta','Calle Real 12, Sevilla',1);
insert into pedido (id_pedido, codigo, fecha, total, estado, metodo_pago, direccion_envio, cliente_id) values (2,'PED-002','2026-05-11',120.50,'ENVIADO','PayPal','Avenida Sol 45, Madrid',2);
insert into pedido (id_pedido, codigo, fecha, total, estado, metodo_pago, direccion_envio, cliente_id) values (3,'PED-003','2026-05-12',35.75,'ENTREGADO','Transferencia','Plaza Espana 7, Valencia',3);

insert into libro (isbn, titulo, autor, editorial, anio_publicacion, genero, idioma, numero_paginas, precio, stock, descripcion, imagen_url, disponible) values ('9788408274129','El Problema de los Tres Cuerpos','Liu Cixin','Nova',2022,'Ciencia Ficcion','Espanol',416,22.95,15,'Una novela de ciencia ficcion sobre el primer contacto con una civilizacion extraterrestre.','https://m.media-amazon.com/images/I/51rP8+IANzL.jpg',true);
insert into libro (isbn, titulo, autor, editorial, anio_publicacion, genero, idioma, numero_paginas, precio, stock, descripcion, imagen_url, disponible) values ('9788445006547','1984','George Orwell','Minotauro',2021,'Distopia','Espanol',352,14.95,20,'Una critica al totalitarismo y la vigilancia extrema.','https://m.media-amazon.com/images/I/51rXrmHv51L.jpg',true);
insert into libro (isbn, titulo, autor, editorial, anio_publicacion, genero, idioma, numero_paginas, precio, stock, descripcion, imagen_url, disponible) values ('9788466353771','Harry Potter y la piedra filosofal','J.K. Rowling','Salamandra',2019,'Fantasia','Espanol',256,18.50,30,'La primera aventura del joven mago Harry Potter.','https://m.media-amazon.com/images/I/51eylUrzhYL.jpg',true);

insert into linea_pedido (id_linea_pedido, pedido_id, libro_isbn, cantidad, precio_unitario) values (1,1,'9788408274129',1,22.95);
insert into linea_pedido (id_linea_pedido, pedido_id, libro_isbn, cantidad, precio_unitario) values (2,1,'9788445006547',2,14.95);
insert into linea_pedido (id_linea_pedido, pedido_id, libro_isbn, cantidad, precio_unitario) values (3,2,'9788466353771',1,18.50);
