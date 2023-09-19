package com.tip.kuruma.services

import com.tip.kuruma.models.Car
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

    fun deleteAllCars() = carRepository.deleteAll()

    fun updateCar(id: Long, car: Car): Car {
        val existingCar = carRepository.findById(id).orElse(null)
        existingCar.brand = car.brand
        existingCar.model = car.model
        existingCar.years = car.years
        existingCar.color = car.color
        existingCar.image = car.image
        existingCar.isDeleted = car.isDeleted
        return carRepository.save(existingCar)
    }
}
