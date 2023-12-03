package com.tip.kuruma.models

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.LocalDate

class SuscriptionTest{
    val suscription = Suscription(
        userId = 1,
        auth = "auth",
        endpoint = "ep",
        key = "key",
        isDeleted = false,
    )

    @Test
    fun `create a simple suscription entity`() {
        assertEquals(1, suscription.userId)
        assertEquals("auth", suscription.auth)
        assertEquals("ep", suscription.endpoint)
        assertEquals("key", suscription.key)
        assertEquals(false, suscription.isDeleted)
        assertEquals(LocalDate.now(), suscription.created_at)
        assertEquals(LocalDate.now(), suscription.updated_at)
    }
}