package com.es.jwtSecurityKotlin.repository

import com.es.jwtSecurityKotlin.model.Editorial
import org.springframework.data.jpa.repository.JpaRepository

interface EditorialRepository: JpaRepository<Editorial, Long> {
}