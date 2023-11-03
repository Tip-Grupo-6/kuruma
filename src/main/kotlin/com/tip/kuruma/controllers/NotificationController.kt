package com.tip.kuruma.controllers

import com.tip.kuruma.dto.NotificationDTO
import com.tip.kuruma.models.Notification
import com.tip.kuruma.services.CarItemService
import com.tip.kuruma.services.CarService
import com.tip.kuruma.services.NotificationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/notifications")
class NotificationController @Autowired constructor(
    private val notificationService: NotificationService,
    private val carItemService: CarItemService
) {
    @GetMapping
    fun getAllNotifications(): ResponseEntity<List<NotificationDTO>> {
        val notifications = notificationService.getAllNotifications()

        return ResponseEntity.ok(NotificationDTO.fromNotifications(notifications, carItemService))
    }

    @PostMapping
    fun createNotification(@RequestBody notificationDTO: NotificationDTO): ResponseEntity<NotificationDTO> {
        val savedNotification = notificationService.saveNotification(notificationDTO.toNotification())
        return ResponseEntity.status(201).body(NotificationDTO.fromNotification(savedNotification, carItemService))
    }

    @GetMapping("/{id}")
    fun getNotificationById(@PathVariable id: Long): ResponseEntity<NotificationDTO> {
        val notification = notificationService.getNotificationById(id)
        return if (notification != null) {
            ResponseEntity.ok(NotificationDTO.fromNotification(notification, carItemService))
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @PutMapping("/{id}")
    fun updateNotification(@PathVariable id: Long, @RequestBody notificationDTO: NotificationDTO): ResponseEntity<NotificationDTO> {
        val notification = notificationService.updateNotification(id, notificationDTO.toNotification())
        return ResponseEntity.ok(NotificationDTO.fromNotification(notification, carItemService))
    }

    @PatchMapping("/{id}")
    fun patchNotification(@PathVariable id: Long, @RequestBody partialNotificationDTO: NotificationDTO): ResponseEntity<NotificationDTO> {
        val notification = notificationService.patchNotification(id, partialNotificationDTO.toNotification())
        return ResponseEntity.ok(NotificationDTO.fromNotification(notification, carItemService))
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