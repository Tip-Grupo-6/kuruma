package com.tip.kuruma.models

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.LocalDate

class CarItemTest{
    val maintenanceItem = MaintenanceItem(id = 1L, code = "code")
    @Test
    fun `create a simple car item entity`() {
        val carItem = CarItem(
            carId = 1L,
            currentKmsSinceLastChange = 0,
            initialCarKilometers = 0,
            maintenanceItem = maintenanceItem,
            lastChange = LocalDate.now().minusMonths(9),
            isDeleted = false

        )
        assertEquals(false, carItem.isDeleted)
        assertEquals(LocalDate.now().minusMonths(9), carItem.lastChange)
        assertEquals(maintenanceItem, carItem.maintenanceItem)
        assertEquals(1L, carItem.carId)
        assertEquals(0, carItem.currentKmsSinceLastChange)
        assertEquals(0, carItem.initialCarKilometers)
    }
}