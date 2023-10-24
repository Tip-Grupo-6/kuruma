package com.tip.kuruma.dto.car_data

data class CarDetailDTO(
    val referencePrice0km: Double?,
    val used0KmPrice: Double?,
    val statedAmount: Double?,
    val fullCarDescripcion: String?,
    val makeDescription: String?,
    val modelDescription: String?,
    val year: Int?,
    val infoAutoCode: Int?,
    val category: String?,
    val fuelCode: String?,
    val isImported: Boolean?,
    val panoramicCrystalCeiling: Boolean?,
    val carBrand: String?
)