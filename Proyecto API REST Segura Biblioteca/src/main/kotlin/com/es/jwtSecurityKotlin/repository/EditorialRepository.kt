package com.es.jwtSecurityKotlin.repository

import com.es.jwtSecurityKotlin.model.Editorial
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface EditorialRepository: JpaRepository<Editorial, Long> {
}