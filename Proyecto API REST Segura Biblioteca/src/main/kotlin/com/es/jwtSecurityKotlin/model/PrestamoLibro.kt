package com.es.jwtSecurityKotlin.model

import jakarta.persistence.*
import java.util.*

@Entity
data class PrestamoLibro(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne
    @JoinColumn(name = "libro_id")
    val libro: Libro,

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    val usuario: Usuario,

    var devuelto: Boolean = false,

    @Temporal(TemporalType.DATE)
    val fecha_prestamo: Date,

    @Temporal(TemporalType.DATE)
    val limite_prestamo: Date
)