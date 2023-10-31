package com.tip.kuruma.services

import com.tip.kuruma.EntityNotFoundException
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

    fun getNotificationById(id: Long): Notification? = notificationRepository.findById(id).orElseThrow { EntityNotFoundException("Notification with id $id not found") }

    fun deleteNotification(id: Long) = notificationRepository.deleteById(id)
}
