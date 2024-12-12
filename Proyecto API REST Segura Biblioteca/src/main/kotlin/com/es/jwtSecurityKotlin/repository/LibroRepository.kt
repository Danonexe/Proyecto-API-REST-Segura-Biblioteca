package com.es.jwtSecurityKotlin.repository

import com.es.jwtSecurityKotlin.model.Libro
import com.es.jwtSecurityKotlin.model.Usuario
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface LibroRepository: JpaRepository<Libro, Long> {
    fun findByTitulo(username: String) : Optional<Libro>
    fun findByEditorial(username: String) : Optional<Libro>
}