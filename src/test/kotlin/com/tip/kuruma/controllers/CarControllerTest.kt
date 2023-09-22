package com.tip.kuruma.controllers

import com.tip.kuruma.EntityNotFoundException
import com.tip.kuruma.dto.CarDTO
import com.tip.kuruma.models.Car
import com.tip.kuruma.models.CarItem
import com.tip.kuruma.services.CarService
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
    @Autowired
    private val carService: CarService? = null

    @Autowired
    private val carController: CarController? = null


    @Test
    fun getAllCars() {
        val car = createAnyCar()
        carController?.createCar(CarDTO.fromCar(car))

        // get all cars
        val cars = carController?.getAllCars()
        // assert that the list of cars is not empty

        assert(cars?.body?.isNotEmpty() == true)
    }

    @Test
    @Transactional
    @Rollback(true)
    fun createCar() {
    // create a new car using carController.createCar
    val car = createAnyCar()
    val createdCar = carController?.createCar(CarDTO.fromCar(car))

    // car assertions
    assert(createdCar?.body?.brand == "Honda")
    assert(createdCar?.body?.model == "Civic")
    assert(createdCar?.body?.year == 2023)
    assert(createdCar?.body?.color == "white")
    assert(createdCar?.body?.maintenance_values?.size == 1)
    assert(createdCar?.body?.maintenance_values?.get(0)?.name == "Oil Change")

    }

    @Test
    @Transactional
    @Rollback(true)
    fun getCarById() {
        // create a new car using carController.createCar
        val car = createAnyCar()
        val responseEntity = carController?.createCar(CarDTO.fromCar(car))
        val carSaved = responseEntity?.body!!

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

        // get an unexisting car by id
        assertThrows<EntityNotFoundException> {
            carController?.getCarById(10000L)
        }


    }

    @Test
    @Transactional
    @Rollback(true)
    fun updateCar() {
        val responseEntity = carController?.createCar(CarDTO.fromCar(createAnyCar()))
        val carSaved = responseEntity?.body!!

        // update car
        val dto = CarDTO(
                brand = "Another brand",
                model = "Another model",
                year = 2023,
                color = "another color"
        )

        // update car using carController.updateCar
        carController?.updateCar(carSaved.id!!, dto)

        var updatedCar = carService?.getCarById(carSaved.id!!)

        // assert updateCar new values
        assert(updatedCar?.brand == "Another brand")
        assert(updatedCar?.model == "Another model")
        assert(updatedCar?.year == 2023)
        assert(updatedCar?.color == "another color")

    }

    @Test
    @Transactional
    @Rollback(true)
    fun deleteCar() {
        val response = carController?.createCar(CarDTO.fromCar(createAnyCar()))
        val carSaved = response?.body!!

        // delete car saved
        val responseEntity: ResponseEntity<Any> = carController?.deleteCar(carSaved.id!!) as ResponseEntity<Any>

        // Check if the response is not null and has an OK status
        assert(responseEntity.statusCode == HttpStatus.NO_CONTENT)
        // Check car deleted message
//        val responseBody = responseEntity.body as Map<*, *>
//        assert(responseBody["message"] == "Ford Focus deleted")

        // Check if the car saved with is deleted
        val car = carService?.getCarById(carSaved.id!!)
        assert(car?.isDeleted == true)

        assertThrows<EntityNotFoundException> {
            carController.deleteCar(10000L)
        }
    }


    private fun createAnyCar(): Car = Car(
            brand = "Honda",
            model = "Civic",
            year = 2023,
            color = "white",
            carItems = listOf(
                    CarItem(
                            name = "Oil Change"
                    )
            )
    )
}