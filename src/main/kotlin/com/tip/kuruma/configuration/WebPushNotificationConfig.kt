package com.tip.kuruma.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "push-notifications")
data class WebPushNotificationConfig(
        var publicKey: String = "",
        var privateKey: String = ""
)
