package com.tip.kuruma.dto.auth

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class UserToken(
        val userId: Long? = null,
        val email: String? = null,
        val name: String? = null,
        val carId: Long? = null
)
