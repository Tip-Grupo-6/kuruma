package com.tip.kuruma.builders

import com.tip.kuruma.models.CarItem
import com.tip.kuruma.models.MaintenanceItem
import java.time.LocalDate

class CarItemBuilder {
    private var maintenanceItem: MaintenanceItem? = MaintenanceItemBuilder().build()
    private var lastChange =  LocalDate.now()
    private var isDeleted = false

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

    fun build(): CarItem {
        return CarItem(
            maintenanceItem = maintenanceItem,
            isDeleted = isDeleted,
            last_change = lastChange

        )
    }


}