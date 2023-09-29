package com.tip.kuruma.dto

import com.tip.kuruma.models.CarItem
import com.tip.kuruma.models.MaintenanceItem
import java.time.LocalDate

data class CarItemDTO(
    var id: Long? = null,
    var car_id: Long? = null,
    val code: String? = null,
    val name: String? = null,
    var last_change: LocalDate? = null,
    var next_change_due: LocalDate? = null,
    var due_status: Boolean = false,
    var is_deleted: Boolean? = false
) {
    companion object {
        fun fromCarItem(carItem: CarItem): CarItemDTO {
            val carItemDTO = CarItemDTO(
                id = carItem.id,
                car_id = carItem.carId,
                code = carItem.maintenanceItem?.code,
                name = carItem.maintenanceItem?.description,
                is_deleted = carItem.isDeleted,
                last_change = carItem.lastChange
            )
            carItem.maintenanceItem?.replacementFrequency?.let {
                val nextChangeDue = carItem.lastChange.plusMonths(it.toLong())
                carItemDTO.next_change_due = nextChangeDue
                carItemDTO.due_status = nextChangeDue.isBefore(LocalDate.now())
            }
            return  carItemDTO
        }

        fun fromCarItems(carItems: List<CarItem>?): List<CarItemDTO> {
            return carItems?.map { fromCarItem(it) } ?: listOf()
        }
    }

    fun toCarItem(): CarItem {
        return CarItem(
                carId = this.id,
                maintenanceItem = MaintenanceItem(code = this.code),
                lastChange = this.last_change ?: LocalDate.now(),
                isDeleted = this.is_deleted
        )
    }
}
