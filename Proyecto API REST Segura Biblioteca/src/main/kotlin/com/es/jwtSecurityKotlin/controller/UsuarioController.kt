package com.es.jwtSecurityKotlin.controller

import com.es.jwtSecurityKotlin.model.Usuario
import com.es.jwtSecurityKotlin.service.TokenService
import com.es.jwtSecurityKotlin.service.UsuarioService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/usuarios")
class UsuarioController {

    @Autowired
    private lateinit var tokenService: TokenService

    @Autowired
    private lateinit var usuarioService: UsuarioService

    @Autowired
    private lateinit var authenticationManager: AuthenticationManager

    // Register
    @PostMapping("/register")
    fun register(
        @RequestBody newUsuario: Usuario
    ): ResponseEntity<Usuario> {
        val usuarioRegistrado = usuarioService.registerUsuario(newUsuario)
        return ResponseEntity(usuarioRegistrado, HttpStatus.CREATED)
    }

    // Login
    @PostMapping("/login")
    fun login(@RequestBody usuario: Usuario): ResponseEntity<Any> {
        val authentication: Authentication
        try {
            authentication = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(usuario.username, usuario.password)
            )
        } catch (e: AuthenticationException) {
            return ResponseEntity(mapOf("mensaje" to "Credenciales incorrectas"), HttpStatus.UNAUTHORIZED)
        }

        val token = tokenService.generarToken(authentication)
        return ResponseEntity(mapOf("mensaje" to "Login exitoso", "token" to token), HttpStatus.OK)
    }

    // Obtener todos los usuarios
    @GetMapping
    fun getAllUsuarios(): ResponseEntity<List<Usuario>> {
        val usuarios = usuarioService.getAllUsuarios()
        return ResponseEntity(usuarios, HttpStatus.OK)
    }

    // Obtener un usuario por ID
    @GetMapping("/{id}")
    fun getUsuarioPorId(@PathVariable id: Long): ResponseEntity<Usuario> {
        val usuario = usuarioService.getUsuarioPorId(id)
        return ResponseEntity(usuario, HttpStatus.OK)
    }

    // Editar un usuario
    @PutMapping("/{id}")
    fun editarUsuario(@PathVariable id: Long, @RequestBody usuarioActualizado: Usuario): ResponseEntity<Usuario> {
        val usuarioEditado = usuarioService.editUsuario(id, usuarioActualizado)
        return ResponseEntity(usuarioEditado, HttpStatus.OK)
    }

    // Borrar un usuario por ID
    @DeleteMapping("/{id}")
    fun borrarUsuarioPorId(@PathVariable id: Long): ResponseEntity<Void> {
        usuarioService.deleteUsuarioPorId(id)
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }
}
