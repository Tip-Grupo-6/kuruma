package com.tip.kuruma.dto

import com.tip.kuruma.models.Car
import java.time.LocalDate

data class CarDTO(
    var id: Long? = null,
    var brand: String? = null,
    var model: String? = null,
    var years: Int? = null,
    var color: String? = null,
    var image: String? = null,
    var is_deleted: Boolean? = false,
    var last_oil_change: LocalDate ?= null,
    var last_water_check: LocalDate ?= null,
    var last_tire_pressure_check: LocalDate ?= null,
    val maintenance_values: MaintenanceStatusDTO? = null

) {
    companion object {
        fun fromCar(car: Car): CarDTO {
            return CarDTO(

                id = car.id,
                brand = car.brand,
                model = car.model,
                years = car.years,
                color = car.color,
                image = car.image,
                is_deleted = car.isDeleted,
                maintenance_values = MaintenanceStatusDTO(
                )
            )
        }

        fun fromCars(cars: List<Car>): List<CarDTO> {
            return cars.map { fromCar(it) }
        }
    }
}
