package com.tip.kuruma.services

import com.tip.kuruma.EntityNotFoundException
import com.tip.kuruma.builders.CarBuilder
import com.tip.kuruma.models.Car
import com.tip.kuruma.repositories.CarRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Rollback
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
class CarServiceTest  {
    @Autowired
    private lateinit var carRepository: CarRepository

    @Autowired
    private lateinit var carService: CarService

    private fun builtCar(): Car {
        return CarBuilder().withUserId(1L).withCarItems(listOf()).withId(null).build()
    }

    @BeforeEach
    fun clearDatabase() {
        carRepository.deleteAll()
    }

    @Test
    @Transactional
    @Rollback
    fun `fetching all cars when there is none of the available `() {
        val foundCars = carService.getAllCars()
        assertEquals(0, foundCars.size)
    }

    @Test
    @Transactional
    @Rollback
    fun `fetching all cars when there is one of the available `() {
        val car = builtCar()
        val savedCar = carService.saveCar(car)
        val foundCars = carService.getAllCars()
        assertEquals(1, foundCars.size)
        assertEquals(savedCar.id, foundCars[0].id)
    }

    @Test
    @Transactional
    @Rollback
    fun `fetching a car by id when it exists`() {
        val car = builtCar()
        val savedCar = carService.saveCar(car)
        val foundCar = carService.getCarById(savedCar.id!!)
        assertEquals(savedCar.id, foundCar.id)
    }

    @Test
    @Transactional
    @Rollback
    fun `fetching a car by id when it does not exist`() {
        val car = builtCar()
        val savedCar = carService.saveCar(car)
        assertThrows(EntityNotFoundException::class.java) {
            carService.getCarById(savedCar.id!! + 1)
        }
    }

    @Test
    @Transactional
    @Rollback
    fun `saving a car`() {
        val car = builtCar()
        val savedCar = carService.saveCar(car)
        // id is not null
        assert(savedCar.id != null)
        assertEquals(car.brand, savedCar.brand)
        assertEquals(car.model, savedCar.model)
        assertEquals(car.year, savedCar.year)
        assertEquals(car.color, savedCar.color)
        assertEquals(car.isDeleted, savedCar.isDeleted)
    }

    @Test
    @Transactional
    @Rollback
    fun `deleting a car by id when it exists`() {
        val car = builtCar()
        val savedCar = carService.saveCar(car)
        carService.deleteCar(savedCar.id!!)
        val deletedCar = carService.getCarById(savedCar.id!!)
        assertEquals(true, deletedCar.isDeleted)
    }

    @Test
    @Transactional
    @Rollback
    fun `deleting a car by id when it does not exist`() {
        val car = builtCar()
        val savedCar = carService.saveCar(car)
        assertThrows(EntityNotFoundException::class.java) {
            carService.deleteCar(savedCar.id!! + 1)
        }
    }

    @Test
    @Transactional
    @Rollback
    fun `updating a car by id when it exists`() {
        val car = builtCar()
        val savedCar = carService.saveCar(car)
        val updatedCar = carService.updateCar(savedCar.id!!, car.copy(brand = "New Brand"))
        assertEquals("New Brand", updatedCar.brand)
    }

    @Test
    @Transactional
    @Rollback
    fun `updating a car by id when it does not exist`() {
        val car = builtCar()
        val savedCar = carService.saveCar(car)
        assertThrows(EntityNotFoundException::class.java) {
            carService.updateCar(savedCar.id!! + 1, car.copy(brand = "New Brand"))
        }
    }
}
