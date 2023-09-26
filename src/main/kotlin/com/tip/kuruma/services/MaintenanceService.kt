package com.tip.kuruma.services

import com.tip.kuruma.EntityNotFoundException
import com.tip.kuruma.models.MaintenanceItem
import com.tip.kuruma.repositories.MaintenanceItemRepository
import org.slf4j.LoggerFactory
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
class MaintenanceService(
        private val maintenanceItemRepository: MaintenanceItemRepository
) {

    companion object {
        private val LOGGER = LoggerFactory.getLogger(MaintenanceService::class.java)
    }

    fun getAll(): List<MaintenanceItem> {
        LOGGER.info("Find all maintenance items")
        return maintenanceItemRepository.findAll()
    }

    @Cacheable("maintenance_item_by_code")
    fun findByCode(code: String): MaintenanceItem {
        LOGGER.info("Find maintenance item by code $code")
        return maintenanceItemRepository.findByCode(code)
                .orElseThrow { EntityNotFoundException("maintenance item with code $code not found") }
    }


}