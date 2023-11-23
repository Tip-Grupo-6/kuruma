package com.tip.kuruma.repositories

import com.tip.kuruma.models.Car
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface CarRepository : JpaRepository<Car, Long> {
    fun findAllByIsDeletedIsFalse(): List<Car>

    @Query(value = "SELECT c.id FROM Car c WHERE c.userId = ?1")
    fun getCarIdByUser(userId: Long): Long?
}
