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
    var status_color: String? = null

) {
    companion object {
        fun fromCar(car: Car): CarDTO {
            val carDTO = CarDTO(
                id = car.id,
                brand = car.brand,
                model = car.model,
                years = car.years,
                color = car.color,
                image = car.image,
                is_deleted = car.isDeleted,
                maintenance_values = car.carItems?.let { CarItemDTO.fromCarItems(it) }
            )
            carDTO.status_color = carDTO.maintenance_values?.let { carDTO.getCarStatusColor(it) }


            return carDTO
        }

        fun fromCars(cars: List<Car>): List<CarDTO> {
            return cars.map { fromCar(it) }
        }
    }

    fun getCarStatusColor(carItems: List<CarItemDTO>): String {
        when {
            // all carItem due status are true
            carItems?.all { it.due_status == true } ?: false -> return "red"
            // at least one carItem due status is true
            carItems?.any { it.due_status == true } ?: false -> return "yellow"
            // all carItem due status are false
            else -> return "green"
        }
    }
}
