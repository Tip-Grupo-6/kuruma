package com.tip.kuruma.controllers

import com.tip.kuruma.dto.CarDTO
import com.tip.kuruma.models.Car
import com.tip.kuruma.services.CarService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
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
    fun getCarById(@PathVariable id: Long): ResponseEntity<out Any> {
        val car = carService.getCarById(id)

        return if (car != null) {
            ResponseEntity.ok(CarDTO.fromCar(car))
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(mapOf("message" to "Car $id not found"))
        }
    }

    @PutMapping("/{id}")
    fun updateCar(@PathVariable id: Long,@RequestBody carUpdate: Car ): ResponseEntity<out Any> {
        var car =  carService.getCarById(id)

        return if (car != null) {
            car = car.id?.let { carService.updateCar(it, carUpdate) }
            return ResponseEntity.ok(car?.let { CarDTO.fromCar(it) })
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(mapOf("message" to "Car $id not found"))
        }


    }

    @DeleteMapping("/{id}")
    fun deleteCar(@PathVariable id: Long): Any {
        val car = carService.getCarById(id)
        return if (car != null) {
            car.isDeleted = true
            carService.saveCar(car)
            return ResponseEntity.status(HttpStatus.OK)
                .body(mapOf("message" to " ${car.getName()} deleted"))
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(mapOf("message" to "Car $id not found"))
        }
    }
}