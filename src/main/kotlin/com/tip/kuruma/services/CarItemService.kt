package com.tip.kuruma.services

import com.tip.kuruma.models.CarItem
import com.tip.kuruma.repositories.CarItemRepository
import com.tip.kuruma.repositories.CarRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
@Service
class CarItemService @Autowired constructor(
    private val carItemRepository: CarItemRepository) {

    fun getAllCarItems(): List<CarItem> {
        return carItemRepository!!.findAll()
    }

    fun saveCarItem(carItem: CarItem): CarItem {
        return carItemRepository!!.save(carItem)
    }

    fun getCarItemById(id: Long): CarItem? {
        return carItemRepository!!.findById(id).orElse(null)
    }

    fun deleteCarItem(id: Long) {
        carItemRepository!!.deleteById(id)
    }

}