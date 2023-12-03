package com.tip.kuruma.models

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.LocalDate

class UserItemTest{
    val maintenanceItem = MaintenanceItem(
        code = "WATER",
        description = "Agua",
        kilometersFrequency = 10000,
        replacementFrequency = 2
    )
    val user = User(id = 1, name = "Juan")
    val userItem = UserItem(
        userId = user.id,
        maintenanceItem = maintenanceItem,
        lastChange = LocalDate.now()
    )

    @Test
    fun `create a simple user item entity`() {
        assertEquals(user.id, userItem.userId)
        assertEquals(maintenanceItem, userItem.maintenanceItem)
        assertEquals(false, userItem.isDeleted)
        assertEquals(LocalDate.now(), userItem.lastChange)
        assertEquals(LocalDate.now(), userItem.created_at)
        assertEquals(LocalDate.now(), userItem.updated_at)
    }
}