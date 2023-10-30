package com.tip.kuruma.controllers

import com.tip.kuruma.dto.CarItemDTO
import com.tip.kuruma.dto.NotificationDTO
import com.tip.kuruma.models.Car
import com.tip.kuruma.models.CarItem
import com.tip.kuruma.models.MaintenanceItem
import com.tip.kuruma.models.Notification
import com.tip.kuruma.services.CarService
import com.tip.kuruma.services.NotificationService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDate

@SpringBootTest
class NotificationControllerTest {
    @Autowired
    private lateinit var carService: CarService

    @Autowired
    private val notificationService: NotificationService? = null
    @Autowired
    private val notificationController: NotificationController? = null


    @Test
    fun getAllNotifications() {/*
        val car = createAnyCar()
        carService.saveCar(car)
        val notification = createAndSaveNotification(car)
        notificationController?.createNotification(NotificationDTO.fromNotification(notification))
        val notifications = notificationController?.getAllNotifications()
        assert(notifications?.body?.isNotEmpty() == true)
        assert(notifications?.body?.get(0)?.maintenance_messages?.get("Oil Change") == "Oil change is due")
        assert(notifications?.body?.get(0)?.maintenance_messages?.get("Water Change") == "Water change is due")
        assert(notifications?.body?.get(0)?.maintenance_messages?.get("Tire Pressure") == "Tire pressure is low")*/
    }
    @Test
    fun createNotification() {
    }

    @Test
    fun getNotificationById() {
    }

    @Test
    fun updateNotification() {
    }

    @Test
    fun deleteNotification() {
    }

    private fun createAnyCar(): Car = Car(
        brand = "Honda",
        model = "Civic",
        year = 2023,
        color = "white",
        carItems = listOf(
            CarItem(
                    maintenanceItem = MaintenanceItem(
                            description = "Oil Change"
                    )
            )
        )
    )
}