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
            notificationDTO.maintenance_messages = notificationDTO.generateMaintenanceMessages(CarItemDTO.fromCarItems(
                notification.car?.carItems
            ))
            return notificationDTO
        }

        fun fromNotifications(notifications: List<Notification>): List<NotificationDTO> {
            return notifications.map { fromNotification(it) }
        }
    }

    private fun generateMaintenanceMessages(carItems: List<CarItemDTO>?): Map<String, String> {
        println(carItems)
        val maintenanceMessages = mutableMapOf<String, String>()

        carItems?.forEach { carItem ->
            val maintenanceMessage = maintenanceMessage(carItem)
            maintenanceMessages[carItem.name ?: "Unknown"] = maintenanceMessage
        }

        return maintenanceMessages
    }
    fun maintenanceMessage(carItem: CarItemDTO): String {
        var adviceMessage: String = "Everything is up to date";
        println("ChangeDueDate")
        println( carItem.next_change_due)
        val ChangeDueDate = carItem.next_change_due as LocalDate?
        val daysUntilDue = ChronoUnit.DAYS.between(LocalDate.now(), ChangeDueDate)
        val carItemName = carItem.name
        println("days until due")
        println(daysUntilDue)
        if (daysUntilDue in 0..30) {
            adviceMessage = "Change $carItemName within $daysUntilDue days"
        }
        else if (daysUntilDue < 0) {
            adviceMessage = "$carItemName.name change is overdue"
        }

        return adviceMessage
    }

}
