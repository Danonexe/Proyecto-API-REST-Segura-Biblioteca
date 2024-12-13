package com.es.jwtSecurityKotlin.model

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "prestamo_libros")
data class PrestamoLibro(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id_prestamo: Long? = null,

    @ManyToOne
    @JoinColumn(name = "id_libro", nullable = false)
    var libro: Libro? = null,

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    var usuario: Usuario? = null,

    @Column(nullable = false)
    var devuelto: Boolean = false,

    @Column(nullable = false)
    var fecha_prestamo: Date? = null,

    @Column(nullable = false)
    var limite_prestamo: Date? = null
)