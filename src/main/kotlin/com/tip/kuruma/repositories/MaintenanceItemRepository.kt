package com.tip.kuruma.repositories

import com.tip.kuruma.models.MaintenanceItem
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface MaintenanceItemRepository: JpaRepository<MaintenanceItem, Long> {

    fun findByCode(code: String): Optional<MaintenanceItem>

}