package com.tip.kuruma.dto

import com.tip.kuruma.models.Car
import java.time.LocalDate
import java.time.temporal.ChronoUnit

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
    var maintenance_messages: Map<String, Any>? = null

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
                maintenance_values = car.carItems?.let { CarItemDTO.fromCarItems(it) },
            )
            carDTO.maintenance_messages = carDTO.generateMaintenanceMessages(
                CarItemDTO.fromCarItems(car.carItems)
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

    private fun generateMaintenanceMessages(carItems: List<CarItemDTO>?): Map<String, String> {
        val maintenanceMessages = mutableMapOf<String, String>()

        carItems?.forEach { carItem ->
            val maintenanceMessage = maintenanceMessage(carItem)
            maintenanceMessages[carItem.name ?: "Unknown"] = maintenanceMessage
        }

        return maintenanceMessages
    }
    fun maintenanceMessage(carItem: CarItemDTO): String {
        val carItemName = carItem.name
        val changeDueDate = carItem.next_change_due
        val currentDate = LocalDate.now()
        val daysUntilDue = ChronoUnit.DAYS.between(currentDate, changeDueDate)

        return when {
            daysUntilDue <= 0 -> "Tu cambio de $carItemName está atrasado. Cambiá tu $carItemName lo antes posible."
            daysUntilDue <= 7 -> "Esta semana es la fecha de vencimiento del cambio de $carItemName."
            daysUntilDue <= 15 -> "Faltan 15 días para la fecha de vencimiento del cambio de $carItemName."
            daysUntilDue <= 30 -> "Este mes es la fecha de vencimiento del cambio de $carItemName."
            else -> "Tu $carItemName está al día."
        }
    }
}
