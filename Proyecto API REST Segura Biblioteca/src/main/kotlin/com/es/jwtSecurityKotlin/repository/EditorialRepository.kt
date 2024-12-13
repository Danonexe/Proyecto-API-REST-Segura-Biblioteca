package com.es.jwtSecurityKotlin.repository

import com.es.jwtSecurityKotlin.model.Editorial
import com.es.jwtSecurityKotlin.model.Usuario
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface EditorialRepository: JpaRepository<Editorial, Long> {
    fun findByNombre(nombre: String) : Optional<Editorial>
}