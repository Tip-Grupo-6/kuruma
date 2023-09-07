package com.tip.kuruma.controllers

import com.tip.kuruma.dto.CarDTO
import com.tip.kuruma.models.Car
import com.tip.kuruma.services.CarService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/cars")
class CarController @Autowired constructor(
    private val carService: CarService
) {
    @GetMapping
    fun getAllCars(): ResponseEntity<List<CarDTO>> {
        val cars = carService.getAllCars()

        return ResponseEntity.ok(CarDTO.fromCars(cars))
    }

    @PostMapping
    fun createCar(@RequestBody car: Car): ResponseEntity<CarDTO> {
        val savedCar = carService.saveCar(car)
        return ResponseEntity.status(201).body(CarDTO.fromCar(savedCar))
    }

    @GetMapping("/{id}")
    fun getCarById(@PathVariable id: Long): ResponseEntity<CarDTO> {
        val car = carService.getCarById(id)
        return if (car != null) ResponseEntity.ok(CarDTO.fromCar(car)) else ResponseEntity.notFound().build()
    }

    @PutMapping("/{id}")
    fun updateCar(@PathVariable id: Long,@RequestBody carUpdate: Car ): ResponseEntity<CarDTO> {
        val car = carService.updateCar(id, carUpdate);
        return ResponseEntity.ok(CarDTO.fromCar(car))
    }

    @DeleteMapping("/{id}")
    fun deleteCar(@PathVariable id: Long): ResponseEntity<Unit> {
        val car = carService.getCarById(id)
        if (car != null) {
            car.isDeleted = true
            carService.saveCar(car)
            return ResponseEntity.noContent().build()
        }
        return ResponseEntity.notFound().build()
    }
}