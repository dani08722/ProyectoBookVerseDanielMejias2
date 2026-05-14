insert into cliente (id_cliente, nombre, apellidos, email, telefono, direccion, ciudad, codigo_postal, pais, password, fecha_registro, activo) values (1,'Daniel','Mejias','daniel@gmail.com','600111222','Calle Real 12','Sevilla','41001','Espana','1234','2026-05-01',true);
insert into cliente (id_cliente, nombre, apellidos, email, telefono, direccion, ciudad, codigo_postal, pais, password, fecha_registro, activo) values (2,'Lucia','Fernandez','lucia@gmail.com','611222333','Avenida Sol 45','Madrid','28001','Espana','abcd','2026-05-02',true);
insert into cliente (id_cliente, nombre, apellidos, email, telefono, direccion, ciudad, codigo_postal, pais, password, fecha_registro, activo) values (3,'Carlos','Ruiz','carlos@gmail.com','622333444','Plaza Espana 7','Valencia','46001','Espana','pass123','2026-05-03',false);

insert into pedido (id_pedido, codigo, fecha, total, estado, metodo_pago, direccion_envio, cliente_id) values (1,'PED-001','2026-05-10',59.99,'pendiente','Tarjeta','Calle Real 12, Sevilla',1);
insert into pedido (id_pedido, codigo, fecha, total, estado, metodo_pago, direccion_envio, cliente_id) values (2,'PED-002','2026-05-11',120.50,'enviado','PayPal','Avenida Sol 45, Madrid',2);
insert into pedido (id_pedido, codigo, fecha, total, estado, metodo_pago, direccion_envio, cliente_id) values (3,'PED-003','2026-05-12',35.75,'entregado','Transferencia','Plaza Espana 7, Valencia',3);

insert into libro (isbn, titulo, autor, editorial, anio_publicacion, genero, idioma, numero_paginas, precio, stock, descripcion, imagen_url, disponible) values ('9788408274129','El Problema de los Tres Cuerpos','Liu Cixin','Nova',2022,'Ciencia Ficcion','Espanol',416,22.95,15,'Una novela de ciencia ficcion sobre el primer contacto con una civilizacion extraterrestre.','https://example.com/img/trescuerpos.jpg',true);
insert into libro (isbn, titulo, autor, editorial, anio_publicacion, genero, idioma, numero_paginas, precio, stock, descripcion, imagen_url, disponible) values ('9788445006547','1984','George Orwell','Minotauro',2021,'Distopia','Espanol',352,14.95,20,'Una critica al totalitarismo y la vigilancia extrema.','https://example.com/img/1984.jpg',true);
insert into libro (isbn, titulo, autor, editorial, anio_publicacion, genero, idioma, numero_paginas, precio, stock, descripcion, imagen_url, disponible) values ('9788466353771','Harry Potter y la piedra filosofal','J.K. Rowling','Salamandra',2019,'Fantasia','Espanol',256,18.50,30,'La primera aventura del joven mago Harry Potter.','https://example.com/img/harrypotter1.jpg',true);

insert into linea_pedido (id_linea_pedido, pedido_id, libro_isbn, cantidad, precio_unitario) values (1,1,'9788408274129',1,22.95);
insert into linea_pedido (id_linea_pedido, pedido_id, libro_isbn, cantidad, precio_unitario) values (2,1,'9788445006547',2,14.95);
insert into linea_pedido (id_linea_pedido, pedido_id, libro_isbn, cantidad, precio_unitario) values (3,2,'9788466353771',1,18.50);
