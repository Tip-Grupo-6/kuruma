package com.tip.kuruma.controllers

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
    fun getAllCars(): ResponseEntity<List<Car>> {
        val cars = carService.getAllCars()
        return ResponseEntity.ok(cars)
    }

    @PostMapping
    fun createCar(@RequestBody car: Car): ResponseEntity<Car> {
        val savedCar = carService.saveCar(car)
        return ResponseEntity.status(201).body(savedCar)
    }

    @GetMapping("/{id}")
    fun getCarById(@PathVariable id: Long): ResponseEntity<Car?> {
        val car = carService.getCarById(id)
        return if (car != null) ResponseEntity.ok(car) else ResponseEntity.notFound().build()
    }

    @PutMapping("/{id}")
    fun updateCar(@PathVariable id: Long,@RequestBody carUpdate: Car ): ResponseEntity<Car> {
        val car = carService.updateCar(id, carUpdate);
        return ResponseEntity.ok(car)
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