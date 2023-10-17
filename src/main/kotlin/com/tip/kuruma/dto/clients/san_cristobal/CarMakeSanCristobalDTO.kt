package com.tip.kuruma.dto.clients.san_cristobal

import com.fasterxml.jackson.annotation.JsonProperty

data class CarMakeSanCristobalDTO(
        @JsonProperty("brands")
        val brands: List<CarBrandSanCristobalDTO>?
)
