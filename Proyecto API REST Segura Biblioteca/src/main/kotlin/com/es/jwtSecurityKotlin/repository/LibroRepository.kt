package com.es.jwtSecurityKotlin.repository

import com.es.jwtSecurityKotlin.model.Editorial
import com.es.jwtSecurityKotlin.model.Libro
import com.es.jwtSecurityKotlin.model.Usuario
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface LibroRepository: JpaRepository<Libro, Long> {
    fun findByTitulo(titulo: String) : Optional<Libro>
    fun findByEditorial(editorial: Editorial) : Optional<Libro>
    fun findByTituloAndEditorial(titulo: String, editorial: Editorial): Optional<Libro>
}