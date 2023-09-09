package com.tip.kuruma.dto

import com.tip.kuruma.models.Notification
import java.time.LocalDate
import java.time.temporal.ChronoUnit

data class NotificationDTO(
    var car : CarDTO? = null,
    var is_deleted : Boolean? = false,
    var maintenance_messages: Map<String, Any>? = null
) {
    companion object {
        fun fromNotification(notification: Notification): NotificationDTO {
            val notificationDTO = NotificationDTO(
                car = notification.car?.let { CarDTO.fromCar(it) },
                is_deleted = notification.isDeleted
            )
            notificationDTO.maintenance_messages = mapOf(
                "oil_message" to notificationDTO.oilMaintenanceMessage(),
                "water_message" to notificationDTO.waterMaintenanceMessage(),
                "tire_pressure_message" to notificationDTO.tireMaintenanceMessage()
            )
            return notificationDTO
        }

        fun fromNotifications(notifications: List<Notification>): List<NotificationDTO> {
            return notifications.map { fromNotification(it) }
        }
    }

    fun oilMaintenanceMessage(): String {
        var adviceMessage: String = "Everything is up to date";
        val oilChangeDueDate = car?.maintenance_values?.nextOilChangeDue as LocalDate?
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
        val waterCheckDueDate = car?.maintenance_values?.nextWaterCheckDue as LocalDate?
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
        val tirePressureCheckDueDate = car?.maintenance_values?.nextTirePressureCheckDue as LocalDate?
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
