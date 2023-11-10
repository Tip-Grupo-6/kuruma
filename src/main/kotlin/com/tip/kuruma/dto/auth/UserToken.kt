package com.tip.kuruma.dto.auth

data class UserToken(
        val email: String? = null,
        val name: String? = null,
        val carId: String? = null
)
