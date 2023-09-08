package com.tip.kuruma.models


import com.tip.kuruma.models.helpers.MaintenanceSchedule.ScheduleCarUtils.getNextOilChangeDue
import com.tip.kuruma.models.helpers.MaintenanceSchedule.ScheduleCarUtils.getNextTirePressureCheckDue
import com.tip.kuruma.models.helpers.MaintenanceSchedule.ScheduleCarUtils.getNextWaterCheckDue

import org.junit.jupiter.api.Assertions.*

class CarTest {
    @org.junit.jupiter.api.Test
    fun testCar() {
        val car = Car()
        car.brand = "Peugeot"
        car.model = "208"
        car.years = 2023
        car.color = "Black"
        car.image = "peugeot_208.jpg"

        assertEquals("Peugeot", car.brand)
        assertEquals("208", car.model)
        assertEquals(2023, car.years)
        assertEquals("Black", car.color)
        assertEquals("peugeot_208.jpg", car.image)
        assertEquals(false, car.isDeleted)
        assertEquals("Peugeot 208", car.getName())

        // next changes
        assertEquals(car.lastOilChange.plusMonths(6), car.getNextOilChangeDue())
        assertEquals(car.lastWaterCheck.plusMonths(3), car.getNextWaterCheckDue())
        assertEquals(car.lastTirePressureCheck.plusMonths(2), car.getNextTirePressureCheckDue())
    }
}