package com.tip.kuruma.builders

import com.tip.kuruma.models.CarItem
import com.tip.kuruma.models.MaintenanceItem
import java.time.LocalDate

class CarItemBuilder {
    private var id: Long = 1L
    private var carId: Long = 1L
    private var maintenanceItem: MaintenanceItem? = MaintenanceItemBuilder().build()
    private var lastChange =  LocalDate.now()
    private var isDeleted = false
    private var initialCarKilometers = 500
    private var currentKmsSinceLastChange = 4000

    fun withMaintenanceItem(maintenanceItem: MaintenanceItem): CarItemBuilder {
        this.maintenanceItem = maintenanceItem
        return this
    }

    fun withLastChange(lastChange: LocalDate): CarItemBuilder {
        this.lastChange = lastChange
        return this
    }

    fun withIsDeleted(isDeleted: Boolean): CarItemBuilder {
        this.isDeleted = isDeleted
        return this
    }

    fun withInitialCarKilometers(initialCarKilometers: Int): CarItemBuilder {
        this.initialCarKilometers = initialCarKilometers
        return this
    }

    fun withCurrentKmsSinceLastChange(currentKmsSinceLastChange: Int): CarItemBuilder {
        this.currentKmsSinceLastChange = currentKmsSinceLastChange
        return this
    }

    fun withCarId(carId: Long): CarItemBuilder {
        this.carId = carId
        return this
    }

    fun withId(id: Long): CarItemBuilder {
        this.id = id
        return this
    }

    fun build(): CarItem {
        return CarItem(
            id = id,
            carId = carId,
            maintenanceItem = maintenanceItem,
            isDeleted = isDeleted,
            lastChange = lastChange,
            initialCarKilometers = initialCarKilometers,
            currentKmsSinceLastChange = currentKmsSinceLastChange

        )
    }


}