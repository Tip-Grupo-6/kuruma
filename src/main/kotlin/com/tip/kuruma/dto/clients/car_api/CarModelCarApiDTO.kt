package com.tip.kuruma.dto.clients.car_api

import com.fasterxml.jackson.annotation.JsonProperty

data class CarModelCarApiDTO(
        @JsonProperty("id")
        val id: Int?,
        @JsonProperty("make_id")
        val makeId: Int?,
        @JsonProperty("name")
        val name: String?
)
