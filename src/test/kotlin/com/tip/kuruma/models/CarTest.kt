package com.tip.kuruma.models

import org.junit.jupiter.api.Assertions.*
import java.time.LocalDate

class CarTest {
    @org.junit.jupiter.api.Test
    fun testCar() {
        val car = Car()
        car.brand = "Peugeot"
        car.model = "208"
        car.year = 2023
        car.color = "Black"
        car.image = "peugeot_208.jpg"

        val carItem = CarItem()
        carItem.name = "Oil"
        carItem.last_change = LocalDate.now()
        carItem.next_change_due = LocalDate.now().plusMonths(6)
        carItem.due_status = false
        car.carItems = listOf(carItem)

        // car
        assertEquals("Peugeot", car.brand)
        assertEquals("208", car.model)
        assertEquals(2023, car.year)
        assertEquals("Black", car.color)
        assertEquals("peugeot_208.jpg", car.image)
        assertEquals(false, car.isDeleted)
        assertEquals("Peugeot 208", car.getName())

        // car items
        assertEquals("Oil", car.carItems?.get(0)?.name)
        assertEquals(LocalDate.now(), car.carItems?.get(0)?.last_change)
        assertEquals(LocalDate.now().plusMonths(6), car.carItems?.get(0)?.next_change_due)
        assertEquals(false, car.carItems?.get(0)?.due_status)
    }
}