package com.tip.kuruma.dto

import com.tip.kuruma.models.Car

data class CarDTO(
    var id: Long? = null,
    var brand: String? = null,
    var model: String? = null,
    var years: Int? = null,
    var color: String? = null,
    var image: String? = null,
    var is_deleted: Boolean? = false,
    val maintenance_values: List<CarItemDTO>? = null,
    var statusColor: String? = null

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
                maintenance_values = car.carItems?.let { CarItemDTO.fromCarItems(it) },
                statusColor =
                when {
                   // all carItem due status are true
                    car.carItems?.all { it.due_status } == true -> "red"
                    // at least one carItem due status is true
                    car.carItems?.any { it.due_status } == true -> "yellow"
                    // all carItem due status are false
                    else -> "green"
                }
            )
        }

        fun fromCars(cars: List<Car>): List<CarDTO> {
            return cars.map { fromCar(it) }
        }
    }
}
