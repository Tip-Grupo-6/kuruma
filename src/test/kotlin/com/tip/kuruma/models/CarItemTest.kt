package com.tip.kuruma.models

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.LocalDate

class CarItemTest{
    @Test
    fun carItem() {
        val carItem = CarItem(
            carId = 1L,
            isDeleted = false,
            lastChange = LocalDate.now().minusMonths(9),
        )
        assertEquals(false, carItem.isDeleted)
        assertEquals(LocalDate.now().minusMonths(9), carItem.lastChange)
    }
}