package com.tip.kuruma.dto

import com.tip.kuruma.models.Notification
import com.tip.kuruma.services.CarService
import org.springframework.beans.factory.annotation.Autowired
import java.time.LocalDate
import java.time.temporal.ChronoUnit

data class NotificationDTO(
    var car_id : Long? = null,
    var is_deleted : Boolean? = false,
    var maintenance_messages: Map<String, Any>? = null

) {
    companion object {
        fun fromNotification(notification: Notification, carService: CarService): NotificationDTO {
            val car = carService.getCarById(notification.carId!!)
            val carItems = car.carItems

            val notificationDTO = NotificationDTO(
                car_id = notification.carId,
                is_deleted = notification.isDeleted
            )

            notificationDTO.maintenance_messages = notificationDTO.generateMaintenanceMessages(
                CarItemDTO.fromCarItems(carItems)
            )

            return notificationDTO
        }

        fun fromNotifications(notifications: List<Notification>, carService: CarService): List<NotificationDTO> {
            return notifications.map { fromNotification(it, carService) }
        }

    }

    fun toNotification(): Notification {
        return Notification(
            carId = this.car_id,
            isDeleted = this.is_deleted
        )
    }

    private fun generateMaintenanceMessages(carItems: List<CarItemDTO>?): Map<String, String> {
        val maintenanceMessages = mutableMapOf<String, String>()

        carItems?.forEach { carItem ->
            val maintenanceMessage = maintenanceMessage(carItem)
            maintenanceMessages[carItem.name ?: "Unknown"] = maintenanceMessage
        }

        return maintenanceMessages
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
