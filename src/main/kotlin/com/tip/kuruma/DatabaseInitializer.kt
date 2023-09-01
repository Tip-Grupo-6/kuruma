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
			image = "https://images.unsplash.com/photo-1580894744768-9d9b3c0d4b0f?ixid=MnwxMjA3fDB8MHxzZWFyY2h8Mnx8Y2Fyc3xlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&w=1000&q=80"
		)
		val car2 = Car(
			brand = "Honda",
			model = "Civic",
			years = 2018,
			color = "Blue",
			image = "https://images.unsplash.com/photo-1580894744768-9d9b3c0d4b0f?ixid=MnwxMjA3fDB8MHxzZWFyY2h8Mnx8Y2Fyc3xlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&w=1000&q=80"
		)
		val car3 = Car(
			brand = "Ford",
			model = "Focus",
			years = 2019,
			color = "Black",
			image = "https://images.unsplash.com/photo-1580894744768-9d9b3c0d4b0f?ixid=MnwxMjA3fDB8MHxzZWFyY2h8Mnx8Y2Fyc3xlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&w=1000&q=80"
		)

		carService.saveCar(car1)
		carService.saveCar(car2)
		carService.saveCar(car3)

    }
}