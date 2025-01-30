# Proyecto-API-REST-Segura-Biblioteca

## Autor
**Daniel Hernández Gómez**

## Introducción del Proyecto

### **Título del Proyecto:**
**Biblioteca Gómez**

### **Idea del Proyecto:**
Crear una API que gestione el préstamo de libros y el registro de usuarios y libros mediante una base de datos.

### **Justificación:**
La aplicación permite controlar la gestión de usuarios, libros y préstamos, mejorando la eficiencia y la experiencia tanto de clientes como de empleados.

---

## Descripción de las Tablas

### **Usuarios**
Representa a los usuarios registrados en el sistema.
- **id** (PK): Identificador único del usuario.
- **username** (Unique, Not Null): Nombre de usuario.
- **password** (Not Null): Contraseña cifrada.
- **nombre** (Not Null): Nombre del usuario.
- **apellidos** (Not Null): Apellidos del usuario.
- **roles** (Not Null): Roles asignados al usuario.

---

### **Editoriales**
Representa a las editoriales responsables de la publicación de libros.
- **id_editorial** (PK): Identificador único de la editorial.
- **nombre** (Not Null): Nombre de la editorial.

---

### **Libros**
Representa a los libros disponibles en el sistema.
- **id_libro** (PK): Identificador único del libro.
- **titulo** (Not Null): Título del libro.
- **autor** (Not Null): Autor del libro.
- **editorial** (FK): Relación con la tabla Editoriales.
- **fecha_de_publicacion** (Not Null): Fecha de publicación del libro.

---

### **PrestamoLibro**
Representa el préstamo de un libro a un usuario.
- **id** (PK): Identificador único del préstamo.
- **libro** (FK): Relación con la tabla Libros.
- **usuario** (FK): Relación con la tabla Usuarios.
- **devuelto**: Indica si el libro ha sido devuelto.
- **fecha_prestamo**: Fecha en la que se realizó el préstamo.
- **limite_prestamo**: Fecha límite para devolver el libro.

