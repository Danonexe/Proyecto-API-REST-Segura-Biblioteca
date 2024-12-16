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

    @Override
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
        val username = usuario.username ?: throw IllegalArgumentException("El username no puede ser nulo.")

        if (usuarioRepository.findByUsername(usuario.username!!).isPresent) {
            throw IllegalArgumentException("El usuario con username ${usuario.username} ya existe.")
        }

        val hashedPassword = passwordEncoder.encode(usuario.password)

        val newUsuario = Usuario(
            id = null,
            username = usuario.username,
            password = hashedPassword,
            nombre = usuario.nombre,
            apellidos = usuario.apellidos,
            roles = usuario.roles
        )

        return usuarioRepository.save(newUsuario)
    }

    fun editUsuario(id: Long, usuarioActualizado: Usuario): Usuario {
        val usuarioExistente = usuarioRepository.findById(id).orElseThrow {
            IllegalArgumentException("Usuario con ID $id no encontrado.")
        }

        val hashedPassword = if (usuarioActualizado.password != null) {
            passwordEncoder.encode(usuarioActualizado.password)
        } else {
            usuarioExistente.password
        }

        val usuarioEditado = usuarioExistente.copy(
            username = usuarioActualizado.username ?: usuarioExistente.username,
            password = hashedPassword,
            nombre = usuarioActualizado.nombre ?: usuarioExistente.nombre,
            apellidos = usuarioActualizado.apellidos ?: usuarioExistente.apellidos,
            roles = usuarioActualizado.roles ?: usuarioExistente.roles
        )

        return usuarioRepository.save(usuarioEditado)
    }

    fun getUsuarioPorId(id: Long): Usuario {
        return usuarioRepository.findById(id).orElseThrow {
            IllegalArgumentException("Usuario con ID $id no encontrado.")
        }
    }

    fun getAllUsuarios(): List<Usuario> {
        return usuarioRepository.findAll()
    }

    fun deleteUsuarioPorId(id: Long) {
        if (!usuarioRepository.existsById(id)) {
            throw IllegalArgumentException("Usuario con ID $id no encontrado.")
        }
        usuarioRepository.deleteById(id)
    }
}
