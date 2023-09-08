package com.tip.kuruma.dto

import com.tip.kuruma.models.Car
import com.tip.kuruma.models.Notification
import com.tip.kuruma.models.helpers.MaintenanceSchedule.ScheduleCarUtils.getCarStatus
import com.tip.kuruma.models.helpers.MaintenanceSchedule.ScheduleCarUtils.getNextTirePressureCheckDue
import com.tip.kuruma.models.helpers.MaintenanceSchedule.ScheduleCarUtils.getNextWaterCheckDue
import com.tip.kuruma.models.helpers.MaintenanceSchedule.ScheduleCarUtils.getOilChangeDue
import com.tip.kuruma.models.helpers.MaintenanceSchedule.ScheduleCarUtils.getTirePressureCheckDue
import com.tip.kuruma.models.helpers.MaintenanceSchedule.ScheduleCarUtils.getWaterCheckDue
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import java.time.temporal.Temporal

data class NotificationDTO(
var message: String? = null,
var car : CarDTO? = null,
var isDeleted : Boolean? = false
) {
    companion object {
        fun fromNotification(notification: Notification): NotificationDTO {
            val notificationDTO = NotificationDTO(
                car = notification.car?.let { CarDTO.fromCar(it) },
                isDeleted = notification.isDeleted
            )
            notificationDTO.message = notificationDTO.maintenanceMessage()
            return notificationDTO
        }

        fun fromNotifications(notifications: List<Notification>): List<NotificationDTO> {
            return notifications.map { fromNotification(it) }
        }
    }

    fun maintenanceMessage(): String {
        var adviceMessage: String = "Everything is up to date";
        val oilChangeDueDate = car?.maintenanceValues?.get("NextOilChangeDue") as LocalDate?
        if (oilChangeDueDate != null) {
            val daysUntilDue = ChronoUnit.DAYS.between(LocalDate.now(), oilChangeDueDate)
            println("Oil change due date: ")
            println(daysUntilDue)
            if (daysUntilDue in 0..30) {
                adviceMessage = "Oil change is due within this month"
            }
            else if (daysUntilDue < 0) {
                adviceMessage = "Oil change is overdue"
            }
        }
        val waterCheckDueDate = car?.maintenanceValues?.get("NextWaterCheckDue") as LocalDate?
        if (waterCheckDueDate != null) {
            val daysUntilDue = ChronoUnit.DAYS.between(LocalDate.now(), waterCheckDueDate)
            println("Water check due date: ")
            println(daysUntilDue)
            if (daysUntilDue in 0..30) {
                adviceMessage = "Water check is due within this month"
            }
            else if (daysUntilDue < 0) {
                adviceMessage = "Water check is overdue"
            }
        }
        val tirePressureCheckDueDate = car?.maintenanceValues?.get("NextTirePressureCheckDue") as LocalDate?
        if (tirePressureCheckDueDate != null) {
            val daysUntilDue = ChronoUnit.DAYS.between(LocalDate.now(), tirePressureCheckDueDate)
            println("Tire pressure check due date: ")
            println(daysUntilDue)
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
