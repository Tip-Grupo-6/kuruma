package com.tip.kuruma.services

import com.tip.kuruma.models.CarItem
import com.tip.kuruma.repositories.CarItemRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class CarItemService(
    private val carItemRepository: CarItemRepository) {

    companion object {
        private val LOGGER = LoggerFactory.getLogger(CarItemService::class.java)
    }

    fun getAllCarItems(): List<CarItem> {
        return carItemRepository.findAll()
    }

    fun saveCarItem(carItem: CarItem): CarItem {
        LOGGER.info("Saving car item $carItem")
        return carItemRepository.save(carItem)
    }

    fun getCarItemById(id: Long): CarItem? {
        return carItemRepository.findById(id).orElse(null)
    }

    fun updateCarItem(id: Long, carItem: CarItem): CarItem {
        val carItemToUpdate = carItemRepository.findById(id).orElse(null)
        carItemToUpdate?.let {
            it.name = carItem.name
            it.last_change = carItem.last_change
            it.next_change_due = carItem.next_change_due
            it.replacement_frequency = carItem.replacement_frequency
            it.due_status = carItem.due_status

            return carItemRepository.save(it)
        }
        return carItem
    }

    fun deleteCarItem(id: Long) {
        val carItemToDelete = carItemRepository.findById(id).orElse(null)
        val updatedCarItem = carItemToDelete.copy(isDeleted = true)
        carItemRepository.save(updatedCarItem)
    }

}