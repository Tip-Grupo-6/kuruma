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

    fun deleteCarItem(id: Long) {
        carItemRepository.deleteById(id)
    }

}