package com.es.jwtSecurityKotlin.controller

import com.es.jwtSecurityKotlin.model.Libro
import com.es.jwtSecurityKotlin.model.Usuario
import com.es.jwtSecurityKotlin.service.LibroService
import com.es.jwtSecurityKotlin.service.UsuarioService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
@RestController
@RequestMapping("/libros")
class LibroController {

    @Autowired
    private lateinit var libroService: LibroService

    // Crear un nuevo libro
    @PostMapping("/create")
    fun createLibro(@RequestBody newLibro: Libro): ResponseEntity<Libro> {
        val libroRegistrado = libroService.createLibro(newLibro)
        return ResponseEntity(libroRegistrado, HttpStatus.CREATED)
    }

    // Obtener todos los libros
    @GetMapping
    fun getAllLibros(): ResponseEntity<List<Libro>> {
        val libros = libroService.getAllLibros()
        return ResponseEntity(libros, HttpStatus.OK)
    }

    // Obtener un libro por su título
    @GetMapping("/{id}")
    fun getLibroByTitulo(@PathVariable id: Long): ResponseEntity<Libro> {
        val libro = libroService.getLibroById(id)
        return ResponseEntity(libro, HttpStatus.OK)
    }

    // Actualizar un libro existente
    @PutMapping("/{id}")
    fun updateLibro(@PathVariable id: Long, @RequestBody updatedLibro: Libro): ResponseEntity<Libro> {
        val libroActualizado = libroService.updateLibro(id, updatedLibro)
        return ResponseEntity(libroActualizado, HttpStatus.OK)
    }

    // Eliminar un libro por ID
    @DeleteMapping("/{id}")
    fun deleteLibro(@PathVariable id: Long): ResponseEntity<Void> {
        libroService.deleteLibroById(id)
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }
}
