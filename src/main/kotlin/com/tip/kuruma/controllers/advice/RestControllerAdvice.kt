package com.tip.kuruma.controllers.advice

import com.tip.kuruma.EntityNotFoundException
import com.tip.kuruma.dto.ErrorDTO
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.AuthenticationException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class RestControllerAdvice {

    companion object {
        private val LOGGER = LoggerFactory.getLogger(RestControllerAdvice::class.java)
    }

    @ExceptionHandler(EntityNotFoundException::class)
    fun handleEntityNotFoundException(ex: EntityNotFoundException): ResponseEntity<Any> {
        LOGGER.info("Entity not found exception was throw: ${ex.message}")
        return ResponseEntity.notFound().build()
    }

    @ExceptionHandler(AuthenticationException::class)
    fun handleEntityNotFoundException(ex: AuthenticationException): ResponseEntity<ErrorDTO> {
        LOGGER.info("Error on authentication: ${ex.message}")
        val error = ErrorDTO("INVALID_CREDENTIALS", "Invalid credentials")
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error)
    }

    @ExceptionHandler(RuntimeException::class)
    fun handleUnexpectedException(ex: RuntimeException): ResponseEntity<Any> {
        LOGGER.info("Unexpected exception was throw: ${ex.message}")
        return ResponseEntity.internalServerError().build()
    }
}