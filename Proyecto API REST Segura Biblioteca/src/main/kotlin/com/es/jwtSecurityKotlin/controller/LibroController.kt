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
    private lateinit var usuarioService: UsuarioService
    @Autowired
    private lateinit var libroService: LibroService

    @PostMapping("/create")
    fun createLibro(@RequestBody newLibro: Libro): ResponseEntity<Libro> {
        // Llamar al UsuarioService para insertar un usuario
        val libroRegistrado = libroService.createLibro(newLibro)

        // Devolver el libro insertado
        return ResponseEntity(libroRegistrado, HttpStatus.CREATED)
    }

    @GetMapping
    fun getAllLibros(): ResponseEntity<List<Libro>> {
        val libros = libroService.getAllLibros()
        return ResponseEntity(libros, HttpStatus.OK)
    }

    @GetMapping("/{titulo}")
    fun getLibroByTitulo(@PathVariable titulo: String): ResponseEntity<Libro> {
        val libro = libroService.getLibroByTitulo(titulo)
        return ResponseEntity(libro, HttpStatus.OK)
    }

    @PutMapping("/{titulo}")
    fun updateLibro(@PathVariable titulo: String, @RequestBody updatedLibro: Libro): ResponseEntity<Libro> {
        val libroActualizado = libroService.updateLibro(titulo, updatedLibro)
        return ResponseEntity(libroActualizado, HttpStatus.OK)
    }

    @DeleteMapping("/delete")
    fun deleteLibro(@RequestBody libro: Libro): ResponseEntity<Void> {
        libroService.deleteLibro(libro)
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }

}
