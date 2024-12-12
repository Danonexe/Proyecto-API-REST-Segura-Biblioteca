package com.es.jwtSecurityKotlin.repository

import com.es.jwtSecurityKotlin.model.Libro
import org.springframework.data.jpa.repository.JpaRepository

interface LibroRepository: JpaRepository<Libro, Long> {

}