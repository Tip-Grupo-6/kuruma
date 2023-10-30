package com.tip.kuruma.controllers

import com.tip.kuruma.dto.NotificationDTO
import com.tip.kuruma.models.Notification
import com.tip.kuruma.services.CarService
import com.tip.kuruma.services.NotificationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/notifications")
class NotificationController @Autowired constructor(
    private val notificationService: NotificationService,
    private val carService: CarService // Add CarService as a dependency
) {
    @GetMapping
    fun getAllNotifications(): ResponseEntity<List<NotificationDTO>> {
        val notifications = notificationService.getAllNotifications()

        // Pass the carService as an argument to fromNotifications
        return ResponseEntity.ok(NotificationDTO.fromNotifications(notifications, carService))
    }

    @PostMapping
    fun createNotification(@RequestBody notificationDTO: NotificationDTO): ResponseEntity<NotificationDTO> {
        // Pass the carService as an argument to fromNotification
        val savedNotification = notificationService.saveNotification(notificationDTO.toNotification())
        return ResponseEntity.status(201).body(NotificationDTO.fromNotification(savedNotification, carService))
    }

    @GetMapping("/{id}")
    fun getNotificationById(@PathVariable id: Long): ResponseEntity<NotificationDTO> {
        val notification = notificationService.getNotificationById(id)
        return if (notification != null) {
            // Pass the carService as an argument to fromNotification
            ResponseEntity.ok(NotificationDTO.fromNotification(notification, carService))
        } else {
            ResponseEntity.notFound().build()
        }
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