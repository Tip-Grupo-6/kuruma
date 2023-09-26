package com.tip.kuruma.services

import com.tip.kuruma.EntityNotFoundException
import com.tip.kuruma.models.Car
import com.tip.kuruma.models.MaintenanceItem
import com.tip.kuruma.repositories.CarRepository
import com.tip.kuruma.repositories.MaintenanceItemRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CarService @Autowired constructor(
        private val carRepository: CarRepository,
        private val carItemService: CarItemService,
        private val maintenanceService: MaintenanceService
) {

    companion object {
        private val LOGGER = LoggerFactory.getLogger(CarService::class.java)
    }

    fun getAllCars(): List<Car> {
        LOGGER.info("Get all cars")
        return carRepository.findAll()
    }

    @Transactional
    fun saveCar(car: Car): Car {
        LOGGER.info("Saving car $car")
        val savedCar = carRepository.save(car)
        savedCar.carItems?.map {
            val maintenanceItem = maintenanceService.findByCode(it.maintenanceItem?.code!!)
            carItemService.saveCarItem(it.copy(car_id = savedCar.id, maintenanceItem = maintenanceItem))
        }
        return savedCar
    }

    fun getCarById(id: Long): Car {
        LOGGER.info("Find car with id $id")
        return carRepository.findById(id)
                .orElseThrow { EntityNotFoundException("car with id $id not found") }
    }

    fun deleteCar(id: Long) {
        val existingCar = this.getCarById(id)
        val updatedCar = existingCar.copy(isDeleted = true)
        carRepository.save(updatedCar)
        LOGGER.info("Car with id $id has been deleted")
    }

    fun deleteAllCars() = carRepository.deleteAll()

    fun updateCar(id: Long, car: Car): Car {
        val existingCar = this.getCarById(id)
        val updatedCar = existingCar.copy(
                brand = car.brand,
                model = car.model,
                year = car.year,
                color = car.color,
                image = car.image
        )
        return carRepository.save(updatedCar).also {
            LOGGER.info("Car with id $id has been updated")
        }
    }
}
