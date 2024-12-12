package com.es.jwtSecurityKotlin.repository

import com.es.jwtSecurityKotlin.model.Libro
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface LibroRepository: JpaRepository<Libro, Long> {

}