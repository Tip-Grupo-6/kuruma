package com.tip.kuruma.controllers

import com.tip.kuruma.models.MaintenanceItem
import com.tip.kuruma.services.MaintenanceService
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/maintenance_items")
@CrossOrigin
class MaintenanceItemController(
        private val maintenanceService: MaintenanceService
) {

    companion object {
        private val LOGGER = LoggerFactory.getLogger(MaintenanceItemController::class.java)
    }

    @GetMapping
    fun getAllMaintenanceItems(): ResponseEntity<List<MaintenanceItem>> {
        LOGGER.info("Calling to GET /maintenance_items")
        val maintenanceItems = maintenanceService.getAll()
        return ResponseEntity.ok(maintenanceItems)
    }

}