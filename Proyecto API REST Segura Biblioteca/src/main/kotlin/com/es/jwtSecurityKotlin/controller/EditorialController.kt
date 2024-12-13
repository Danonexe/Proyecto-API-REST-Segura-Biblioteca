package com.es.jwtSecurityKotlin.controller

import com.es.jwtSecurityKotlin.model.Editorial
import com.es.jwtSecurityKotlin.service.EditorialService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/editoriales")
class EditorialController {

    @Autowired
    private lateinit var editorialService: EditorialService

    // Crear una nueva editorial
    @PostMapping("/create")
    fun createEditorial(@RequestBody newEditorial: Editorial): ResponseEntity<Editorial> {
        val editorialCreada = editorialService.createEditorial(newEditorial)
        return ResponseEntity(editorialCreada, HttpStatus.CREATED)
    }

    // Obtener todas las editoriales
    @GetMapping
    fun getAllEditoriales(): ResponseEntity<List<Editorial>> {
        val editoriales = editorialService.getAllEditoriales()
        return ResponseEntity(editoriales, HttpStatus.OK)
    }

    // Obtener una editorial por su nombre
    @GetMapping("/{nombre}")
    fun getEditorialByNombre(@PathVariable nombre: String): ResponseEntity<Editorial> {
        val editorial = editorialService.getEditorialByNombre(nombre)
        return ResponseEntity(editorial, HttpStatus.OK)
    }

    // Actualizar una editorial por su nombre
    @PutMapping("/{nombre}")
    fun updateEditorial(@PathVariable nombre: String, @RequestBody updatedEditorial: Editorial): ResponseEntity<Editorial> {
        val editorialActualizada = editorialService.updateEditorial(nombre, updatedEditorial)
        return ResponseEntity(editorialActualizada, HttpStatus.OK)
    }

    // Eliminar una editorial por su nombre
    @DeleteMapping("/{nombre}")
    fun deleteEditorial(@PathVariable nombre: String): ResponseEntity<Void> {
        editorialService.deleteEditorial(nombre)
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }

    // Obtener todos los libros de una editorial
    @GetMapping("/{nombreEditorial}/libros")
    fun getLibrosByEditorial(@PathVariable nombreEditorial: String): ResponseEntity<Any> {
        val libros = editorialService.getLibrosByEditorial(nombreEditorial)
        return if (libros.isPresent) {
            ResponseEntity(libros.get(), HttpStatus.OK)
        } else {
            ResponseEntity("No se encontraron libros para la editorial: $nombreEditorial", HttpStatus.NOT_FOUND)
        }
    }
}
