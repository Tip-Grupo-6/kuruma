package com.tip.kuruma.services

import Car
import com.tip.kuruma.repositories.CarRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CarService @Autowired constructor(
    private val carRepository: CarRepository
) {
    fun getAllCars(): List<Car> = carRepository.findAll()

    fun saveCar(car: Car): Car = carRepository.save(car)

    fun getCarById(id: Long): Car? = carRepository.findById(id).orElse(null)

    fun deleteCar(id: Long) = carRepository.deleteById(id)
}