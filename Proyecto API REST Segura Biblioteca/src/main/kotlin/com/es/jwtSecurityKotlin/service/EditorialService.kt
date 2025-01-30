package com.es.jwtSecurityKotlin.service

import com.es.jwtSecurityKotlin.exception.BadRequestException
import com.es.jwtSecurityKotlin.exception.ConflictException
import com.es.jwtSecurityKotlin.exception.NotFoundException
import com.es.jwtSecurityKotlin.model.Editorial
import com.es.jwtSecurityKotlin.model.Libro
import com.es.jwtSecurityKotlin.repository.EditorialRepository
import com.es.jwtSecurityKotlin.repository.LibroRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class EditorialService {

    @Autowired
    private lateinit var editorialRepository: EditorialRepository

    @Autowired
    private lateinit var libroRepository: LibroRepository

    // Crear una nueva editorial
    fun createEditorial(editorial: Editorial): Editorial {
        val nombre = editorial.nombre ?: throw BadRequestException("El nombre de la editorial no puede ser nulo.")

        // Comprobar si ya existe una editorial con el mismo nombre
        if (editorialRepository.findByNombre(nombre).isPresent) {
            throw ConflictException("Ya existe una editorial con el nombre: $nombre")
        }

        // Guardar la nueva editorial
        return editorialRepository.save(editorial)
    }

    // Obtener todas las editoriales
    fun getAllEditoriales(): List<Editorial> {
        return editorialRepository.findAll()
    }

    // Obtener una editorial por su nombre
    fun getEditorialByNombre(nombre: String): Editorial {
        return editorialRepository.findByNombre(nombre)
            .orElseThrow { NotFoundException("No se encontró una editorial con el nombre: $nombre") }
    }

    // Actualizar una editorial existente
    fun updateEditorial(nombre: String, updatedEditorial: Editorial): Editorial {
        val editorialExistente = editorialRepository.findByNombre(nombre)
            .orElseThrow { NotFoundException("No se encontró una editorial con el nombre: $nombre") }

        // Actualizar los campos de la editorial existente
        editorialExistente.nombre = updatedEditorial.nombre ?: editorialExistente.nombre

        // Guardar los cambios en la base de datos
        return editorialRepository.save(editorialExistente)
    }

    // Eliminar una editorial
    fun deleteEditorial(nombre: String) {
        val editorialExistente = editorialRepository.findByNombre(nombre)
            .orElseThrow { NotFoundException("No se encontró una editorial con el nombre: $nombre") }

        // Eliminar la editorial encontrada
        editorialRepository.delete(editorialExistente)
    }

    // Obtener todos los libros de una editorial
    fun getLibrosByEditorial(nombreEditorial: String): Optional<Libro> {
        val editorial = editorialRepository.findByNombre(nombreEditorial)
            .orElseThrow { NotFoundException("No se encontró una editorial con el nombre: $nombreEditorial") }

        return libroRepository.findByEditorial(editorial)
    }
}
