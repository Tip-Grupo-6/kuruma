package com.tip.kuruma.dto

import com.tip.kuruma.models.Notification
import java.time.LocalDate
import java.time.temporal.ChronoUnit

data class NotificationDTO(
    var car : CarDTO? = null,
    var isDeleted : Boolean? = false,
    var maintenanceMessages: Map<String, Any>? = null
) {
    companion object {
        fun fromNotification(notification: Notification): NotificationDTO {
            val notificationDTO = NotificationDTO(
                car = notification.car?.let { CarDTO.fromCar(it) },
                isDeleted = notification.isDeleted
            )
            notificationDTO.maintenanceMessages = mapOf(
                "OilMessage" to notificationDTO.oilMaintenanceMessage(),
                "WaterMessage" to notificationDTO.waterMaintenanceMessage(),
                "TirePressureMessage" to notificationDTO.tireMaintenanceMessage()
            )
            return notificationDTO
        }

        fun fromNotifications(notifications: List<Notification>): List<NotificationDTO> {
            return notifications.map { fromNotification(it) }
        }
    }

    fun oilMaintenanceMessage(): String {
        var adviceMessage: String = "Everything is up to date";
        val oilChangeDueDate = car?.maintenanceValues?.get("NextOilChangeDue") as LocalDate?
        if (oilChangeDueDate != null) {
            val daysUntilDue = ChronoUnit.DAYS.between(LocalDate.now(), oilChangeDueDate)
            if (daysUntilDue in 0..30) {
                adviceMessage = "Oil change is due within this month"
            }
            else if (daysUntilDue < 0) {
                adviceMessage = "Oil change is overdue"
            }
        }

        return adviceMessage
    }

    fun waterMaintenanceMessage(): String {
        var adviceMessage: String = "Everything is up to date";
        val waterCheckDueDate = car?.maintenanceValues?.get("NextWaterCheckDue") as LocalDate?
        if (waterCheckDueDate != null) {
            val daysUntilDue = ChronoUnit.DAYS.between(LocalDate.now(), waterCheckDueDate)
            if (daysUntilDue in 0..30) {
                adviceMessage = "Water check is due within this month"
            }
            else if (daysUntilDue < 0) {
                adviceMessage = "Water check is overdue"
            }
        }

        return adviceMessage
    }

    fun tireMaintenanceMessage(): String {
        var adviceMessage: String = "Everything is up to date";
        val tirePressureCheckDueDate = car?.maintenanceValues?.get("NextTirePressureCheckDue") as LocalDate?
        if (tirePressureCheckDueDate != null) {
            val daysUntilDue = ChronoUnit.DAYS.between(LocalDate.now(), tirePressureCheckDueDate)
            if (daysUntilDue in 0..30) {
                adviceMessage = "Tire pressure check is due within this month"
            }
            else if (daysUntilDue < 0) {
                adviceMessage = "Tire pressure check is overdue"
            }
        }

        return adviceMessage
    }
}
