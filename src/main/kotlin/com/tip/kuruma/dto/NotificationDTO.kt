package com.tip.kuruma.dto

import com.tip.kuruma.models.Notification
import java.time.LocalDate

data class NotificationDTO(
    var id: Long? = null,
    var user_id: Long? = null,
    var car_id : Long? = null,
    var maintenance_item_id: Long? = null,
    var frequency: Int? = null,
    var message: String? = null,
    var is_deleted : Boolean? = false,
    var notificated_at: LocalDate? = null,
    var created_at: LocalDate? = LocalDate.now(),
    var updated_at: LocalDate? = LocalDate.now()

) {
    companion object {
        fun fromNotification(notification: Notification): NotificationDTO {
            return NotificationDTO(
                id = notification.id,
                user_id= notification.userId,
                car_id = notification.carId,
                maintenance_item_id = notification.maintenanceItemId,
                frequency = notification.frequency,
                message = notification.message,
                is_deleted = notification.isDeleted,
                notificated_at = notification.notificated_at,
                created_at = notification.created_at,
                updated_at = notification.updated_at
            )
        }

        fun fromNotifications(notifications: List<Notification>): List<NotificationDTO> {
            return notifications.map { fromNotification(it) }
        }

    }

    fun toNotification(): Notification {
        return Notification(
            userId = this.user_id,
            carId = this.car_id,
            maintenanceItemId = this.maintenance_item_id,
            frequency = this.frequency,
            message = this.message,
            isDeleted = this.is_deleted,
            notificated_at = this.notificated_at,
            created_at = this.created_at,
            updated_at = this.updated_at
        )
    }

}
