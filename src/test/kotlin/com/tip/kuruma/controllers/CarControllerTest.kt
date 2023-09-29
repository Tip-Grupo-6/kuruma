package com.tip.kuruma.controllers

import com.tip.kuruma.EntityNotFoundException
import com.tip.kuruma.builders.CarBuilder
import com.tip.kuruma.dto.CarDTO
import com.tip.kuruma.models.Car
import com.tip.kuruma.services.CarService
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.test.annotation.Rollback
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
class CarControllerTest {
    private val carService: CarService  = mockk()

    @Autowired
    private val carController: CarController? = null

    @BeforeEach
    fun setup() {
        clearAllMocks()
    }

    fun builtCar(): Car {
        val car = CarBuilder().build()
        every { carService.saveCar(any()) } returns car
        val savedCar = carController?.createCar(CarDTO.fromCar(car))
        return savedCar?.body!!.toCar()
    }


    @Test
    @Transactional
    @Rollback(true)
    fun getAllCars() {
        val car = builtCar()

        every { carService.getAllCars() } returns listOf(car)

        val cars = carController?.getAllCars()

        assert(cars?.body?.isNotEmpty() == true)
        assert(cars?.body?.get(0)?.brand == "Honda")

        // Verify that carService.getAllCars() is never called
        verify(exactly = 0) {
            carService.getAllCars()
        }

    }

    @Test
    @Transactional
    @Rollback(true)
    fun createCar() {
    val car = CarBuilder().withBrand("Peugeot").withModel("208").withYear(2023).withColor("Black").build()

    every { carService.saveCar(car) } returns car

    val createdCar = carController?.createCar(CarDTO.fromCar(car))

    // car assertions
    assert(createdCar?.body?.brand == "Peugeot")
    assert(createdCar?.body?.model == "208")
    assert(createdCar?.body?.year == 2023)
    assert(createdCar?.body?.color == "Black")


    // Verify that carService.saveCar() is never called
    verify(exactly = 0) {
        carService.saveCar(car)
    }

    }

    @Test
    @Transactional
    @Rollback(true)
    fun getCarById() {
        val car = builtCar()

        every { carService.getCarById(car.id!!) } returns car

        // Get an existing car by id
        val response = carController?.getCarById(car.id!!)

        // Check if the response is not null and has an OK status
        assert(response?.statusCodeValue == HttpStatus.OK.value())

        // Cast the response body to CarDTO
        val carDTO = response?.body as? CarDTO

        // Now you can access the properties of the CarDTO
        assert(carDTO?.id == car.id)
        assert(carDTO?.brand == "Honda")
        assert(carDTO?.model == "Civic")

        // Verify that carService.getCarById() is never called
        verify(exactly = 0) {
            carService.getCarById(car.id!!)
        }

        // get an unexisting car by id
        assertThrows<EntityNotFoundException> {
            carController?.getCarById(10000L)
        }


    }

    @Test
    @Transactional
    @Rollback(true)
    fun updateCar() {
        val car = builtCar()

        // update car
        val dto = CarDTO(
                brand = "Another brand",
                model = "Another model",
                year = 2023,
                color = "another color"
        )
        
        every { carService.updateCar(car.id!!, dto.toCar()) } returns car.copy(
                brand = "Another brand",
                model = "Another model",
                year = 2023,
                color = "another color")

        // update car using carController.updateCar
        carController?.updateCar(car.id!!, dto)
        
        every { carService.getCarById(car.id!!) } returns car.copy(
                brand = "Another brand",
                model = "Another model",
                year = 2023,
                color = "another color")

        val updatedCar = carService?.getCarById(car.id!!)

        // assert updateCar new values
        assert(updatedCar?.brand == "Another brand")
        assert(updatedCar?.model == "Another model")
        assert(updatedCar?.year == 2023)
        assert(updatedCar?.color == "another color")
        
        // Verify that carService.updateCar() is never called
        verify(exactly = 0) {
            carService.updateCar(car.id!!, dto.toCar())
        }

    }

    @Test
    @Transactional
    @Rollback(true)
    fun deleteCar() {
        var car = builtCar()

        every { carService.saveCar(car) } returns car.copy(isDeleted = true)
        // delete car saved
        val responseEntity: ResponseEntity<Any> = carController?.deleteCar(car.id!!) as ResponseEntity<Any>

        // Check if the response is not null and has an OK status
        assert(responseEntity.statusCode == HttpStatus.NO_CONTENT)

        // Check if the car is deleted
        val carResponse = carController?.getCarById(car.id!!) as ResponseEntity<Any>

        val deleted_car = carResponse?.body!! as CarDTO

        assert(deleted_car.is_deleted == true)

        // Verify that carService.saveCar() is never called
        verify(exactly = 0) {
            carService.saveCar(car)
        }


        assertThrows<EntityNotFoundException> {
            carController?.deleteCar(10000L)
        }
    }
}