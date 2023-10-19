package com.tip.kuruma.dto.clients.car_api

import com.fasterxml.jackson.annotation.JsonProperty

data class CarBrandCarApiDTO(
        @JsonProperty("id")
        val id: Int,
        @JsonProperty("name")
        val name: String
)
