package com.tip.kuruma.services

import com.tip.kuruma.models.Notification
import com.tip.kuruma.repositories.NotificationRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class NotificationService @Autowired constructor(
    private val notificationRepository: NotificationRepository
) {
    fun getAllNotifications(): List<Notification> = notificationRepository.findAll()

    fun saveNotification(notification: Notification): Notification = notificationRepository.save(notification)

    fun getNotificationById(id: Long): Notification? = notificationRepository.findById(id).orElse(null)

    fun deleteNotification(id: Long) = notificationRepository.deleteById(id)

    fun updateNotification(id: Long, notification: Notification): Notification {
        val notificationToUpdate = notificationRepository.findById(id).orElse(null)
        notificationToUpdate?.oilMessage = notification.oilMessage
        notificationToUpdate?.waterMessage = notification.waterMessage
        notificationToUpdate?.tirePressureMessage = notification.tirePressureMessage
        return notificationRepository.save(notificationToUpdate!!)
    }
}
