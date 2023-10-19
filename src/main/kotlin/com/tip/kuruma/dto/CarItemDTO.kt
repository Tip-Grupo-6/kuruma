package com.tip.kuruma.dto

import com.tip.kuruma.models.CarItem
import com.tip.kuruma.models.MaintenanceItem
import java.time.LocalDate

data class CarItemDTO(
    var id: Long? = null,
    var car_id: Long? = null,
    val code: String? = null,
    val name: String? = null,
    val replacement_frequency: Int? = null,
    var last_change: LocalDate? = null,
    var next_change_due: LocalDate? = null,
    var due_status: Boolean = false,
    var is_deleted: Boolean? = false,
    var status_color: String? = null
) {
    companion object {
        fun fromCarItem(carItem: CarItem): CarItemDTO {
            val carItemDTO = CarItemDTO(
                id = carItem.id,
                car_id = carItem.carId,
                code = carItem.maintenanceItem?.code,
                name = carItem.maintenanceItem?.description,
                replacement_frequency = carItem.maintenanceItem?.replacementFrequency,
                is_deleted = carItem.isDeleted,
                last_change = carItem.lastChange
            )
            carItem.maintenanceItem?.replacementFrequency?.let {
                val nextChangeDue = carItem.lastChange.plusMonths(it.toLong())
                carItemDTO.next_change_due = nextChangeDue
                carItemDTO.due_status = nextChangeDue.isBefore(LocalDate.now())
                carItemDTO.status_color = carItemDTO.getCarItemStatusColor(carItemDTO)
            }
            return  carItemDTO
        }

        fun fromCarItems(carItems: List<CarItem>?): List<CarItemDTO> {
            return carItems?.map { fromCarItem(it) } ?: listOf()
        }
    }

    fun toCarItem(): CarItem {
        return CarItem(
                id = this.id,
                carId = this.car_id,
                maintenanceItem = MaintenanceItem(code = this.code, description = this.name, replacementFrequency = this.replacement_frequency),
                lastChange = this.last_change ?: LocalDate.now(),
                isDeleted = this.is_deleted
        )
    }

    fun getCarItemStatusColor(carItemDTO: CarItemDTO): String {
        // red status is when car item is due, green is when it's not due yet, and yellow is when it's due in the current month
        return when {
            carItemDTO.due_status -> "red"
            carItemDTO.next_change_due?.month == LocalDate.now().month -> "yellow"
            // green should have due status false and next_change_due month should be after current month
            carItemDTO.next_change_due?.isAfter(LocalDate.now())!! -> "green"
            else -> "unknown"
        }
    }
}
