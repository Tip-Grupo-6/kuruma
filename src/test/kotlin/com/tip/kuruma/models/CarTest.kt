package com.tip.kuruma.models

import Car
import org.junit.jupiter.api.Assertions.*

class CarTest {
    @org.junit.jupiter.api.Test
    fun testCar() {
        val car = Car()
        car.name = "Civic"
        car.brand = "Honda"
        car.model = "EX"
        car.year = 2023
        car.color = "Blue"
        car.image = "civic.jpg"

        assertEquals("Civic", car.name)
        assertEquals("Honda", car.brand)
        assertEquals("EX", car.model)
        assertEquals(2023, car.year)
        assertEquals("Blue", car.color)
        assertEquals("civic.jpg", car.image)
        assertEquals(false, car.isDeleted)
    }
}