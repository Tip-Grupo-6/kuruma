package com.tip.kuruma.services

import com.tip.kuruma.configuration.WebPushNotificationConfig
import com.tip.kuruma.dto.notification.WebPushNotificationDTO
import org.springframework.stereotype.Service
import java.security.Security
import nl.martijndwars.webpush.Notification
import nl.martijndwars.webpush.PushService
import nl.martijndwars.webpush.Subscription
import nl.martijndwars.webpush.Subscription.Keys
import org.bouncycastle.jce.provider.BouncyCastleProvider
import org.slf4j.LoggerFactory

@Service
class WebPushNotificationService(
    private val config: WebPushNotificationConfig
) {

    companion object {
        private val LOGGER = LoggerFactory.getLogger(WebPushNotificationService::class.java)
    }

    private lateinit var pushService: PushService

    init {
        Security.addProvider(BouncyCastleProvider())
        pushService = PushService(config.publicKey, config.privateKey)
    }

    fun sendNotification(pushNotification: WebPushNotificationDTO) {
        LOGGER.info("Send message '${pushNotification.message}' to ${pushNotification.endpoint}")
        val ep = pushNotification.endpoint
        val keys = Keys(pushNotification.key, pushNotification.auth)
        val subscription = Subscription(ep, keys)
        pushService.send(Notification(subscription, pushNotification.message))
    }

}
