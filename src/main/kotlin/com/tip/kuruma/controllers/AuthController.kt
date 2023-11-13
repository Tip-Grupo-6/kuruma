package com.tip.kuruma.controllers

import com.tip.kuruma.dto.UserDTO
import com.tip.kuruma.dto.auth.LoginRequestDTO
import com.tip.kuruma.dto.auth.RegisterUserResponseDTO
import com.tip.kuruma.dto.auth.TokenResponseDTO
import com.tip.kuruma.services.auth.AuthService
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/auth")
@CrossOrigin
class AuthController(
        val authService: AuthService
) {

    companion object {
        private val LOGGER = LoggerFactory.getLogger(AuthController::class.java)
    }

    @PostMapping("/login")
    fun login(@RequestBody loginRequestDTO: LoginRequestDTO): ResponseEntity<TokenResponseDTO> {
        LOGGER.info("Calling to login for user ${loginRequestDTO.username}")
        val token = authService.login(loginRequestDTO)
        return ResponseEntity.ok(token)
    }

    @PostMapping("/register")
    fun register(@RequestBody userDTO: UserDTO): ResponseEntity<RegisterUserResponseDTO> {
        LOGGER.info("Calling to register for user $userDTO")
        val response = authService.register(userDTO.toUser())
        return ResponseEntity.ok(response)
    }
}