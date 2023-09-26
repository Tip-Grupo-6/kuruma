package com.tip.kuruma.models

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.LocalDate

class CarItemTest{
    @Test
    fun carItem() {
        val carItem = CarItem(
            car_id = 1L,
            isDeleted = false,
            last_change = LocalDate.now().minusMonths(9),
        )
        assertEquals(false, carItem.isDeleted)
        assertEquals(LocalDate.now().minusMonths(9), carItem.last_change)
    }
}