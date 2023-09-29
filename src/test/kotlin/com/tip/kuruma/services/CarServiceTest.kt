package com.tip.kuruma.services

import com.tip.kuruma.builders.CarBuilder
import com.tip.kuruma.models.Car
import com.tip.kuruma.repositories.CarRepository
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.test.annotation.Rollback
import org.springframework.transaction.annotation.Transactional

class CarServiceTest {
    private val carRepository: CarRepository  = mockk()
    private val carItemService: CarItemService = mockk()
    private val maintenanceService: MaintenanceService = mockk()

    private val carService: CarService = CarService(carRepository, carItemService, maintenanceService)

    @BeforeEach
    fun setup() {
        clearAllMocks()
    }

    private fun builtCar(): Car {
        return CarBuilder().withCarItems(null).build()
    }

    @Test
    fun getAllCars() {
        val car = builtCar()

        every { carRepository.findAll() } returns listOf(car)

        val cars = carService.getAllCars()

        // assert car info
        assert(cars?.isNotEmpty() == true)
        assert(cars?.get(0)?.brand == "Honda")
        assert(cars?.get(0)?.model == "Civic")
        assert(cars?.get(0)?.color == "white")

        // Verify that the carRepository.findAll() is never called
        verify(exactly = 1) {
            carRepository.findAll()
        }
    }

    @Test
    fun saveCar() {
        val car = CarBuilder().withBrand("Peugeot").withModel("208").withColor("Black").withCarItems(null).build()
        every { carRepository.save(car) } returns car

        // save car using carService.saveCar
        val createdCar = carService.saveCar(car)

        // assert car info
        assert(createdCar.brand == "Peugeot")
        assert(createdCar.model == "208")
        assert(createdCar.color == "Black")

        // verify that carRepository.save() is never called
        verify(exactly = 1) {
            carRepository.save(car)
        }
    }

    @Test
    fun getCarById() {
        val car = builtCar()
<<<<<<< HEAD
        every { carRepository.save(car) } returns car
        carService?.saveCar(car)
=======

        every { carRepository.findById(car.id!!) } returns Optional.of(car)
>>>>>>> 78b0dc1 (Adjust & Improve CarService test and make it DRY)

        // get car by id
        val carById = car.id?.let { carService?.getCarById(it) }

        // assert car info
        assert(carById?.brand == "Honda")
        assert(carById?.model == "Civic")
        assert(carById?.color == "white")

        // verify that carRepository.findById() is never called
        verify(exactly = 1) {
            carRepository.findById(car.id!!)
        }
    }

    @Test
    fun updateCar() {
           val car = builtCar()

            every { carRepository.findById(car.id!!) } returns Optional.of(car)

            // update car
            val updatedCar = car.copy(
                brand = "Another brand",
                year = 2023,
                color = "Another color",
                model = "Another model"
            )

            every { carRepository.save(updatedCar) } returns updatedCar

            // update car using carService.updateCar
            val carUpdated = carService.updateCar(car.id!!, updatedCar)

            // assert updateCar new values
            assert(carUpdated.brand == "Another brand")
            assert(carUpdated.model == "Another model")
            assert(carUpdated.color == "Another color")
            assert(carUpdated.year == 2023)

            // verify that carRepository.save() is never called
            verify(exactly = 1) {
                carRepository.save(updatedCar)
            }

        }

    @Test
    fun deleteCar() {
        var car = builtCar()

        every { carRepository.findById(car.id!!) } returns Optional.of(car)
        every { carRepository.save( car.copy(isDeleted = true)) } returns car.copy(isDeleted = true)

        carService.deleteCar(car.id!!)

        // Verify that carService.saveCar() is never called
        verify(exactly = 1) {
            carRepository.save(car.copy(isDeleted = true))
        }

    }

    @Test
    fun deleteAllCars() {
        builtCar()

        every { carRepository.deleteAll() } returns Unit

        carService.deleteAllCars()

        // verify that carRepository.deleteAll() is never called
        verify(exactly = 1) {
            carRepository.deleteAll()
        }
    }
}