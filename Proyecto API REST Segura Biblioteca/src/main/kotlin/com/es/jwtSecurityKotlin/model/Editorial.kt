package com.es.jwtSecurityKotlin.model

import jakarta.persistence.*
import java.util.*


@Entity
@Table(name = "editoriales")
data class Editorial(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id_editorial: Long? = null,

    @Column(nullable = false)
    var nombre: String? = null,

    @Column(nullable = false)
    var apellidos: String? = null,

    @Column(nullable = false)
    var fecha_de_nacimiento: Date? = null
)