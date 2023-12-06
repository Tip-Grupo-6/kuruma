package com.tip.kuruma.builders

import com.tip.kuruma.models.Notification

class NotificationBuilder {

    private var id: Long? = null
    private var userId: Long? = 1L
    private var carId: Long? = 1L
    private var maintenanceItemId: Long? = 1L
    private var frequency: Int? = 7
    private var message: String? = "Message"
    private var isDeleted: Boolean? = false


    fun withId(id: Long?): NotificationBuilder {
        this.id = id
        return this
    }

    fun withUserId(userId: Long?): NotificationBuilder {
        this.userId = userId
        return this
    }

    fun withCarId(carId: Long?): NotificationBuilder {
        this.carId = carId
        return this
    }

    fun withMaintenanceItemId(maintenanceItemId: Long?): NotificationBuilder {
        this.maintenanceItemId = maintenanceItemId
        return this
    }

    fun withFrequency(frequency: Int?): NotificationBuilder {
        this.frequency = frequency
        return this
    }

    fun withMessage(message: String?): NotificationBuilder {
        this.message = message
        return this
    }

    fun build(): Notification {
        return Notification(
            id = id,
            userId = userId,
            carId = carId,
            maintenanceItemId = maintenanceItemId,
            frequency = frequency,
            message = message,
            isDeleted = isDeleted
        )
    }

}