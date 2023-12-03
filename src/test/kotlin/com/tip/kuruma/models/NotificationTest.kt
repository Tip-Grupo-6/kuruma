package com.tip.kuruma.models

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.LocalDate

class NotificationTest {
    @Test
    fun `create a simple notification entity`() {
        val notification = Notification(
            id = 1,
            carId = 1,
            isDeleted = false,
            frequency = 1,
            message = "message",
            notificated_at = LocalDate.now(),

        )

        assertEquals(1, notification.id)
        assertEquals(1, notification.carId)
        assertEquals(false, notification.isDeleted)
        assertEquals(1, notification.frequency)
        assertEquals("message", notification.message)
        assertEquals(LocalDate.now(), notification.notificated_at)
        assertEquals(LocalDate.now(), notification.created_at)
        assertEquals(LocalDate.now(), notification.updated_at)

    }
}