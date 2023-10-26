package com.tip.kuruma.dto.car_data

import com.fasterxml.jackson.annotation.JsonProperty

data class CarDetailDTO(
    @JsonProperty("referencePrice0km") val referencePrice0km: Double?,
    @JsonProperty("used0KmPrice") val used0KmPrice: Double?,
    @JsonProperty("statedAmount") val statedAmount: Double?,
    @JsonProperty("fullCarDescripcion") val fullCarDescripcion: String?,
    @JsonProperty("makeDescription") val makeDescription: String?,
    @JsonProperty("modelDescription") val modelDescription: String?,
    @JsonProperty("year") val year: Int?,
    @JsonProperty("infoAutoCode") val infoAutoCode: Int?,
    @JsonProperty("category") val category: String?,
    @JsonProperty("fuelCode") val fuelCode: String?,
    @JsonProperty("isImported") val isImported: Boolean?,
    @JsonProperty("panoramicCrystalCeiling") val panoramicCrystalCeiling: Boolean?,
    @JsonProperty("carBrand") val carBrand: String?
)