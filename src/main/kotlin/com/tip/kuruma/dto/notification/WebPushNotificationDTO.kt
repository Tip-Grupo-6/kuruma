package com.tip.kuruma.dto.notification

data class WebPushNotificationDTO(
        val endpoint: String? = null,
        val key: String? = null,
        val auth: String? = null,
        val message: String? = null
)