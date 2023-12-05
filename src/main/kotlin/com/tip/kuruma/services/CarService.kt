package com.tip.kuruma.services

import com.tip.kuruma.EntityNotFoundException
import com.tip.kuruma.models.Car
import com.tip.kuruma.repositories.CarRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

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
        return carRepository.findAllByIsDeletedIsFalse()
    }

    @Transactional
    fun saveCar(car: Car): Car {
        LOGGER.info("Saving car $car")
        val savedCar = carRepository.save(car)
        val carItemSaved = savedCar.carItems?.map {
            val maintenanceItem = maintenanceService.findByCode(it.maintenanceItem?.code!!)
            carItemService.saveCarItem(it.copy(carId = savedCar.id, maintenanceItem = maintenanceItem))
        }
        return savedCar.copy(carItems = carItemSaved)
    }

    fun getCarById(id: Long): Car {
        LOGGER.info("Find car with id $id")
        val car = carRepository.findById(id)
                    .orElseThrow { EntityNotFoundException("car with id $id not found") }
        val carItems = carItemService.getAllByCarId(car!!)
        return car.copy(carItems = carItems)
    }

    fun getCarByIds(ids: List<Long>): List<Car> {
        LOGGER.info("Find cars with ids $ids")
        return carRepository.findAllById(ids)
    }

    fun deleteCar(id: Long) {
        val existingCar = this.getCarById(id)
        val updatedCar = existingCar.copy(isDeleted = true)
        carRepository.save(updatedCar)
        LOGGER.info("Car with id $id has been deleted")
    }

    fun deleteAllCars() = carRepository.deleteAll()

    @Transactional
    fun updateCar(id: Long, car: Car): Car {
        val existingCar = this.getCarById(id)
        val updatedCar = existingCar.copy(
                brand = car.brand,
                model = car.model,
                year = car.year,
                color = car.color,
                image = car.image,
                kilometers = car.kilometers,
                updated_at = LocalDate.now()
        )
        return carRepository.save(updatedCar).let { carUpdated ->
            LOGGER.info("Car with id $id has been updated")
            val updateCarItems = car.carItems?.let {
                carItemService.updateCarItems(id, it)
            }
            carUpdated.copy(carItems = updateCarItems)
        }
    }

    @Transactional
    fun patchCar(id: Long, patchCar: Car): Car {
        val existingCar = this.getCarById(id)
        val updatedCar = existingCar.copy(
            brand = patchCar.brand ?: existingCar.brand,
            model = patchCar.model ?: existingCar.model,
            year = patchCar.year ?: existingCar.year,
            color = patchCar.color ?: existingCar.color,
            image = patchCar.image ?: existingCar.image,
            kilometers = patchCar.kilometers ?: existingCar.kilometers,
            updated_at = LocalDate.now()
        )

        LOGGER.info("Car with id $id has been patched")

        return carRepository.save(updatedCar)
    }
}
