package com.es.jwtSecurityKotlin.model

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "libros")
data class Libro(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id_libro: Long? = null,

    @Column(nullable = false)
    var titulo: String? = null,

    @Column(nullable = false)
    var autor: String? = null,

    @ManyToOne
    @JoinColumn(name = "id_editorial", nullable = false)
    var editorial: Editorial,

    @Column(nullable = false)
    var fecha_de_publicacion: Date? = null,

    @OneToMany(mappedBy = "libro", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var prestamos: List<PrestamoLibro>? = null
)