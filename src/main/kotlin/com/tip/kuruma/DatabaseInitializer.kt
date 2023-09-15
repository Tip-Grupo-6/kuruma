package com.tip.kuruma

import com.tip.kuruma.models.Car
import com.tip.kuruma.services.CarItemService
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
	@Autowired
	lateinit var carItemService: CarItemService

    override fun run(vararg args: String?) {

		// Create some cars
		val car1 = Car(
			brand = "Toyota",
			model = "Corolla",
			years = 2015,
			color = "Red",
			image = "toyota_corolla.jpg"
		)
		val car2 = Car(
			brand = "Honda",
			model = "Civic",
			years = 2018,
			color = "Blue",
			image = "honda_civic.jpg"
		)
		val car3 = Car(
			brand = "Ford",
			model = "Focus",
			years = 2019,
			color = "Black",
			image = "ford_focus.jpg"
		)

		carService.saveCar(car1)
		carService.saveCar(car2)
		carService.saveCar(car3)

		// some car items
		val carItem1 = com.tip.kuruma.models.CarItem(
			name = "Oil",
			last_change = LocalDate.now().minusMonths(9),
			replacement_frequency = 6,
			due_status = false,
			car = car1
		)
		val carItem2 = com.tip.kuruma.models.CarItem(
			name = "Tire pressure",
			last_change = LocalDate.now().minusMonths(9),
			replacement_frequency = 2,
			due_status = false,
			car = car1
		)
		val carItem3 = com.tip.kuruma.models.CarItem(
			name = "Water",
			last_change = LocalDate.now().minusMonths(9),
			replacement_frequency = 3,
			due_status = false,
			car = car1
		)

		carItemService.saveCarItem(carItem1)
		carItemService.saveCarItem(carItem2)
		carItemService.saveCarItem(carItem3)

		val carItem4 = com.tip.kuruma.models.CarItem(
			name = "Oil",
			last_change = LocalDate.now().minusMonths(9),
			replacement_frequency = 6,
			due_status = false,
			car = car2
		)
		val carItem5 = com.tip.kuruma.models.CarItem(
			name = "Tire pressure",
			last_change = LocalDate.now(),
			replacement_frequency = 2,
			due_status = false,
			car = car2
		)
		val carItem6 = com.tip.kuruma.models.CarItem(
			name = "Water",
			last_change = LocalDate.now(),
			replacement_frequency = 3,
			due_status = false,
			car = car2
		)
		val carItem7 = com.tip.kuruma.models.CarItem(
			name = "Oil",
			last_change = LocalDate.now().minusMonths(6),
			replacement_frequency = 6,
			due_status = false,
			car = car3
		)
		val carItem8 = com.tip.kuruma.models.CarItem(
			name = "Tire pressure",
			last_change = LocalDate.now().minusMonths(2),
			replacement_frequency = 2,
			due_status = false,
			car = car3
		)
		val carItem9 = com.tip.kuruma.models.CarItem(
			name = "Water",
			last_change = LocalDate.now().minusMonths(3),
			replacement_frequency = 3,
			due_status = false,
			car = car3
		)

		carItemService.saveCarItem(carItem4)
		carItemService.saveCarItem(carItem5)
		carItemService.saveCarItem(carItem6)
		carItemService.saveCarItem(carItem7)
		carItemService.saveCarItem(carItem8)
		carItemService.saveCarItem(carItem9)

		// associate car items with cars
		car1.carItems = listOf(carItem1, carItem2, carItem3)
		car2.carItems = listOf(carItem4, carItem5, carItem6)
		car3.carItems = listOf(carItem7, carItem8, carItem9)


		// save cars and car items
		carService.saveCar(car1)
		carService.saveCar(car2)
		carService.saveCar(car3)



		// Create some notifications
		val notification1 = com.tip.kuruma.models.Notification(
			car = car1
		)
		val notification2 = com.tip.kuruma.models.Notification(
			car = car2
		)
		val notification3 = com.tip.kuruma.models.Notification(
			car = car3
		)

		notificationService.saveNotification(notification1)
		notificationService.saveNotification(notification2)
		notificationService.saveNotification(notification3)

    }
}