package com.tip.kuruma.services

import com.tip.kuruma.EntityNotFoundException
import com.tip.kuruma.builders.SuscriptionBuilder
import com.tip.kuruma.builders.UserBuilder
import com.tip.kuruma.models.Suscription
import com.tip.kuruma.repositories.SuscriptionRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Rollback
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
class SuscriptionServiceTest  {/*
    @Autowired
    private lateinit var suscriptionRepository: SuscriptionRepository

    @Autowired
    private lateinit var suscriptionService: SuscriptionService

    @Autowired
    private lateinit var userService: UserService

    private fun builtSuscription(): Suscription {
        var user = UserBuilder().build()
        user = userService.saveUser(user)
        return SuscriptionBuilder().withId(null).withUserId(user.id!!).build()
    }



    @Test
    @Transactional
    @Rollback
    fun `fetching all suscriptions when there is none of the available `() {
        val foundSuscriptions = suscriptionService.getAllSuscriptions()
        assertEquals(0, foundSuscriptions.size)
    }

    @Test
    @Transactional
    @Rollback
    fun `fetching all suscriptions when there is one of the available `() {
        val suscription = builtSuscription()
        val savedSuscription = suscriptionService.saveSuscription(suscription)
        val foundSuscriptions = suscriptionService.getAllSuscriptions()
        assertEquals(1, foundSuscriptions.size)
        assertEquals(savedSuscription.id, foundSuscriptions[0].id)
    }

    @Test
    @Transactional
    @Rollback
    fun `fetching a suscription by id when it exists`() {
        val suscription = builtSuscription()
        val savedSuscription = suscriptionService.saveSuscription(suscription)
        val foundSuscription = suscriptionService.getSuscriptionById(savedSuscription.id!!)
        assertEquals(savedSuscription.id, foundSuscription.id)
    }

    @Test
    @Transactional
    @Rollback
    fun `fetching a suscription by id when it does not exist`() {
        val suscription = builtSuscription()
        val savedSuscription = suscriptionService.saveSuscription(suscription)
        assertThrows(EntityNotFoundException::class.java) {
            suscriptionService.getSuscriptionById(savedSuscription.id!! + 1)
        }
    }

    @Test
    @Transactional
    @Rollback
    fun `saving a suscription`() {
        val suscription = builtSuscription()
        val savedSuscription = suscriptionService.saveSuscription(suscription)
        // id is not null
        assert(savedSuscription.id != null)
        assertEquals(suscription.userId, savedSuscription.userId)
        assertEquals(suscription.key, savedSuscription.key)
        assertEquals(suscription.auth, savedSuscription.auth)
        assertEquals(suscription.endpoint, savedSuscription.endpoint)
        assertEquals(suscription.isDeleted, savedSuscription.isDeleted)
    }

    @Test
    @Transactional
    @Rollback
    fun `deleting a suscription by id when it exists`() {
        val suscription = builtSuscription()
        val savedSuscription = suscriptionService.saveSuscription(suscription)
        suscriptionService.deleteSuscription(savedSuscription.id!!)
        val deletedSuscription = suscriptionService.getSuscriptionById(savedSuscription.id!!)
        assertEquals(true, deletedSuscription.isDeleted)
    }

    @Test
    @Transactional
    @Rollback
    fun `deleting a suscription by id when it does not exist`() {
        val suscription = builtSuscription()
        val savedSuscription = suscriptionService.saveSuscription(suscription)
        assertThrows(EntityNotFoundException::class.java) {
            suscriptionService.deleteSuscription(savedSuscription.id!! + 1)
        }
    }

    @Test
    @Transactional
    @Rollback
    fun `updating a suscription by id when it exists`() {
        val suscription = builtSuscription()
        val savedSuscription = suscriptionService.saveSuscription(suscription)
        val updatedSuscription = suscriptionService.updateSuscription(savedSuscription.id!!, suscription.copy(key = "New Key"))
        assertEquals("New Key", updatedSuscription.key)
    }

    @Test
    @Transactional
    @Rollback
    fun `updating a suscription by id when it does not exist`() {
        val suscription = builtSuscription()
        val savedSuscription = suscriptionService.saveSuscription(suscription)
        assertThrows(EntityNotFoundException::class.java) {
            suscriptionService.updateSuscription(savedSuscription.id!! + 1, suscription.copy(key = "New Key"))
        }
    }*/
}
