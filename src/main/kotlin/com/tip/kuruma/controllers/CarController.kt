package com.tip.kuruma.controllers

import Car
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

    @DeleteMapping("/{id}")
    fun deleteCar(@PathVariable id: Long): ResponseEntity<Unit> {
        carService.deleteCar(id)
        return ResponseEntity.noContent().build()
    }
}