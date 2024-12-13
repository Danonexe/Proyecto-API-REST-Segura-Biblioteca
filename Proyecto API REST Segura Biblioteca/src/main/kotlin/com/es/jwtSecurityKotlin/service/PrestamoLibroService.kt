package com.es.jwtSecurityKotlin.service

import com.es.jwtSecurityKotlin.model.Libro
import com.es.jwtSecurityKotlin.model.PrestamoLibro
import com.es.jwtSecurityKotlin.model.Usuario
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

    // Crear un nuevo préstamo
    fun createPrestamoLibro(usuarioId: Long, libroId: Long): PrestamoLibro {
        // Primero comprovaciones
        val usuario = usuarioRepository.findById(usuarioId)
            .orElseThrow { IllegalArgumentException("No se encontró un usuario con ID: $usuarioId") }

        val libro = libroRepository.findById(libroId)
            .orElseThrow { IllegalArgumentException("No se encontró un libro con ID: $libroId") }

        // Verificar si el usuario tiene préstamos sin devolver
        val prestamosPendientes = prestamoLibroRepository.findByUsuarioIdAndDevueltoFalse(usuarioId)
        if (prestamosPendientes.isPresent && prestamosPendientes.get().isNotEmpty()) {
            throw IllegalArgumentException("El usuario ya tiene un préstamo sin devolver.")
        }

        // Verificar si el libro ya está prestado
        val prestamoLibroExistente = prestamoLibroRepository.findByLibroIdAndDevueltoFalse(libroId)
        if (prestamoLibroExistente.isPresent) {
            throw IllegalArgumentException("El libro ya está prestado a otro usuario.")
        }

        // Crear un nuevo préstamo
        val fechaPrestamo = Date()
        val limitePrestamo = Calendar.getInstance().apply {
            time = fechaPrestamo
            add(Calendar.DAY_OF_MONTH, 30)
        }.time

        val nuevoPrestamo = PrestamoLibro(
            libro = libro,
            usuario = usuario,
            devuelto = false,
            fecha_prestamo = fechaPrestamo,
            limite_prestamo = limitePrestamo
        )

        return prestamoLibroRepository.save(nuevoPrestamo)
    }

    // Devolver un préstamo
    fun devolverPrestamo(prestamoId: Long) {
        val prestamo = prestamoLibroRepository.findById(prestamoId)
            .orElseThrow { IllegalArgumentException("No se encontró un préstamo con ID: $prestamoId") }

        prestamo.devuelto = true
        prestamoLibroRepository.save(prestamo)
    }
}