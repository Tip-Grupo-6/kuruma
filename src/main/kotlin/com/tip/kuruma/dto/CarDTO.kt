package com.tip.kuruma.dto

import com.tip.kuruma.models.Car
import com.tip.kuruma.models.helpers.MaintenanceSchedule.ScheduleCarUtils.getCarStatus
import com.tip.kuruma.models.helpers.MaintenanceSchedule.ScheduleCarUtils.getNextTirePressureCheckDue
import com.tip.kuruma.models.helpers.MaintenanceSchedule.ScheduleCarUtils.getNextWaterCheckDue
import com.tip.kuruma.models.helpers.MaintenanceSchedule.ScheduleCarUtils.getOilChangeDue
import com.tip.kuruma.models.helpers.MaintenanceSchedule.ScheduleCarUtils.getTirePressureCheckDue
import com.tip.kuruma.models.helpers.MaintenanceSchedule.ScheduleCarUtils.getWaterCheckDue
import java.time.LocalDate

data class CarDTO(
    var id: Long? = null,
    var brand: String? = null,
    var model: String? = null,
    var years: Int? = null,
    var color: String? = null,
    var image: String? = null,
    var isDeleted: Boolean? = false,
    var lastOilChange: LocalDate ?= null,
    var lastWaterCheck: LocalDate ?= null,
    var lastTirePressureCheck: LocalDate ?= null,
    val maintenanceValues: Map<String, Any>? = null

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
                isDeleted = car.isDeleted,
                lastOilChange = car.lastOilChange,
                lastWaterCheck = car.lastWaterCheck,
                lastTirePressureCheck = car.lastTirePressureCheck,
                maintenanceValues = mapOf(
                    "NextOilChangeDue" to car.getNextWaterCheckDue(),
                    "NextWaterCheckDue" to car.getNextWaterCheckDue(),
                    "NextTirePressureCheckDue" to car.getNextTirePressureCheckDue(),
                    "OilChangeDue" to car.getOilChangeDue(),
                    "WaterCheckDue" to car.getWaterCheckDue(),
                    "TirePressureCheckDue" to car.getTirePressureCheckDue(),
                    "CarStatus" to car.getCarStatus()
                ))
        }

        fun fromCars(cars: List<Car>): List<CarDTO> {
            return cars.map { fromCar(it) }
        }
    }
}
