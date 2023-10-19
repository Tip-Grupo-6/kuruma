package com.tip.kuruma.dto.clients.san_cristobal

import com.fasterxml.jackson.annotation.JsonProperty

data class CarBrandSanCristobalDTO(
    @JsonProperty("id")
    val id: Int?,
    @JsonProperty("description")
    val description: String?
)
