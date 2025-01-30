package com.es.jwtSecurityKotlin.service

import com.es.jwtSecurityKotlin.exception.ConflictException
import com.es.jwtSecurityKotlin.exception.NotFoundException
import com.es.jwtSecurityKotlin.model.Libro
import com.es.jwtSecurityKotlin.model.PrestamoLibro
import com.es.jwtSecurityKotlin.repository.LibroRepository
import com.es.jwtSecurityKotlin.repository.PrestamoLibroRepository
import com.es.jwtSecurityKotlin.repository.UsuarioRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class PrestamoLibroService {

    @Autowired
    private lateinit var libroRepository: LibroRepository

    @Autowired
    private lateinit var usuarioRepository: UsuarioRepository

    @Autowired
    private lateinit var prestamoLibroRepository: PrestamoLibroRepository

    fun setPrestamo(username: String, libro: Libro): PrestamoLibro {
        // Comprobar si el usuario existe
        val usuario = usuarioRepository.findByUsername(username)
            .orElseThrow { NotFoundException("No se encontró un usuario con el nombre de usuario: $username") }

        // Comprobar si el libro existe
        val libroExistente = libroRepository.findById(libro.id_libro!!)
            .orElseThrow { NotFoundException("No se encontró un libro con el ID: ${libro.id_libro}") }

        // Comprobar si el usuario tiene un préstamo activo sin devolver
        if (prestamoLibroRepository.existsByUsuarioAndDevueltoFalse(usuario)) {
            throw ConflictException("El usuario $username ya tiene un préstamo activo sin devolver.")
        }

        // Comprobar si el libro ya está siendo prestado y no ha sido devuelto
        if (prestamoLibroRepository.existsByLibroAndDevueltoFalse(libroExistente)) {
            throw ConflictException("El libro con título '${libroExistente.titulo}' ya está siendo prestado.")
        }

        // Crear un nuevo préstamo con la fecha de préstamo actual y límite de devolución
        val fechaPrestamo = Date()
        val limitePrestamo = Calendar.getInstance().apply {
            time = fechaPrestamo
            add(Calendar.DAY_OF_MONTH, 30)  }.time

        val nuevoPrestamo = PrestamoLibro(
            libro = libroExistente,
            usuario = usuario,
            devuelto = false,
            fecha_prestamo = fechaPrestamo,
            limite_prestamo = limitePrestamo
        )

        // Guardar el préstamo en la base de datos
        return prestamoLibroRepository.save(nuevoPrestamo)
    }

    // Marcar como devuelto
    fun marcarComoDevuelto(prestamoId: Long): PrestamoLibro {
        // Buscar el préstamo por ID
        val prestamo = prestamoLibroRepository.findById(prestamoId)
            .orElseThrow {  NotFoundException("No se encontró un préstamo con ID: $prestamoId") }

        // Verificar si el préstamo ya está marcado como devuelto
        if (prestamo.devuelto) {
            throw ConflictException("El préstamo con ID: $prestamoId ya está marcado como devuelto.")
        }

        // Marcar como devuelto
        prestamo.devuelto = true
        return prestamoLibroRepository.save(prestamo)
    }

    // Get todos los prestamos
    fun getTodosLosPrestamos(): List<PrestamoLibro> {
        return prestamoLibroRepository.findAll()
    }

    // Get prestamo por ID
    fun getPrestamoPorId(prestamoId: Long): PrestamoLibro {
        return prestamoLibroRepository.findById(prestamoId)
            .orElseThrow { NotFoundException("No se encontró un préstamo con ID: $prestamoId") }
    }

}
