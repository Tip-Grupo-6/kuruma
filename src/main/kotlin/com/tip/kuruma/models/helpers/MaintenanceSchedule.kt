package com.tip.kuruma.models.helpers

import com.tip.kuruma.models.Car

import java.time.LocalDate

class MaintenanceSchedule {
    companion object ScheduleCarUtils {
        fun Car.getNextOilChangeDue(): LocalDate {
            return lastOilChange.plusMonths(6) // Oil change every 6 months
        }

        fun Car.getNextWaterCheckDue(): LocalDate {
            return lastWaterCheck.plusMonths(3) // Water level check every 3 months
        }

        fun Car.getNextTirePressureCheckDue(): LocalDate {
            return lastTirePressureCheck.plusMonths(2) // Tire pressure check every 2 months
        }

        fun Car.getOilChangeDue(): Boolean {
            return LocalDate.now().isAfter(getNextOilChangeDue())
        }

        fun Car.getWaterCheckDue(): Boolean {
            return LocalDate.now().isAfter(getNextWaterCheckDue())
        }

        fun Car.getTirePressureCheckDue(): Boolean {
            return LocalDate.now().isAfter(getNextTirePressureCheckDue())
        }

        fun Car.getCarStatus(): String {
            return when {
                getOilChangeDue() && getWaterCheckDue() && getTirePressureCheckDue() -> "red"
                getOilChangeDue() || getWaterCheckDue() || getTirePressureCheckDue() -> "yellow"
                else -> "green"
            }
        }
    }
}
