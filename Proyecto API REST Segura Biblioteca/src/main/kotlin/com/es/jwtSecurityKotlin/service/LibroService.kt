package com.es.jwtSecurityKotlin.service

import com.es.jwtSecurityKotlin.model.Libro
import com.es.jwtSecurityKotlin.repository.LibroRepository

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class LibroService {

    @Autowired
    private lateinit var libroRepository: LibroRepository


fun CreateLibro(libro: Libro): Libro {
        // Comprobamos que el Libro no exista en la base de datos
        val titulo = libro.titulo ?: throw IllegalArgumentException("El titulo no puede ser nulo.")
        val editorial = libro.editorial ?: throw IllegalArgumentException("La editorial no puede ser nula.")

        //Un libro puede tener el mismo nombre y ser de diferentes editoriales
        if (libroRepository.findByTitulo(libro.titulo!!).isPresent && libroRepository.findByEditorial(libro.editorial!!.toString()).isPresent) {
            throw IllegalArgumentException("El Libro con titulo ${libro.titulo} ya existe con esa version de la editorial.")
        }

        // Guardamos el libro en la base de datos
        return libroRepository.save(libro)
    }


}