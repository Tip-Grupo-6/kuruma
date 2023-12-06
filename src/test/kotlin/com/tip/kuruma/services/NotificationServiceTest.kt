package com.tip.kuruma.services

import com.tip.kuruma.EntityNotFoundException
import com.tip.kuruma.builders.CarBuilder
import com.tip.kuruma.builders.NotificationBuilder
import com.tip.kuruma.models.Notification
import com.tip.kuruma.repositories.NotificationRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Rollback
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
class NotificationServiceTest  {
    @Autowired
    private lateinit var notificationRepository: NotificationRepository

    @Autowired
    private lateinit var notificationService:NotificationService

    @Autowired
    private lateinit var carService: CarService

    private fun builtNotification(): Notification {
        val car = carService.saveCar(CarBuilder().build())
        return NotificationBuilder().withId(null).withCarId(car.id!!).build()
    }

    @BeforeEach
    fun clearDatabase() {
        notificationRepository.deleteAll()
    }

    @Test
    @Transactional
    @Rollback
    fun `fetching all notifications when there is none of the available `() {
        val foundNotifications = notificationService.getAllNotifications()
        assertEquals(0, foundNotifications.size)
    }

    @Test
    @Transactional
    @Rollback
    fun `fetching all notifications when there is one of the available `() {
        val notification = builtNotification()
        val savedNotification = notificationService.saveNotification(notification)
        val foundNotifications = notificationService.getAllNotifications()
        assertEquals(1, foundNotifications.size)
        assertEquals(savedNotification.id, foundNotifications[0].id)
    }

    @Test
    @Transactional
    @Rollback
    fun `fetching a notification by id when it exists`() {
        val notification = builtNotification()
        val savedNotification = notificationService.saveNotification(notification)
        val foundNotification = notificationService.getNotificationById(savedNotification.id!!)
        assertEquals(savedNotification.id, foundNotification.id)
    }

    @Test
    @Transactional
    @Rollback
    fun `fetching a notification by id when it does not exist`() {
        val notification = builtNotification()
        val savedNotification = notificationService.saveNotification(notification)
        assertThrows(EntityNotFoundException::class.java) {
            notificationService.getNotificationById(savedNotification.id!! + 1)
        }
    }

    @Test
    @Transactional
    @Rollback
    fun `saving a notification`() {
        val notification = builtNotification()
        val savedNotification = notificationService.saveNotification(notification)
        // id is not null
        assert(savedNotification.id != null)
        assertEquals(notification.userId, savedNotification.userId)
        assertEquals(notification.carId, savedNotification.carId)
        assertEquals(notification.maintenanceItemId, savedNotification.maintenanceItemId)
        assertEquals(notification.frequency, savedNotification.frequency)
        assertEquals(notification.message, savedNotification.message)
        assertEquals(notification.isDeleted, savedNotification.isDeleted)
    }

    @Test
    @Transactional
    @Rollback
    fun `deleting a notification by id when it exists`() {
        val notification = builtNotification()
        val savedNotification = notificationService.saveNotification(notification)
        notificationService.deleteNotification(savedNotification.id!!)
        val deletedNotification = notificationService.getNotificationById(savedNotification.id!!)
        assertEquals(true, deletedNotification.isDeleted)
    }

    @Test
    @Transactional
    @Rollback
    fun `deleting a notification by id when it does not exist`() {
        val notification = builtNotification()
        val savedNotification = notificationService.saveNotification(notification)
        assertThrows(EntityNotFoundException::class.java) {
            notificationService.deleteNotification(savedNotification.id!! + 1)
        }
    }

    @Test
    @Transactional
    @Rollback
    fun `updating a notification by id when it exists`() {
        val notification = builtNotification()
        val savedNotification = notificationService.saveNotification(notification)
        val updatedNotification = notificationService.updateNotification(savedNotification.id!!, notification.copy(message = "New Message"))
        assertEquals("New Message", updatedNotification.message)
    }

    @Test
    @Transactional
    @Rollback
    fun `updating a notification by id when it does not exist`() {
        val notification = builtNotification()
        val savedNotification = notificationService.saveNotification(notification)
        assertThrows(EntityNotFoundException::class.java) {
            notificationService.updateNotification(savedNotification.id!! + 1, notification.copy(message = "New Message"))
        }
    }
}
