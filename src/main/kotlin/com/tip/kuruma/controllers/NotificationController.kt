package com.tip.kuruma.controllers

import com.tip.kuruma.dto.NotificationDTO
import com.tip.kuruma.models.Notification
import com.tip.kuruma.services.NotificationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/notifications")
class NotificationController @Autowired constructor(
    private val notificationService: NotificationService
) {
    @GetMapping
    fun getAllNotifications(): ResponseEntity<List<NotificationDTO>> {
        val notifications = notificationService.getAllNotifications()

        return ResponseEntity.ok(NotificationDTO.fromNotifications(notifications))
    }

    @PostMapping
    fun createNotification(@RequestBody notification: Notification): ResponseEntity<NotificationDTO> {
        val savedNotification = notificationService.saveNotification(notification)
        return ResponseEntity.status(201).body(NotificationDTO.fromNotification(savedNotification))
    }

    @GetMapping("/{id}")
    fun getNotificationById(@PathVariable id: Long): ResponseEntity<NotificationDTO> {
        val notification = notificationService.getNotificationById(id)
        return if (notification != null) ResponseEntity.ok(NotificationDTO.fromNotification(notification)) else ResponseEntity.notFound().build()
    }

    @PutMapping("/{id}")
    fun updateNotification(@PathVariable id: Long,@RequestBody notificationUpdate: Notification ): ResponseEntity<NotificationDTO> {
        val notification = notificationService.updateNotification(id, notificationUpdate)
        return ResponseEntity.ok(NotificationDTO.fromNotification(notification))
    }

    @DeleteMapping("/{id}")
    fun deleteNotification(@PathVariable id: Long): ResponseEntity<Unit> {
        val notification = notificationService.getNotificationById(id)
        if (notification != null) {
            notification.isDeleted = true
            notificationService.saveNotification(notification)
            return ResponseEntity.noContent().build()
        }
        return ResponseEntity.notFound().build()
    }
}