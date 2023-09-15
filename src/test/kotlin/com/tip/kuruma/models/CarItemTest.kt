package com.tip.kuruma.models

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.LocalDate

class CarItemTest{
    @Test
    fun carItem() {
        val carItem = CarItem(
            car = Car(
                brand = "Toyota",
                model = "Corolla",
                years = 2015,
                color = "Red",
                image = "toyota_corolla.jpg"
            ),
            isDeleted = false,
            name = "Oil",
            last_change = LocalDate.now().minusMonths(9),
            next_change_due = LocalDate.now().minusMonths(9),
            due_status = false
        )
        assertEquals("Toyota", carItem.car?.brand)
        assertEquals(false, carItem.isDeleted)
        assertEquals("Oil", carItem.name)
        assertEquals(LocalDate.now().minusMonths(9), carItem.last_change)
        assertEquals(LocalDate.now().minusMonths(9), carItem.next_change_due)
        assertEquals(false, carItem.due_status)
    }
}