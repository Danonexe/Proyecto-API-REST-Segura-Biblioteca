package com.es.jwtSecurityKotlin.controller

import com.es.jwtSecurityKotlin.model.PrestamoLibro
import com.es.jwtSecurityKotlin.model.Libro
import com.es.jwtSecurityKotlin.service.PrestamoLibroService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/prestamos")
class PrestamoLibroController {

    @Autowired
    private lateinit var prestamoLibroService: PrestamoLibroService

    // Crear un nuevo préstamo
    @PostMapping("/create")
    fun createPrestamo(@RequestParam username: String, @RequestBody libro: Libro): ResponseEntity<PrestamoLibro> {
        val nuevoPrestamo = prestamoLibroService.setPrestamo(username, libro)
        return ResponseEntity(nuevoPrestamo, HttpStatus.CREATED)
    }

    // Marcar un préstamo como devuelto
    @PutMapping("/devolver/{id}")
    fun marcarComoDevuelto(@PathVariable id: Long): ResponseEntity<PrestamoLibro> {
        val prestamoDevuelto = prestamoLibroService.marcarComoDevuelto(id)
        return ResponseEntity(prestamoDevuelto, HttpStatus.OK)
    }

    // Obtener todos los préstamos
    @GetMapping
    fun getTodosLosPrestamos(): ResponseEntity<List<PrestamoLibro>> {
        val prestamos = prestamoLibroService.getTodosLosPrestamos()
        return ResponseEntity(prestamos, HttpStatus.OK)
    }

    // Obtener un préstamo por ID
    @GetMapping("/{id}")
    fun getPrestamoPorId(@PathVariable id: Long): ResponseEntity<PrestamoLibro> {
        val prestamo = prestamoLibroService.getPrestamoPorId(id)
        return ResponseEntity(prestamo, HttpStatus.OK)
    }
}
