# Proyecto-API-REST-Segura-Biblioteca

Daniel Hernández Gómez

Este proyecto implementa una base de datos para gestionar una biblioteca. La base de datos contiene cuatro tablas principales: `Usuarios`, `Libros`, `Editoriales`, y `Prestamo_Libros`, cada una con sus respectivos campos y restricciones.

## Estructura de las Tablas

### Tabla: Usuarios
| Campo          | Restricciones                                              |
|----------------|------------------------------------------------------------|
| `id_usuario`   | Se genera automáticamente.                                |
| `username`     | Único y no puede ser nulo.                                |
| `password`     | No puede ser nulo y se encripta.                          |
| `nombre`       | No puede ser nulo.                                        |
| `apellidos`    | No puede ser nulo.                                        |
| `roles`        | Debe ser `Admin` o `User`.                                |

### Tabla: Libros
| Campo                | Restricciones                                         |
|----------------------|-------------------------------------------------------|
| `id_libro`           | Se genera automáticamente.                           |
| `titulo`             | No puede ser nulo.                                   |
| `autor`              | No puede ser nulo.                                   |
| `editorial`          | No puede ser nulo y tiene relación 1 a 1 con `Editoriales`. |
| `fecha_de_publicacion` | No puede ser nulo y debe ser una fecha.             |

### Tabla: Editoriales
| Campo              | Restricciones                                          |
|--------------------|--------------------------------------------------------|
| `id_editorial`     | Se genera automáticamente.                            |
| `nombre`           | No puede ser nulo.                                    |
| `apellidos`        | No puede ser nulo.                                    |
| `fecha_de_nacimiento` | No puede ser nulo y debe ser una fecha.             |
| `libros`           | No puede ser nulo y tiene relación 1 a muchos con `Libros`. |

### Tabla: Prestamo_Libros
| Campo              | Restricciones                                          |
|--------------------|--------------------------------------------------------|
| `id_prestamo`      | Se genera automáticamente.                            |
| `libro`            | No puede ser nulo y tiene relación 1 a 1 con `Libros`. |
| `usuario`          | No puede ser nulo y tiene relación 1 a 1 con `Usuarios`. |
| `fecha_prestamo`   | No puede ser nulo y debe ser una fecha.                |
| `limite_prestamo`  | No puede ser nulo, debe ser una fecha y es una semana más al préstamo. |

---

## Relación entre Tablas
- **Usuarios** se relaciona con **Prestamo_Libros** (1 a 1).
- **Libros** se relaciona con **Editoriales** (1 a 1).
- **Libros** se relaciona con **Prestamo_Libros** (1 a 1).
- **Editoriales** se relaciona con **Libros** (1 a muchos).

Este diseño asegura la integridad referencial y facilita la gestión de usuarios, libros, editoriales, y préstamos en la biblioteca.
