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
            name = "Oil",
            last_change = LocalDate.now().minusMonths(9),
            next_change_due = LocalDate.now().minusMonths(9),
            due_status = false
        )
        assertEquals(false, carItem.isDeleted)
        assertEquals("Oil", carItem.name)
        assertEquals(LocalDate.now().minusMonths(9), carItem.last_change)
        assertEquals(LocalDate.now().minusMonths(9), carItem.next_change_due)
        assertEquals(false, carItem.due_status)
    }
}