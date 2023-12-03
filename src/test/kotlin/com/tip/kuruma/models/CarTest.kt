package com.tip.kuruma.models

import org.junit.jupiter.api.Assertions.*
import java.time.LocalDate

class CarTest {
    @org.junit.jupiter.api.Test
    fun `create a simple car entity`() {
        val carItem = CarItem(lastChange = LocalDate.now())
        val user = User(id = 1, name = "Test User")

        val car = Car(
            brand = "Peugeot",
            model = "208",
            year = 2023,
            color = "Black",
            image = "peugeot_208.jpg",
            isDeleted = false,
            kilometers = "1000",
            userId = user.id,
            carItems = listOf(carItem)
        )

        // car
        assertEquals("Peugeot", car.brand)
        assertEquals("208", car.model)
        assertEquals(2023, car.year)
        assertEquals("Black", car.color)
        assertEquals("peugeot_208.jpg", car.image)
        assertEquals(false, car.isDeleted)
        assertEquals("Peugeot 208", car.getName())
        assertEquals("1000", car.kilometers)
        assertEquals(user.id, car.userId)

        // car items
        assertEquals(LocalDate.now(), car.carItems?.get(0)?.lastChange)
    }
}