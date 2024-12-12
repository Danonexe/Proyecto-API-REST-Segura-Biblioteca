package com.es.jwtSecurityKotlin.service

import com.es.jwtSecurityKotlin.model.Usuario
import com.es.jwtSecurityKotlin.repository.UsuarioRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*
import java.util.stream.Collectors

@Service
class UsuarioService : UserDetailsService {

    @Autowired
    private lateinit var usuarioRepository: UsuarioRepository

    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder

    override fun loadUserByUsername(username: String?): UserDetails {
        val usuario: Usuario = usuarioRepository
            .findByUsername(username!!)
            .orElseThrow { UsernameNotFoundException("Usuario no encontrado: $username") }

        return User.builder()
            .username(usuario.username)
            .password(usuario.password)
            .roles(usuario.roles)
            .build()
    }

    fun registerUsuario(usuario: Usuario): Usuario {
        // Comprobamos que el usuario no exista en la base de datos
        val username = usuario.username ?: throw IllegalArgumentException("El username no puede ser nulo.")

        if (usuarioRepository.findByUsername(usuario.username!!).isPresent) {
            throw IllegalArgumentException("El usuario con username ${usuario.username} ya existe.")
        }

        // Hasheamos la contraseña del usuario
        val hashedPassword = passwordEncoder.encode(usuario.password)

        // Creamos un nuevo usuario con todos los campos necesarios
        val newUsuario = Usuario(
            id = null, //generado automáticamente
            username = usuario.username,
            password = hashedPassword,
            nombre = usuario.nombre,
            apellidos = usuario.apellidos,
            roles = usuario.roles //para que no se pueda registrar como ADMIN
        )

        // Guardamos el usuario en la base de datos
        return usuarioRepository.save(newUsuario)
    }
}
