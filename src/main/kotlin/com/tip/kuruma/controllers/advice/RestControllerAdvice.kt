package com.tip.kuruma.controllers.advice

import com.tip.kuruma.EntityNotFoundException
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
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

    @ExceptionHandler(RuntimeException::class)
    fun handleUnexpectedException(ex: RuntimeException): ResponseEntity<Any> {
        LOGGER.info("Unexpected exception was throw: ${ex.message}")
        return ResponseEntity.internalServerError().build()
    }
}