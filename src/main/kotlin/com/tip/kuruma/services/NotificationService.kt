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
    fun getAllNotifications(): List<Notification> = notificationRepository.findAll()

    fun saveNotification(notification: Notification): Notification = notificationRepository.save(notification)

    fun getNotificationById(id: Long): Notification? = notificationRepository.findById(id).orElseThrow { EntityNotFoundException("Notification with id $id not found") }

    fun updateNotification(id: Long, notification: Notification): Notification {
        val notificationToUpdate = getNotificationById(id)
        notificationToUpdate?.let {
            it.carId = notification.carId
            it.carItemId = notification.carItemId
            it.frequency = notification.frequency
            it.message = notification.message
            it.isDeleted = notification.isDeleted
            it.updated_at = LocalDate.now()
            return saveNotification(it)
        }
        throw EntityNotFoundException("Notification with id $id not found")
    }

    fun patchNotification(id: Long, notification: Notification): Notification {
        val notificationToUpdate = getNotificationById(id)
        notificationToUpdate?.let {
            it.carId = notification.carId ?: it.carId
            it.carItemId = notification.carItemId ?: it.carItemId
            it.frequency = notification.frequency ?: it.frequency
            it.message = notification.message ?: it.message
            it.isDeleted = notification.isDeleted ?: it.isDeleted
            it.updated_at = LocalDate.now()
            return saveNotification(it)
        }
        throw EntityNotFoundException("Notification with id $id not found")
    }

    fun deleteNotification(id: Long) = notificationRepository.deleteById(id)
}
