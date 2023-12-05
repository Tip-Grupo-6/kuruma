package com.tip.kuruma.repositories

import com.tip.kuruma.models.CarItem
import org.springframework.data.jpa.repository.JpaRepository

interface  CarItemRepository : JpaRepository<CarItem, Long> {

    fun findByCarIdAndIsDeletedIsFalse(id: Long): List<CarItem>
}