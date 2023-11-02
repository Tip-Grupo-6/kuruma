package com.tip.kuruma.services

import com.tip.kuruma.EntityNotFoundException
import com.tip.kuruma.models.CarItem
import com.tip.kuruma.repositories.CarItemRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class CarItemService(
    private val carItemRepository: CarItemRepository,
    private val maintenanceService: MaintenanceService
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
        val maintenanceItem = maintenanceService.findByCode(carItem.maintenanceItem?.code!!)
        return carItemRepository.save(carItem.copy(maintenanceItem = maintenanceItem))
    }

    fun getCarItemById(id: Long): CarItem {
       LOGGER.info("Find car item with id $id")
        return carItemRepository.findById(id).orElseThrow { EntityNotFoundException("car item with id $id not found") }
    }

    fun updateCarItem(id: Long, carItem: CarItem): CarItem {
        val carItemToUpdate = getCarItemById(id)
        return carItemRepository.save(carItemToUpdate.copy(lastChange = carItem.lastChange, updated_at = LocalDate.now())).also {
            LOGGER.info("Car Item with id $id has been updated")
        }
    }

    fun deleteCarItem(id: Long) {
        val carItemToDelete = getCarItemById(id)
        doLogicDelete(carItemToDelete)
    }

    private fun doLogicDelete(carItemToDelete: CarItem) {
        val updatedCarItem = carItemToDelete.copy(isDeleted = true)
        carItemRepository.save(updatedCarItem)
        LOGGER.info("Car Item with id ${carItemToDelete.id} has been deleted")
    }

    fun deleteAllCarItems() = carItemRepository.deleteAll()

    fun updateCarItems(carId: Long, carItems: List<CarItem>): List<CarItem> {
        val carItemsFromDB = carItemRepository.findByCarId(carId)
        carItemsFromDB.forEach { doLogicDelete(it) }
        return carItems.map { this.saveCarItem(it.copy(carId = carId)) }
    }

}