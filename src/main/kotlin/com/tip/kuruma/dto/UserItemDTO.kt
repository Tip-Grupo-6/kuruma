package com.tip.kuruma.dto

import com.tip.kuruma.models.UserItem
import com.tip.kuruma.models.MaintenanceItem
import java.time.LocalDate

data class UserItemDTO(
    var id: Long? = null,
    var user_id: Long? = null,
    val code: String? = null,
    val name: String? = null,
    val replacement_frequency: Int? = null,
    var last_change: LocalDate? = null,
    var next_change_due: LocalDate? = null,
    var status_color: String? = null,
    var due_status: Boolean = false,
    var is_deleted: Boolean? = false,
    var created_at: LocalDate? = LocalDate.now(),
    var updated_at: LocalDate? = LocalDate.now()
) {
    companion object {
        fun fromUserItem(userItem: UserItem): UserItemDTO {
            val userItemDTO = UserItemDTO(
                id = userItem.id,
                user_id = userItem.userId,
                code = userItem.maintenanceItem?.code,
                name = userItem.maintenanceItem?.description,
                replacement_frequency = userItem.maintenanceItem?.replacementFrequency,
                is_deleted = userItem.isDeleted,
                last_change = userItem.lastChange,
                created_at = userItem.created_at,
                updated_at = userItem.updated_at
            )
            userItem.maintenanceItem?.replacementFrequency?.let {
                val nextChangeDue = userItem.lastChange.plusMonths(it.toLong())
                userItemDTO.next_change_due = nextChangeDue
                userItemDTO.due_status = nextChangeDue.isBefore(LocalDate.now())
                userItemDTO.status_color = userItemDTO.getUserItemStatusColor(userItemDTO)
            }
            return  userItemDTO
        }

        fun fromUserItems(userItems: List<UserItem>?): List<UserItemDTO> {
            return userItems?.map { fromUserItem(it) } ?: listOf()
        }
    }

    fun toUserItem(): UserItem {
        return UserItem(
                id = this.id,
                userId = this.user_id,
                maintenanceItem = MaintenanceItem(code = this.code, description = this.name, replacementFrequency = this.replacement_frequency),
                lastChange = this.last_change ?: LocalDate.now(),
                isDeleted = this.is_deleted,
                created_at = this.created_at,
                updated_at = this.updated_at
        )
    }

    fun getUserItemStatusColor(userItemDTO: UserItemDTO): String {
        return when {
            userItemDTO.due_status -> "red"
            userItemDTO.next_change_due?.month == LocalDate.now().month && userItemDTO.next_change_due?.year == LocalDate.now().year   -> "yellow"
            userItemDTO.next_change_due?.isAfter(LocalDate.now().plusMonths(1))!!  -> "green"
            else -> "green"
        }
    }
}
