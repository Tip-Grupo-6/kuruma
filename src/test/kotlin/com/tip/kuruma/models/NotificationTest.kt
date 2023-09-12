package com.tip.kuruma.models

import com.tip.kuruma.dto.NotificationDTO
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.LocalDate

class NotificationTest {
    @Test
    fun testNotification() {
        val notification = Notification(
            car = Car(
                brand = "Toyota",
                model = "Corolla",
                years = 2015,
                color = "Red",
                image = "toyota_corolla.jpg",
                lastOilChange = LocalDate.now().minusMonths(9),
                lastWaterCheck = LocalDate.now().minusMonths(9),
                lastTirePressureCheck = LocalDate.now().minusMonths(9)
            ),
            isDeleted = false,
            oilMessage = "Oil change is due within this month",
            waterMessage = "Water check is due within this month",
            tirePressureMessage = "Tire pressure check is due within this month"
        )
        assertEquals("Toyota", notification.car?.brand)
        assertEquals(false, notification.isDeleted)
        assertEquals("Oil change is due within this month", notification.oilMessage)
        assertEquals("Water check is due within this month", notification.waterMessage)
        assertEquals("Tire pressure check is due within this month", notification.tirePressureMessage)



    }
}