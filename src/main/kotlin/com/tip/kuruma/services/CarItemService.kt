package com.tip.kuruma.services

import com.tip.kuruma.EntityNotFoundException
import com.tip.kuruma.models.CarItem
import com.tip.kuruma.repositories.CarItemRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class CarItemService(
    private val carItemRepository: CarItemRepository
) {

    companion object {
        private val LOGGER = LoggerFactory.getLogger(CarItemService::class.java)
    }

    fun getAllCarItems(): List<CarItem> {
        LOGGER.info("Get all Car Items")
        return carItemRepository.findAll()
    }

    fun saveCarItem(carItem: CarItem): CarItem {
        LOGGER.info("Saving car item $carItem")
        return carItemRepository.save(carItem)
    }

    fun getCarItemById(id: Long): CarItem? {
       LOGGER.info("Find car item with id $id")
        return carItemRepository.findById(id).orElseThrow { EntityNotFoundException("car item with id $id not found") }
    }

    fun updateCarItem(id: Long, carItem: CarItem): CarItem {
        val carItemToUpdate = carItemRepository.findById(id).orElseThrow { EntityNotFoundException("car item with id $id not found") }
        carItemToUpdate?.let {
            it.name = carItem.name
            it.last_change = carItem.last_change
            it.next_change_due = carItem.next_change_due
            it.replacement_frequency = carItem.replacement_frequency
            it.due_status = carItem.due_status

            return carItemRepository.save(it)
        }
       LOGGER.info("Car Item with id $id has been updated")
        return carItem
    }

    fun deleteCarItem(id: Long) {
        val carItemToDelete = carItemRepository.findById(id).orElseThrow { EntityNotFoundException("car item with id $id not found") }
        val updatedCarItem = carItemToDelete.copy(isDeleted = true)
        carItemRepository.save(updatedCarItem)
       LOGGER.info("Car Item with id $id has been deleted")
    }

    fun deleteAllCarItems() = carItemRepository.deleteAll()

}