package com.tip.kuruma.services

import com.tip.kuruma.EntityNotFoundException
import com.tip.kuruma.models.Notification
import com.tip.kuruma.repositories.NotificationRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class NotificationService @Autowired constructor(
    private val notificationRepository: NotificationRepository
) {
    fun getAllNotifications(): List<Notification> = notificationRepository.findAllByIsDeletedIsFalse()

    fun saveNotification(notification: Notification): Notification = notificationRepository.save(notification)

    fun getNotificationById(id: Long): Notification = notificationRepository.findById(id).orElseThrow { EntityNotFoundException("Notification with id $id not found") }

    fun updateNotification(id: Long, notification: Notification): Notification {
        val notificationToUpdate = getNotificationById(id)
        notificationToUpdate.let {
            it.carId = notification.carId
            it.maintenanceItemId = notification.maintenanceItemId
            it.frequency = notification.frequency
            it.message = notification.message
            it.isDeleted = notification.isDeleted
            it.updated_at = LocalDate.now()
            return saveNotification(it)
        }
    }

    fun patchNotification(id: Long, notification: Notification): Notification {
        val notificationToUpdate = getNotificationById(id)
        notificationToUpdate.let {
            it.carId = notification.carId ?: it.carId
            it.maintenanceItemId = notification.maintenanceItemId ?: it.maintenanceItemId
            it.frequency = notification.frequency ?: it.frequency
            it.message = notification.message ?: it.message
            it.isDeleted = notification.isDeleted ?: it.isDeleted
            it.updated_at = LocalDate.now()
            return saveNotification(it)
        }
    }

    fun deleteNotification(id: Long) {
        val notification = getNotificationById(id)
        notification.isDeleted = true
        saveNotification(notification)
    }
}
