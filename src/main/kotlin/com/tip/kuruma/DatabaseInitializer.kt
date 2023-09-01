package com.tip.kuruma

import com.tip.kuruma.models.Car
import com.tip.kuruma.services.CarService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class DatabaseInitializate : CommandLineRunner {
    @Autowired
    lateinit var carService: CarService

    override fun run(vararg args: String?) {

		// Create some cars
		val car1 = Car(
			brand = "Toyota",
			model = "Corolla",
			years = 2015,
			color = "Red",
			image = "car_image.png"
		)
		val car2 = Car(
			brand = "Honda",
			model = "Civic",
			years = 2018,
			color = "Blue",
			image = "car_image.png"
		)
		val car3 = Car(
			brand = "Ford",
			model = "Focus",
			years = 2019,
			color = "Black",
			image = "car_image.png"
		)

		carService.saveCar(car1)
		carService.saveCar(car2)
		carService.saveCar(car3)

    }
}