package com.tip.kuruma.controllers

import com.tip.kuruma.dto.CarItemDTO
import com.tip.kuruma.services.CarItemService
import com.tip.kuruma.services.CarService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/car_items")
class CarItemController @Autowired constructor(
    private val carItemService: CarItemService
) {

    companion object {
        private val LOGGER = LoggerFactory.getLogger(CarItemService::class.java)
    }

    @GetMapping
    fun getAllCarItems(): ResponseEntity<List<CarItemDTO>> {
        LOGGER.info("Calling to GET /car_items")
        val car_items = carItemService.getAllCarItems()

        return ResponseEntity.ok(CarItemDTO.fromCarItems(car_items))
    }

    @PostMapping
    fun createCarItem(@RequestBody carItemDTO: CarItemDTO): ResponseEntity<CarItemDTO> {
        LOGGER.info("Calling to POST /car_items with request $carItemDTO")
        val savedCarItem = carItemService.saveCarItem(carItemDTO.toCarItem())
        return ResponseEntity.status(HttpStatus.CREATED).body(CarItemDTO.fromCarItem(savedCarItem))
    }

    @GetMapping("/{id}")
    fun getCarItemByID(@PathVariable id: Long): ResponseEntity<CarItemDTO> {
        LOGGER.info("Calling to GET /car_items/$id")
        val carItem = carItemService.getCarItemById(id)
        return ResponseEntity.ok(carItem?.let { CarItemDTO.fromCarItem(it) })
    }

    @PutMapping("/{id}")
    fun updateCarItem(@PathVariable id: Long,@RequestBody carItemDTO: CarItemDTO): ResponseEntity<CarItemDTO> {
        LOGGER.info("Calling to PUT /car_items/$id with request $carItemDTO")
        val carItem = carItemService.updateCarItem(id, carItemDTO.toCarItem())
        return ResponseEntity.ok(CarItemDTO.fromCarItem(carItem))
    }

    @DeleteMapping("/{id}")
    fun deleteCarItem(@PathVariable id: Long): ResponseEntity<Unit> {
        LOGGER.info("Calling to DELETE /car_items/$id")
        carItemService.deleteCarItem(id)
        return ResponseEntity.noContent().build()
    }
}