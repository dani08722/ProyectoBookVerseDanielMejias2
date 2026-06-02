# BookVerse

BookVerse es una aplicación web desarrollada con Spring Boot para gestionar una tienda de libros. La aplicación permite consultar un catálogo público, ver el detalle de cada libro, registrarse como cliente, iniciar sesión, usar un carrito de compra y, desde la zona de administración, gestionar libros, clientes y pedidos.

## Funcionalidades básicas

### Zona pública

- Página principal con libros destacados seleccionados aleatoriamente.
- Catálogo de libros disponible para cualquier usuario.
- Búsqueda de libros por texto, incluyendo ISBN, título, autor y género.
- Vista de detalle de libro con portada, precio, descuento, stock, descripción y datos editoriales.
- Registro de nuevos clientes.
- Inicio y cierre de sesión.
- Pantalla de acceso denegado para usuarios sin permisos.

### Carrito y pedidos de usuario

- Carrito de compra mantenido en memoria de sesión.
- Añadir libros al carrito desde el catálogo o desde el detalle del libro.
- Restar unidades de un producto.
- Eliminar un producto concreto del carrito.
- Vaciar todos los productos del carrito.
- Cálculo de subtotales por línea y total del carrito.
- Control de stock al añadir productos.
- Confirmación del carrito para crear un pedido asociado al usuario autenticado.

### Administración

- Listado, creación, edición, detalle y eliminación de libros.
- Gestión de descuentos, stock y disponibilidad de libros.
- Listado, creación, edición, detalle y eliminación de clientes.
- Validación de datos de cliente, usuario, libro y pedido.
- Listado, creación, edición, detalle y eliminación de pedidos.
- Creación de pedidos con varias líneas de libros.
- Cálculo automático de subtotales y total del pedido.
- Actualización del stock al crear pedidos.
- Protección de rutas de administración para usuarios con rol `ADMIN`.

## Tecnologías y dependencias usadas

El proyecto está construido con Java 21 y Spring Boot 4.0.6. Las dependencias principales declaradas en `pom.xml` son:

- `spring-boot-starter-webmvc`: desarrollo web MVC con controladores y rutas.
- `spring-boot-starter-thymeleaf`: motor de plantillas HTML.
- `spring-boot-starter-data-jpa`: persistencia con JPA/Hibernate.
- `spring-boot-starter-security`: autenticación, roles y protección de rutas.
- `spring-boot-starter-validation`: validación de formularios y entidades.
- `spring-boot-h2console`: consola web para consultar la base de datos H2.
- `h2`: base de datos en memoria usada en desarrollo.
- `lombok`: reducción de código repetitivo en modelos, servicios y controladores.

En el frontend se usan:

- Thymeleaf para renderizar vistas del servidor.
- Bootstrap 5 para estilos y componentes.
- Bootstrap Icons para iconos.
- CSS y JavaScript propios en `src/main/resources/static`.

## Herramientas usadas

- Java 21.
- Maven
- Spring Boot.
- Spring Tools for Eclipse / Eclipse como entorno de trabajo del proyecto.
- Git para control de versiones.
- H2 Console para inspeccionar la base de datos durante el desarrollo.
- Navegador web para probar la aplicación.

Cuando la aplicación esté arrancada, se puede abrir en:

```text
http://localhost:9000
```

La consola de H2 está disponible en:

```text
http://localhost:9000/h2-console
```

Datos de conexión de H2:

```text
JDBC URL: jdbc:h2:mem:bookversedb
Usuario: sa
Contraseña: vacía
```

## Usuarios de prueba

La aplicación carga datos iniciales desde `src/main/resources/import.sql`.

| Usuario | Contraseña | Rol |
| --- | --- | --- |
| `admin` | `admin` | `ADMIN` |
| `user` | `user` | `USER` |
| `carlos` | `pass123` | `USER` |

## Base de datos

La base de datos usada es H2 en memoria. En cada arranque se crea de nuevo el esquema y se cargan los datos iniciales. Esto facilita las pruebas, aunque los datos se pierden al detener la aplicación.

## Seguridad

Las rutas públicas son la página principal, login, registro, catálogo, detalle de libros, recursos estáticos y acceso denegado. Las rutas bajo `/admin/**` requieren rol `ADMIN`. El resto de rutas requieren un usuario autenticado.