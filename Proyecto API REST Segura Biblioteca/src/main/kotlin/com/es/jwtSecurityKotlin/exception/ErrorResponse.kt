package com.es.jwtSecurityKotlin.exception

data class ErrorResponse(
    val status: Int,
    val message: String,
)