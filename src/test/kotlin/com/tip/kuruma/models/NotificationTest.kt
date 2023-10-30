package com.tip.kuruma.models

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class NotificationTest {
    @Test
    fun testNotification() {
        val notification = Notification(
            id = 1,
            carId = 1,
            isDeleted = false
        )

        assertEquals(1, notification.id)
        assertEquals(1, notification.carId)
        assertEquals(false, notification.isDeleted)

    }
}