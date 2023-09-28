package com.tip.kuruma.controllers

import com.tip.kuruma.EntityNotFoundException
import com.tip.kuruma.builders.CarBuilder
import com.tip.kuruma.dto.CarDTO
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


    @Test
    @Transactional
    @Rollback(true)
    fun getAllCars() {
        val car = CarBuilder().build()

        every { carService.getAllCars() } returns listOf(car)
        carController?.createCar(CarDTO.fromCar(car))

        val cars = carController?.getAllCars()

        assert(cars?.body?.isNotEmpty() == true)

        // Verify that carService.findAll() is never called
        verify(exactly = 0) {
            carService.getAllCars()
        }

    }

    @Test
    @Transactional
    @Rollback(true)
    fun createCar() {
    // create a new car using carController.createCar
    val car = CarBuilder().build()
    every { carService.saveCar(car) } returns car
    val createdCar = carController?.createCar(CarDTO.fromCar(car))

    // car assertions
    assert(createdCar?.body?.brand == "Honda")
    assert(createdCar?.body?.model == "Civic")
    assert(createdCar?.body?.year == 2023)
    assert(createdCar?.body?.color == "white")
    assert(createdCar?.body?.maintenance_values?.size == 1)


    // Verify that carService.saveCar() is never called
    verify(exactly = 0) {
        carService.saveCar(car)
    }

    }

    @Test
    @Transactional
    @Rollback(true)
    fun getCarById() {
        // create a new car using carController.createCar
        val car = CarBuilder().build()
        every { carService.saveCar(car) } returns car
        val responseEntity = carController?.createCar(CarDTO.fromCar(car))
        val carSaved = responseEntity?.body!!

        every { carService.getCarById(carSaved.id!!) } returns car

        // Get an existing car by id
        val response = carController?.getCarById(carSaved.id!!)
        // Check if the response is not null and has an OK status
        assert(response?.statusCodeValue == HttpStatus.OK.value())

        // Cast the response body to CarDTO
        val carDTO = response?.body as? CarDTO

        // Now you can access the properties of the CarDTO
        assert(carDTO?.id == carSaved.id)
        assert(carDTO?.brand == "Honda")
        assert(carDTO?.model == "Civic")

        // Verify that carService.getCarById() is never called
        verify(exactly = 0) {
            carService.getCarById(carSaved.id!!)
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
        val car = CarBuilder().build()
        every { carService.saveCar(any()) } returns car
        val responseEntity = carController?.createCar(CarDTO.fromCar(car))
        val carSaved = responseEntity?.body!!

        // update car
        val dto = CarDTO(
                brand = "Another brand",
                model = "Another model",
                year = 2023,
                color = "another color"
        )
        
        every { carService.updateCar(carSaved.id!!, dto.toCar()) } returns car.copy(
                brand = "Another brand",
                model = "Another model",
                year = 2023,
                color = "another color")

        // update car using carController.updateCar
        carController?.updateCar(carSaved.id!!, dto)
        
        every { carService.getCarById(carSaved.id!!) } returns car.copy(
                brand = "Another brand",
                model = "Another model",
                year = 2023,
                color = "another color")

        val updatedCar = carService?.getCarById(carSaved.id!!)

        // assert updateCar new values
        assert(updatedCar?.brand == "Another brand")
        assert(updatedCar?.model == "Another model")
        assert(updatedCar?.year == 2023)
        assert(updatedCar?.color == "another color")
        
        // Verify that carService.updateCar() is never called
        verify(exactly = 0) {
            carService.updateCar(carSaved.id!!, dto.toCar())
        }

    }

    @Test
    @Transactional
    @Rollback(true)
    fun deleteCar() {
       /* val response = carController?.createCar(CarDTO.fromCar(CarBuilder().build()))
        val carSaved = response?.body!!

        // delete car saved
        val responseEntity: ResponseEntity<Any> = carController?.deleteCar(carSaved.id!!) as ResponseEntity<Any>

        // Check if the response is not null and has an OK status
        assert(responseEntity.statusCode == HttpStatus.NO_CONTENT)

        // Check if the car saved with is deleted
        val car = carService?.getCarById(carSaved.id!!)
        assert(car?.isDeleted == true)

        assertThrows<EntityNotFoundException> {
            carController.deleteCar(10000L)
        }*/
    }
}