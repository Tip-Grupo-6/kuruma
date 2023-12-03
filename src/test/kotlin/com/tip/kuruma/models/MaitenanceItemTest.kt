package com.tip.kuruma.models

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.LocalDate

class MaitenanceItemTest {
    @Test
    fun `create a simple maitenance item entity`() {
        val maintenanceItem = MaintenanceItem(
            code = "WATER",
            description = "Agua",
            kilometersFrequency = 10000,
            replacementFrequency = 2
        )

        assertEquals("WATER", maintenanceItem.code)
        assertEquals("Agua", maintenanceItem.description)
        assertEquals(10000, maintenanceItem.kilometersFrequency)
        assertEquals(2, maintenanceItem.replacementFrequency)
        assertEquals(LocalDate.now(), maintenanceItem.created_at)
        assertEquals(LocalDate.now(), maintenanceItem.updated_at)
    }
}