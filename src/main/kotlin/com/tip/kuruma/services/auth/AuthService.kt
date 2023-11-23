package com.tip.kuruma.services.auth

import com.tip.kuruma.dto.auth.LoginRequestDTO
import com.tip.kuruma.dto.auth.RegisterUserResponseDTO
import com.tip.kuruma.dto.auth.TokenResponseDTO
import com.tip.kuruma.enums.Role
import com.tip.kuruma.models.User
import com.tip.kuruma.repositories.CarRepository
import com.tip.kuruma.repositories.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service


@Service
class AuthService(
        val userRepository: UserRepository,
        val carRepository: CarRepository,
        val jwtService: JwtService,
        val passwordEncoder: PasswordEncoder,
        val authenticationManager: AuthenticationManager
) {

    companion object {
        private val LOGGER = LoggerFactory.getLogger(AuthService::class.java)
    }

    fun login(request: LoginRequestDTO): TokenResponseDTO {
        LOGGER.info("Authenticating user ${request.username}")
        authenticationManager.authenticate(UsernamePasswordAuthenticationToken(request.username, request.password))
        val user = userRepository.findByEmail(request.username).get()
        val carId = carRepository.getCarIdByUser(user.id!!)
        val token = jwtService.getToken(user, carId)
        return TokenResponseDTO(token)
    }

    fun register(request: User): RegisterUserResponseDTO? {
        val user = request.copy(
                role = Role.USER,
                password = passwordEncoder.encode(request.password)
        )
        val save = userRepository.save(user)
        return RegisterUserResponseDTO(save.id!!, save.email!!)
    }

}