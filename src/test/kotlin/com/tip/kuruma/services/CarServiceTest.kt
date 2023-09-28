package com.tip.kuruma.services

import com.tip.kuruma.builders.CarBuilder
import com.tip.kuruma.controllers.CarController
import com.tip.kuruma.models.Car
import com.tip.kuruma.repositories.CarRepository
import io.mockk.clearAllMocks
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Rollback
import org.springframework.transaction.annotation.Transactional
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import java.util.*

@SpringBootTest
class CarServiceTest {
    @Autowired
    private val carService: CarService? = null

    private val carRepository: CarRepository  = mockk()

    @BeforeEach
    fun setup() {
        clearAllMocks()
    }

    private fun builtCar(): Car {
        return CarBuilder().withCarItems(listOf()).build()
    }

    @Test
    @Transactional
    @Rollback(true)
    fun getAllCars() {
        val car = builtCar()
        every { carRepository.findAll() } returns listOf(car)
        every { carRepository.save(car) } returns car

        carService?.saveCar(car)

        val cars = carService?.getAllCars()

        // assert car info
        assert(cars?.isNotEmpty() == true)
        assert(cars?.get(0)?.brand == "Honda")
        assert(cars?.get(0)?.model == "Civic")
        assert(cars?.get(0)?.color == "white")

        // Verify that the carRepository.findAll() is never called
        verify(exactly = 0) {
            carRepository.findAll()
        }
    }

    @Test
    @Transactional
    @Rollback(true)
    fun saveCar() {
        val car = builtCar()
        every { carRepository.save(car) } returns car
        carService?.saveCar(car)

        // assert car info
        assert(car.brand == "Honda")
        assert(car.model == "Civic")
        assert(car.color == "white")

        // verify that carRepository.save() is never called
        verify(exactly = 0) {
            carRepository.save(car)
        }
    }

    @Test
    @Transactional
    @Rollback(true)
    fun getCarById() {
        val car = builtCar()
        every { carRepository.save(car) } returns car
        // mock returns optional car
        carService?.saveCar(car)

        // get car by id
        val carById = car.id?.let { carService?.getCarById(it) }

        // assert car info
        assert(carById?.brand == "Honda")
        assert(carById?.model == "Civic")
        assert(carById?.color == "white")

        // verify that carRepository.findById() is never called
        verify(exactly = 0) {
            carRepository.findById(car.id!!)
        }
    }

    @Test
    @Transactional
    @Rollback(true)
    fun updateCar() {
           var car = builtCar()
           every { carRepository.save(car) } returns car
           carService?.saveCar(car)

            // update car
            val updatedCar = car.copy(
                brand = "Another brand",
                year = 2023,
                color = "Another color",
                model = "Another model"
            )

            every { carRepository.save(updatedCar) } returns updatedCar

            // update car using carService.updateCar
            updatedCar.id?.let { carService?.updateCar(it, updatedCar) }

            // get car by id
            val carById = car.id?.let { carService?.getCarById(it) }

            // assert updateCar new values
            assert(carById?.brand == "Another brand")
            assert(carById?.model == "Another model")
            assert(carById?.color == "Another color")
            assert(carById?.year == 2023)

            // verify that carRepository.save() is never called
            verify(exactly = 0) {
                carRepository.save(car)
            }

        }

    @Test
    @Transactional
    @Rollback(true)
    fun deleteCar() {
        val car = builtCar()
        every { carRepository.save(car) } returns car.copy(isDeleted = true)

        carService?.saveCar(car)

        // delete car by id
        car.id?.let { carService?.deleteCar(it) }

        // get car by id
        val carById = car.id?.let { carService?.getCarById(it) }

        // assert car info
        assert(carById?.isDeleted == true)

        // verify that carRepository.save() is never called
        verify(exactly = 0) {
            carRepository.save(car)
        }

    }

    @Test
    @Transactional
    @Rollback(true)
    fun deleteAllCars() {
        val car = builtCar()
        every { carRepository.save(car) } returns car.copy(isDeleted = true)
        carService?.saveCar(car)

        // delete all cars
        carService?.deleteAllCars()

        every { carRepository.findAll() } returns listOf()
        // get all cars
        val cars = carService?.getAllCars()

        // assert car info
        assert(cars?.isEmpty() == true)
    }
}