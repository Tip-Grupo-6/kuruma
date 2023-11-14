package com.tip.kuruma.controllers

import com.tip.kuruma.dto.CarDTO
import com.tip.kuruma.services.CarService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/cars")
@CrossOrigin(origins = ["http://localhost:3000"])
class CarController @Autowired constructor(
    private val carService: CarService
) {

    companion object {
        private val LOGGER = LoggerFactory.getLogger(CarController::class.java)
    }

    @GetMapping
    fun getAllCars(): ResponseEntity<List<CarDTO>> {
        LOGGER.info("Calling to GET /cars")
        val cars = carService.getAllCars()

        return ResponseEntity.ok(CarDTO.fromCars(cars))
    }

    @PostMapping
    fun createCar(@RequestBody carDTO: CarDTO): ResponseEntity<CarDTO> {
        LOGGER.info("Calling to POST /cars with request $carDTO")
        val savedCar = carService.saveCar(carDTO.toCar())
        return ResponseEntity.status(HttpStatus.CREATED).body(CarDTO.fromCar(savedCar))
    }

    @GetMapping("/{id}")
    fun getCarById(@PathVariable id: Long): ResponseEntity<CarDTO> {
        LOGGER.info("Calling to GET /cars/$id")
        val car = carService.getCarById(id)
        return ResponseEntity.ok(CarDTO.fromCar(car))
    }

    @PutMapping("/{id}")
    fun updateCar(@PathVariable id: Long,@RequestBody carDTO: CarDTO): ResponseEntity<CarDTO> {
        LOGGER.info("Calling to PUT /cars/$id with request $carDTO")
        val car = carService.updateCar(id, carDTO.toCar())
        return ResponseEntity.ok(CarDTO.fromCar(car))
    }

    @PatchMapping("/{id}")
    fun patchCar(@PathVariable id: Long, @RequestBody partialCarDTO: CarDTO): ResponseEntity<CarDTO> {
        LOGGER.info("Calling to PATCH /cars/$id with request $partialCarDTO")
        val updatedCar = carService.patchCar(id, partialCarDTO.toCar())
        return ResponseEntity.ok(CarDTO.fromCar(updatedCar))
    }

    @DeleteMapping("/{id}")
    fun deleteCar(@PathVariable id: Long): ResponseEntity<Unit> {
        LOGGER.info("Calling to DELETE /cars/$id")
        carService.deleteCar(id)
        return ResponseEntity.noContent().build()
    }
}