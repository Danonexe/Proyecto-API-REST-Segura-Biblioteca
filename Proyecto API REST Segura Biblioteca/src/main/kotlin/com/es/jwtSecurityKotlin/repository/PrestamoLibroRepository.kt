package com.es.jwtSecurityKotlin.repository

import com.es.jwtSecurityKotlin.model.PrestamoLibro
import org.springframework.data.jpa.repository.JpaRepository

interface PrestamoLibroRepository: JpaRepository<PrestamoLibro, Long> {
}