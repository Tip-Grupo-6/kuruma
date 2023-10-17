package com.tip.kuruma.dto.clients.san_cristobal

import com.fasterxml.jackson.annotation.JsonProperty

data class CarModelSanCristobalDTO(
        @JsonProperty("models")
        val models: List<CarBrandSanCristobalDTO>?
)
