package com.tip.kuruma.controllers

import com.tip.kuruma.dto.notification.WebPushNotificationDTO
import com.tip.kuruma.services.WebPushNotificationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/push-notifications")
@CrossOrigin
class NotificationPushController @Autowired constructor(
        private val webPushNotificationService: WebPushNotificationService
) {
    @PostMapping
    fun createNotification(@RequestBody pushNotificationDTO: WebPushNotificationDTO): ResponseEntity<Unit> {
        webPushNotificationService.sendNotification(pushNotificationDTO)
        return ResponseEntity.noContent().build()
    }
}