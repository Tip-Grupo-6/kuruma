package com.tip.kuruma.repositories

import com.tip.kuruma.models.Notification
import org.springframework.data.jpa.repository.JpaRepository

interface NotificationRepository : JpaRepository<Notification, Long> {
    fun findAllByIsDeletedIsFalse(): List<Notification>

    fun findAllByCarIdAndIsDeletedIsFalse(carId: Long): List<Notification>
}
