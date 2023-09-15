package com.tip.kuruma.dto

import com.tip.kuruma.models.CarItem
import java.time.LocalDate

data class CarItemDTO(
    var id: Long? = null,
    var car_id: Long? = null,
    var is_deleted: Boolean? = false,
    var name: String? = null,
    var last_change: LocalDate = LocalDate.now(),
    var next_change_due: LocalDate = LocalDate.now(),
    var replacement_frequency: Int = 0,
    var due_status: Boolean = false
) {
    companion object {
        fun fromCarItem(carItem: CarItem): CarItemDTO {
            val hola = carItem.last_change.plusMonths(carItem.replacement_frequency.toLong())
            println("car_id")
            println(carItem.id)
            println(hola)
            val carItemDTO = CarItemDTO(
                id = carItem.id,
                name = carItem.name,
                car_id = carItem.id,
                is_deleted = carItem.isDeleted,
                last_change = carItem.last_change,
                replacement_frequency = carItem.replacement_frequency
            )
            carItemDTO.next_change_due = carItemDTO.last_change.plusMonths(carItemDTO.replacement_frequency.toLong())
            carItemDTO.due_status = carItemDTO.next_change_due.isBefore(LocalDate.now())

            return  carItemDTO
        }

        fun fromCarItems(carItems: List<CarItem>?): List<CarItemDTO> {
            return carItems?.map { fromCarItem(it) } ?: listOf()
        }
    }
}
