package com.tip.kuruma.dto.clients.san_cristobal
import com.fasterxml.jackson.annotation.JsonProperty
import com.tip.kuruma.dto.car_data.CarDetailDTO

data class CarModelDetailSanCristobalDTO(
    @JsonProperty("versions")
    val versions: List<CarDetailDTO>?
)
