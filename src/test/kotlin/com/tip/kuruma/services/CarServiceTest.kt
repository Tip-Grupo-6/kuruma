package com.tip.kuruma.services

import com.tip.kuruma.models.Car
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class CarServiceTest {
    @Autowired
    private val carService: CarService? = null

    private fun createAnyCar(): Car = Car(
        brand = "Honda",
        model = "Civic",
        year = 2023,
        color = "white"
    )

    private fun createAndSaveAnyCar(): Car {
        val car = createAnyCar()
        return carService?.saveCar(car)!!
    }

    @Test
    fun getAllCars() {
        val car = createAndSaveAnyCar()
        carService?.saveCar(car)

        val cars = carService?.getAllCars()

        // assert car info
        assert(cars?.isNotEmpty() == true)
        assert(cars?.get(0)?.brand == "Honda")
        assert(cars?.get(0)?.model == "Civic")
        assert(cars?.get(0)?.color == "white")
    }

    @Test
    fun saveCar() {
        val car = createAndSaveAnyCar()

        // assert car info
        assert(car.brand == "Honda")
        assert(car.model == "Civic")
        assert(car.color == "white")
    }

    @Test
    fun getCarById() {
        val car = createAndSaveAnyCar()

        // get car by id
        val carById = car.id?.let { carService?.getCarById(it) }

        // assert car info
        assert(carById?.brand == "Honda")
        assert(carById?.model == "Civic")
        assert(carById?.color == "white")
    }

    @Test
    fun deleteCar() {
        val car = createAndSaveAnyCar()

        // delete car by id
        car.id?.let { carService?.deleteCar(it) }

        // get car by id
        val carById = car.id?.let { carService?.getCarById(it) }

        // assert car info
        assert(carById?.isDeleted == true)

    }

    @Test
    fun deleteAllCars() {
        val car = createAndSaveAnyCar()
        carService?.saveCar(car)

        // delete all cars
        carService?.deleteAllCars()

        // get all cars
        val cars = carService?.getAllCars()

        // assert car info
        assert(cars?.isEmpty() == true)
    }

    @Test
    fun updateCar() {
        val car = createAndSaveAnyCar()

        // update car
        val updatedCar = car.copy(
            brand = "Another brand",
            year = 2023,
            color = "Another color",
            model = "Another model"
        )

        // update car using carService.updateCar
        updatedCar.id?.let { carService?.updateCar(it, updatedCar) }

        // get car by id
        val carById = car.id?.let { carService?.getCarById(it) }

        // assert updateCar new values
        assert(carById?.brand == "Another brand")
        assert(carById?.model == "Another model")
        assert(carById?.color == "Another color")
        assert(carById?.year == 2023)

    }
}