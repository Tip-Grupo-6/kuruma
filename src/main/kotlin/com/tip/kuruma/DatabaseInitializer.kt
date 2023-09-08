package com.tip.kuruma

import com.tip.kuruma.models.Car
import com.tip.kuruma.services.CarService
import com.tip.kuruma.services.NotificationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class DatabaseInitializate : CommandLineRunner {
    @Autowired
    lateinit var carService: CarService
	@Autowired
	lateinit var notificationService: NotificationService

    override fun run(vararg args: String?) {

		// Create some cars
		val car1 = Car(
			brand = "Toyota",
			model = "Corolla",
			years = 2015,
			color = "Red",
			image = "toyota_corolla.jpg",
			lastOilChange = LocalDate.now().minusMonths(9),
			lastWaterCheck = LocalDate.now().minusMonths(9),
			lastTirePressureCheck = LocalDate.now().minusMonths(9)
		)
		val car2 = Car(
			brand = "Honda",
			model = "Civic",
			years = 2018,
			color = "Blue",
			image = "honda_civic.jpg",
			lastOilChange = LocalDate.now().minusMonths(9),
			lastWaterCheck = LocalDate.now(),
			lastTirePressureCheck = LocalDate.now()
		)
		val car3 = Car(
			brand = "Ford",
			model = "Focus",
			years = 2019,
			color = "Black",
			image = "ford_focus.jpg",
			lastOilChange = LocalDate.now().minusMonths(6),
			lastWaterCheck = LocalDate.now().minusMonths(3),
			lastTirePressureCheck = LocalDate.now().minusMonths(2)
		)

		carService.saveCar(car1)
		carService.saveCar(car2)
		carService.saveCar(car3)


		// Create some notifications
		val notification1 = com.tip.kuruma.models.Notification(
			message = "",
			car = car1
		)
		val notification2 = com.tip.kuruma.models.Notification(
			message = "",
			car = car2
		)
		val notification3 = com.tip.kuruma.models.Notification(
			message = "",
			car = car3
		)

		notificationService.saveNotification(notification1)
		notificationService.saveNotification(notification2)
		notificationService.saveNotification(notification3)

    }
}