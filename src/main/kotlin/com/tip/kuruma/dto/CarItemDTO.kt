package com.tip.kuruma.dto

import com.tip.kuruma.models.CarItem
import java.time.LocalDate

data class CarItemDTO(
    var id: Long? = null,
    var name: String? = null,
    var last_change: LocalDate? = null,
    var next_change_due: LocalDate? = null,
    var replacement_frequency: Int = 0,
    var due_status: Boolean = false,
    var is_deleted: Boolean? = false
) {
    companion object {
        fun fromCarItem(carItem: CarItem): CarItemDTO {
            val carItemDTO = CarItemDTO(
                id = carItem.id,
                name = carItem.name,
                is_deleted = carItem.isDeleted,
                last_change = carItem.last_change,
                replacement_frequency = carItem.replacement_frequency
            )
            carItemDTO.next_change_due = carItem.last_change.plusMonths(carItemDTO.replacement_frequency.toLong())
            carItemDTO.due_status = carItemDTO.next_change_due?.isBefore(LocalDate.now()) ?: false

            return  carItemDTO
        }

        fun fromCarItems(carItems: List<CarItem>?): List<CarItemDTO> {
            return carItems?.map { fromCarItem(it) } ?: listOf()
        }
    }

    fun toCarItem(): CarItem {
        return CarItem(
                name= this.name,
                last_change = this.last_change!!
        )
    }
}