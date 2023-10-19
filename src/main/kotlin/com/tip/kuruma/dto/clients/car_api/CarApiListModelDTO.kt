package com.tip.kuruma.dto.clients.car_api

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class CarApiListModelDTO(
    @JsonProperty("data")
    val data: List<CarModelCarApiDTO>?
)