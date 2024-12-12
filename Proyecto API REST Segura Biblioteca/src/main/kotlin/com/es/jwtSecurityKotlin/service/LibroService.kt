package com.es.jwtSecurityKotlin.service

import com.es.jwtSecurityKotlin.model.Editorial
import com.es.jwtSecurityKotlin.model.Libro
import com.es.jwtSecurityKotlin.repository.LibroRepository

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class LibroService {

    @Autowired
    private lateinit var libroRepository: LibroRepository

    fun createLibro(libro: Libro): Libro {
        // Comprobamos que el Libro no exista en la base de datos
        val titulo = libro.titulo ?: throw IllegalArgumentException("El titulo no puede ser nulo.")
        val editorial = libro.editorial ?: throw IllegalArgumentException("La editorial no puede ser nula.")

        // Un libro puede tener el mismo nombre y ser de diferentes editoriales
        if (libroRepository.findByTitulo(libro.titulo!!).isPresent && libroRepository.findByEditorial(libro.editorial!!).isPresent) {
            throw IllegalArgumentException("El Libro con titulo ${libro.titulo} ya existe con esa version de la editorial.")
        }

        // Guardamos el libro en la base de datos
        return libroRepository.save(libro)
    }

    // Devuelve todos los libros
    fun getAllLibros(): List<Libro> {
        return libroRepository.findAll()
    }

    // Devuelve un libro por su titulo
    fun getLibroByTitulo(titulo: String): Libro {
        return libroRepository.findByTitulo(titulo)
            .orElseThrow { IllegalArgumentException("No se encontró un libro con el título: $titulo") }
    }

    // Actualiza un libro existente
    fun updateLibro(titulo: String, updatedLibro: Libro): Libro {
        val libroExistente = libroRepository.findByTitulo(titulo)
            .orElseThrow { IllegalArgumentException("No se encontró un libro con el título: $titulo") }

        // Actualizamos los valores del libro existente
        libroExistente.titulo = updatedLibro.titulo ?: libroExistente.titulo
        libroExistente.editorial = updatedLibro.editorial ?: libroExistente.editorial
        libroExistente.autor = updatedLibro.autor ?: libroExistente.autor
        libroExistente.fecha_de_publicacion = updatedLibro.fecha_de_publicacion ?: libroExistente.fecha_de_publicacion

        // Guardamos los cambios en la base de datos
        return libroRepository.save(libroExistente)
    }

    fun deleteLibro(libro: Libro) {
        val libroExistente = libroRepository.findByTituloAndEditorial(libro.titulo!!, libro.editorial!!)
            .orElseThrow { IllegalArgumentException("No se encontró un libro con el título: ${libro.titulo} y la editorial: ${libro.editorial}") }

        // Comprobar si los datos coinciden
        if (libroExistente == libro) {
            libroRepository.delete(libroExistente)
        } else {
            throw IllegalArgumentException("El libro encontrado no coincide exactamente con el proporcionado.")
        }
    }


}
