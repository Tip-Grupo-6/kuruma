package com.tip.kuruma.builders

import com.tip.kuruma.enums.Role
import com.tip.kuruma.models.MaintenanceItem
import com.tip.kuruma.models.User
import com.tip.kuruma.models.UserItem
import java.time.LocalDate

class UserItemBuilder {

    private var id: Long? = null
    private var userId: Long? = 1L
    private var maintenanceItem: MaintenanceItem? = MaintenanceItemBuilder().build()
    private var lastChange: LocalDate = LocalDate.now()
    private var isDeleted: Boolean? = false

    fun withId(id: Long?): UserItemBuilder {
        this.id = id
        return this
    }

    fun withUserId(userId: Long?): UserItemBuilder {
        this.userId = userId
        return this
    }

    fun withMaintenanceItem(maintenanceItem: MaintenanceItem?): UserItemBuilder {
        this.maintenanceItem = maintenanceItem
        return this
    }

    fun withLastChange(lastChange: LocalDate): UserItemBuilder {
        this.lastChange = lastChange
        return this
    }



    fun build(): UserItem {
        return UserItem(
            id = id,
            userId = userId,
            maintenanceItem = maintenanceItem,
            lastChange = lastChange,
            isDeleted = isDeleted

        )
    }

}