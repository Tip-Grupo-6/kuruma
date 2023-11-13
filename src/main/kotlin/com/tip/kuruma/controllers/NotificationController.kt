package com.tip.kuruma.controllers

import com.tip.kuruma.dto.NotificationDTO
import com.tip.kuruma.services.NotificationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/notifications")
@CrossOrigin
class NotificationController @Autowired constructor(
    private val notificationService: NotificationService
) {
    @GetMapping
    fun getAllNotifications(): ResponseEntity<List<NotificationDTO>> {
        val notifications = notificationService.getAllNotifications()

        return ResponseEntity.ok(NotificationDTO.fromNotifications(notifications))
    }

    @GetMapping("/car/{carId}")
    fun getAllNotificationsByCarId(@PathVariable carId: Long): ResponseEntity<List<NotificationDTO>> {
        val notifications = notificationService.getAllNotificationsByCarId(carId)

        return ResponseEntity.ok(NotificationDTO.fromNotifications(notifications))
    }

    @PostMapping
    fun createNotification(@RequestBody notificationDTO: NotificationDTO): ResponseEntity<NotificationDTO> {
        val savedNotification = notificationService.saveNotification(notificationDTO.toNotification())
        return ResponseEntity.status(201).body(NotificationDTO.fromNotification(savedNotification))
    }

    @GetMapping("/{id}")
    fun getNotificationById(@PathVariable id: Long): ResponseEntity<NotificationDTO> {
        val notification = notificationService.getNotificationById(id)
        return ResponseEntity.ok(NotificationDTO.fromNotification(notification))
    }

    @PutMapping("/{id}")
    fun updateNotification(@PathVariable id: Long, @RequestBody notificationDTO: NotificationDTO): ResponseEntity<NotificationDTO> {
        val notification = notificationService.updateNotification(id, notificationDTO.toNotification())
        return ResponseEntity.ok(NotificationDTO.fromNotification(notification))
    }

    @PatchMapping("/{id}")
    fun patchNotification(@PathVariable id: Long, @RequestBody partialNotificationDTO: NotificationDTO): ResponseEntity<NotificationDTO> {
        val notification = notificationService.patchNotification(id, partialNotificationDTO.toNotification())
        return ResponseEntity.ok(NotificationDTO.fromNotification(notification))
    }

    @DeleteMapping("/{id}")
    fun deleteNotification(@PathVariable id: Long): ResponseEntity<Unit> {
        notificationService.deleteNotification(id)
        return ResponseEntity.noContent().build()
    }
}