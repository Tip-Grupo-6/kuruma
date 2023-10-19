package com.tip.kuruma.dto

import com.tip.kuruma.models.Car

data class CarDTO(
    var id: Long? = null,
    var brand: String? = null,
    var model: String? = null,
    var year: Int? = null,
    var color: String? = null,
    var kilometers: String? = null,
    var image: String? = null,
    var is_deleted: Boolean? = false,
    val maintenance_values: List<CarItemDTO>? = null,

) {
    companion object {
        fun fromCar(car: Car): CarDTO {
            val carDTO = CarDTO(
                id = car.id,
                brand = car.brand,
                model = car.model,
                year = car.year,
                color = car.color,
                image = car.image,
                kilometers = car.kilometers,
                is_deleted = car.isDeleted,
                maintenance_values = car.carItems?.let { CarItemDTO.fromCarItems(it) }
            )


            return carDTO
        }

        fun fromCars(cars: List<Car>): List<CarDTO> {
            return cars.map { fromCar(it) }
        }
    }

    fun toCar(): Car {
        return Car(
            id = this.id,
            brand = this.brand,
            model = this.model,
            year = this.year,
            color = this.color,
            image = this.image,
            kilometers = this.kilometers,
            carItems = this.maintenance_values?.map {
                it.toCarItem()
            }
        )
    }
}
