# BookVerse

BookVerse es una aplicación web de tienda de libros desarrollada con Spring Boot. Permite consultar un catálogo público, ver el detalle de cada libro, registrarse como cliente, iniciar sesión, gestionar un carrito de compra y confirmar pedidos. Además, incluye una zona de administración para mantener libros, clientes y pedidos.

## Características principales

### Zona pública

- Página de inicio con libros destacados.
- Catálogo de libros accesible sin iniciar sesión.
- Búsqueda por ISBN, título, autor y género.
- Vista de detalle con portada, precio, descuento, stock, descripción y datos editoriales.
- Registro de nuevos clientes.
- Inicio y cierre de sesión.
- Página de acceso denegado para usuarios sin permisos.

### Carrito y pedidos

- Carrito de compra guardado en sesión.
- Añadir libros al carrito desde el catálogo o desde el detalle.
- Sumar, restar y eliminar unidades de cada libro.
- Vaciar el carrito completo.
- Cálculo de subtotales por línea y total del carrito.
- Comprobación de stock antes de añadir productos.
- Confirmación del carrito para crear un pedido asociado al usuario autenticado.

### Administración

- Gestión completa de libros: listado, creación, edición, detalle y eliminación.
- Gestión de descuentos, stock y disponibilidad de libros.
- Gestión completa de clientes.
- Gestión completa de pedidos.
- Creación de pedidos con varias líneas de libros.
- Cálculo automático de subtotales y total del pedido.
- Actualización del stock al crear pedidos.
- Validación de formularios.
- Protección de rutas de administración para usuarios con rol `ADMIN`.

## Tecnologías

- Java 21.
- Spring Boot 4.0.6.
- Spring MVC.
- Spring Data JPA.
- Spring Security.
- Thymeleaf.
- H2 Database.
- Bean Validation.
- Lombok.
- Maven.
- Bootstrap 5.
- Bootstrap Icons.

## Estructura del proyecto

```text
src/main/java
+-- com.salesianostriana.dam.proyectobookversedanielmejias2
    +-- controllers    # Controladores MVC
    +-- models         # Entidades del dominio
    +-- repository     # Repositorios JPA
    +-- security       # Configuración de seguridad
    +-- services       # Lógica de negocio

src/main/resources
+-- static             # CSS, JavaScript e imágenes
+-- templates          # Vistas Thymeleaf
+-- application.properties
+-- import.sql         # Datos iniciales
```

## Requisitos previos

Antes de ejecutar el proyecto necesitas:

- JDK 21 instalado.
- Git, si quieres clonar el repositorio.
- No es obligatorio instalar Maven, porque el proyecto incluye Maven Wrapper (`mvnw` y `mvnw.cmd`).

Puedes comprobar la versión de Java con:

```bash
java -version
```

## Puesta en marcha

1. Clona el repositorio o descarga el proyecto.

```bash
git clone <url-del-repositorio>
cd ProyectoBookVerseDanielMejias2
```

2. Arranca la aplicación con Maven Wrapper.

En Windows:

```bash
.\mvnw.cmd spring-boot:run
```

En Linux o macOS:

```bash
./mvnw spring-boot:run
```

3. Abre la aplicación en el navegador.

```text
http://localhost:9000
```

## Base de datos H2

El proyecto usa una base de datos H2 en memoria. En cada arranque se crea el esquema y se cargan los datos iniciales desde:

```text
src/main/resources/import.sql
```

La consola de H2 está disponible en:

```text
http://localhost:9000/h2-console
```

Datos de conexión:

| Campo | Valor |
| --- | --- |
| JDBC URL | `jdbc:h2:mem:bookversedb` |
| Usuario | `sa` |
| Contraseña | vacía |

## Usuarios de prueba

| Usuario | Contraseña | Rol |
| --- | --- | --- |
| `admin` | `admin` | `ADMIN` |
| `user` | `user` | `USER` |
| `carlos` | `pass123` | `USER` |

## Rutas principales

| Ruta | Descripción | Acceso |
| --- | --- | --- |
| `/` | Página principal | Público |
| `/catalogo` | Catálogo de libros | Público |
| `/libro/{isbn}` | Detalle de un libro | Público |
| `/registro` | Registro de cliente | Público |
| `/login` | Inicio de sesión | Público |
| `/carrito` | Carrito de compra | Usuario autenticado |
| `/admin/libros` | Gestión de libros | `ADMIN` |
| `/admin/clientes` | Gestión de clientes | `ADMIN` |
| `/admin/pedidos` | Gestión de pedidos | `ADMIN` |
| `/h2-console` | Consola de base de datos | Desarrollo |

## Ejecutar pruebas

Para lanzar la batería de pruebas:

```bash
.\mvnw.cmd test
```

En Linux o macOS:

```bash
./mvnw test
```

## Configuración destacada

La configuración principal se encuentra en `src/main/resources/application.properties`.

```properties
server.port=9000
spring.datasource.url=jdbc:h2:mem:bookversedb;DB_CLOSE_ON_EXIT=FALSE
spring.datasource.username=sa
spring.datasource.password=
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
```

## Seguridad

Las rutas públicas incluyen la página principal, el catálogo, el detalle de libros, el registro, el login, los recursos estáticos y la pantalla de acceso denegado. Las rutas bajo `/admin/**` requieren rol `ADMIN`. El resto de acciones privadas, como la gestión del carrito y la confirmación de pedidos, requieren un usuario autenticado.

## Autor

Proyecto desarrollado por Daniel Mejías Lora.
