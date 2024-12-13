package com.es.jwtSecurityKotlin.repository

import com.es.jwtSecurityKotlin.model.Libro
import com.es.jwtSecurityKotlin.model.PrestamoLibro
import com.es.jwtSecurityKotlin.model.Usuario
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface PrestamoLibroRepository : JpaRepository<PrestamoLibro, Long> {
    fun findByUsuarioIdAndDevueltoFalse(usuarioId: Long): Optional<List<PrestamoLibro>>
    fun findByLibroIdAndDevueltoFalse(libroId: Long): Optional<PrestamoLibro>
}