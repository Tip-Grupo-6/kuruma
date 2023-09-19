package com.tip.kuruma.controllers

import com.tip.kuruma.dto.CarDTO
import com.tip.kuruma.models.Car
import com.tip.kuruma.models.CarItem
import com.tip.kuruma.services.CarService
import org.junit.jupiter.api.Test
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
        // get all cars
        val cars = carController?.getAllCars()
        // assert that the list of cars is not empty
        println(cars?.body)
        assert(cars?.body?.isNotEmpty() == true)
        assert(cars?.body?.size == 3)

        // assert first car
        assert(cars?.body?.get(0)?.id == 1L)
        assert(cars?.body?.get(0)?.brand == "Toyota")
        assert(cars?.body?.get(0)?.model == "Corolla")

        //assert second car
        assert(cars?.body?.get(1)?.id == 2L)
        assert(cars?.body?.get(1)?.brand == "Honda")
        assert(cars?.body?.get(1)?.model == "Civic")
    }

    @Test
    @Transactional
    @Rollback(true)
    fun createCar() {
    // create a new car using carController.createCar
    val car = Car(
        brand = "Honda",
        model = "Civic",
        years = 2023,
        color = "white",
        carItems = listOf(
            CarItem(
                name = "Oil Change"
            )
        )
    )
    val createdCar = carController?.createCar(car)

    // car assertions
    assert(createdCar?.body?.id == 4L)
    assert(createdCar?.body?.brand == "Honda")
    assert(createdCar?.body?.model == "Civic")
    assert(createdCar?.body?.years == 2023)
    assert(createdCar?.body?.color == "white")
    assert(createdCar?.body?.maintenance_values?.size == 1)
    assert(createdCar?.body?.maintenance_values?.get(0)?.name == "Oil Change")

    }

    @Test
    @Transactional
    @Rollback(true)
    fun getCarById() {
        // Get an existing car by id
        val response = carController?.getCarById(1L)
        // Check if the response is not null and has an OK status
        assert(response?.statusCodeValue == HttpStatus.OK.value())

        // Cast the response body to CarDTO
        val carDTO = response?.body as? CarDTO

        // Now you can access the properties of the CarDTO
        assert(carDTO?.id == 1L)
        assert(carDTO?.brand == "Toyota")
        assert(carDTO?.model == "Corolla")

        // get an unexisting car by id
        val response2 = carController?.getCarById(10L)
        // Check if the response is not null and has an OK status
        assert(response2?.statusCodeValue == HttpStatus.NOT_FOUND.value())


    }

    @Test
    @Transactional
    @Rollback(true)
    fun updateCar() {
        // get first car
        val car = carService?.getCarById(1L)
        // sanity check
        assert(car?.brand == "Toyota")
        assert(car?.model == "Corolla")
        assert(car?.years == 2015)
        assert(car?.color == "Red")


        // update car
        car?.brand = "Another brand"
        car?.model = "Another model"
        car?.years = 2023
        car?.color = "another color"

        // update car using carController.updateCar
        carController?.updateCar(1L, car as Car)

        var updatedCar = carService?.getCarById(1L)

        // assert updateCar new values
        assert(updatedCar?.brand == "Another brand")
        assert(updatedCar?.model == "Another model")
        assert(updatedCar?.years == 2023)
        assert(updatedCar?.color == "another color")

    }

    @Test
    @Transactional
    @Rollback(true)
    fun deleteCar() {
        // delete car with id 3
        val responseEntity: ResponseEntity<Any> = carController?.deleteCar(3L) as ResponseEntity<Any>
        println("Response entity: $responseEntity")

        // Check if the response is not null and has an OK status
        assert(responseEntity.statusCode == HttpStatus.OK)
        // Check car deleted message
        val responseBody = responseEntity.body as Map<*, *>
        assert(responseBody["message"] == "Ford Focus deleted")
        // Check if the car with id 1 is deleted
        val car = carService?.getCarById(3L)
        assert(car?.isDeleted == true)

        // try to delete a fake id car
        val responseEntity2: ResponseEntity<Any> = carController?.deleteCar(10L) as ResponseEntity<Any>
        assert(responseEntity2.statusCode == HttpStatus.NOT_FOUND)
        // assert responseEntity2 message
        val responseBody2 = responseEntity2.body as Map<*, *>
        assert(responseBody2["message"] == "Car 10 not found")
    }

}