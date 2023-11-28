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
    val kilometers_frequency: Int? = null,
    val initial_car_kilometers: Int? = null,
    var current_kms_since_last_change: Int? = null,
    var km_maintenance_messages: String? = null,
    var last_change: LocalDate? = null,
    var next_change_due: LocalDate? = null,
    var due_status: Boolean = false,
    var is_deleted: Boolean? = false,
    var status_color: String? = null,
    var created_at: LocalDate? = LocalDate.now(),
    var updated_at: LocalDate? = LocalDate.now()
) {
    companion object {
        fun fromCarItem(carItem: CarItem): CarItemDTO {
            val carItemDTO = CarItemDTO(
                id = carItem.id,
                car_id = carItem.carId,
                code = carItem.maintenanceItem?.code,
                name = carItem.maintenanceItem?.description,
                replacement_frequency = carItem.maintenanceItem?.replacementFrequency,
                kilometers_frequency = carItem.maintenanceItem?.kilometersFrequency,
                initial_car_kilometers = carItem.initialCarKilometers,
                current_kms_since_last_change = carItem.currentKmsSinceLastChange,
                is_deleted = carItem.isDeleted,
                last_change = carItem.lastChange,
                created_at = carItem.created_at,
                updated_at = carItem.updated_at
            )
            carItem.maintenanceItem?.replacementFrequency?.let {
                val nextChangeDue = carItem.lastChange.plusMonths(it.toLong())
                carItemDTO.next_change_due = nextChangeDue
                carItemDTO.due_status = nextChangeDue.isBefore(LocalDate.now())
                carItemDTO.status_color = carItemDTO.getCarItemStatusColor(carItemDTO)
                carItemDTO.km_maintenance_messages = carItemDTO.getKilometerMaintenanceMessage(carItemDTO)
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
                maintenanceItem = MaintenanceItem(code = this.code, description = this.name, replacementFrequency = this.replacement_frequency, kilometersFrequency = this.kilometers_frequency),
                lastChange = this.last_change ?: LocalDate.now(),
                isDeleted = this.is_deleted,
                created_at = this.created_at,
                updated_at = this.updated_at
        )
    }

    fun getCarItemStatusColor(carItemDTO: CarItemDTO): String {
        val maintenanceItemKilometerFrequency = carItemDTO.toCarItem().maintenanceItem?.kilometersFrequency  ?: 0
        val currentKmsSinceLastChange = carItemDTO.current_kms_since_last_change  ?: 0
        return when {
            carItemDTO.due_status || currentKmsSinceLastChange > maintenanceItemKilometerFrequency -> "red"
            carItemDTO.next_change_due?.isAfter(LocalDate.now().plusMonths(1))!! && currentKmsSinceLastChange < maintenanceItemKilometerFrequency -> "green"
            carItemDTO.next_change_due?.month == LocalDate.now().month && carItemDTO.next_change_due?.year == LocalDate.now().year  ||  currentKmsSinceLastChange == maintenanceItemKilometerFrequency -> "yellow"
            else -> "green"
        }
    }

    fun getKilometerMaintenanceMessage(carItemDTO: CarItemDTO): String {
        val maintenanceItemKilometerFrequency = carItemDTO.toCarItem().maintenanceItem?.kilometersFrequency ?: 0
        val currentKmsSinceLastChange = carItemDTO.current_kms_since_last_change ?: 0
        val carItemName = carItemDTO.name ?: "Ítem"
        return when {
            currentKmsSinceLastChange == maintenanceItemKilometerFrequency -> "Has alcanzado los kilómetros recomendados de tu $carItemName. Ya es hora de cambiarlo"
            currentKmsSinceLastChange < maintenanceItemKilometerFrequency -> "Te faltan ${maintenanceItemKilometerFrequency - currentKmsSinceLastChange} kilómetros para cambiar tu $carItemName"
            else -> "Has superado los kilómetros recomendados de tu $carItemName. Deberías cambiarlo lo antes posible"
        }
    }
}
