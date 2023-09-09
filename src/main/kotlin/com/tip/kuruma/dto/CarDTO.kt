package com.tip.kuruma.dto

import com.tip.kuruma.models.Car
import com.tip.kuruma.models.helpers.MaintenanceSchedule.ScheduleCarUtils.getCarStatus
import com.tip.kuruma.models.helpers.MaintenanceSchedule.ScheduleCarUtils.getNextOilChangeDue
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
                last_oil_change = car.lastOilChange,
                last_water_check = car.lastWaterCheck,
                last_tire_pressure_check = car.lastTirePressureCheck,
                maintenance_values = MaintenanceStatusDTO(
                    car.getNextOilChangeDue(),
                    car.getNextWaterCheckDue(),
                    car.getNextTirePressureCheckDue(),
                    car.getOilChangeDue(),
                    car.getWaterCheckDue(),
                    car.getTirePressureCheckDue(),
                    car.getCarStatus()
                )
            )
        }

        fun fromCars(cars: List<Car>): List<CarDTO> {
            return cars.map { fromCar(it) }
        }
    }
}
