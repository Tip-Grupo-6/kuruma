package com.tip.kuruma.dto

import com.tip.kuruma.models.Notification
import com.tip.kuruma.services.CarItemService
import java.time.LocalDate
import java.time.temporal.ChronoUnit

data class NotificationDTO(
    var id: Long? = null,
    var car_id : Long? = null,
    var car_item_id: Long? = null,
    var frequency: Int? = null,
    var message: String? = null,
    var is_deleted : Boolean? = false,
    var created_at: LocalDate? = LocalDate.now(),
    var updated_at: LocalDate? = LocalDate.now()

) {
    companion object {
        fun fromNotification(notification: Notification): NotificationDTO {
            return NotificationDTO(
                id = notification.id,
                car_id = notification.carId,
                car_item_id = notification.carItemId,
                frequency = notification.frequency,
                message = notification.message,
                is_deleted = notification.isDeleted,
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
            carId = this.car_id,
            carItemId = this.car_item_id,
            frequency = this.frequency,
            message = this.message,
            isDeleted = this.is_deleted,
            created_at = this.created_at,
            updated_at = this.updated_at
        )
    }

    fun maintenanceMessage(carItem: CarItemDTO): String {
        val carItemName = carItem.name
        val changeDueDate = carItem.next_change_due
        val currentDate = LocalDate.now()
        val daysUntilDue = ChronoUnit.DAYS.between(currentDate, changeDueDate)

        return when {
            daysUntilDue <= 0 -> "Tu cambio de $carItemName está atrasado. Cambiá tu $carItemName lo antes posible."
            daysUntilDue <= 7 -> "Esta semana es la fecha de vencimiento del cambio de $carItemName."
            daysUntilDue <= 15 -> "Faltan 15 días para la fecha de vencimiento del cambio de $carItemName."
            daysUntilDue <= 30 -> "Este mes es la fecha de vencimiento del cambio de $carItemName."
            else -> "Tu $carItemName está al día."
        }
    }

}
