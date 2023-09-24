package com.tip.kuruma.services

import com.tip.kuruma.models.CarItem
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDate

@SpringBootTest
class CarItemServiceTest {
    @Autowired
    private val carItemService: CarItemService? = null

    private fun createAnyCarItem(): CarItem = CarItem(
        name = "Oil Change",
        last_change = LocalDate.now(),
        replacement_frequency = 30,
        due_status = false
    )

    private fun createAndSaveAnyCar(): CarItem {
        val carItem = createAnyCarItem()
        return carItemService?.saveCarItem(carItem)!!
    }

    @Test
    fun getAllCars() {
        val carItem = createAndSaveAnyCar()

        val carItems = carItemService?.getAllCarItems()

        // assert car info
        assert(carItems?.isNotEmpty() == true)
        assert(carItems?.get(0)?.name == "Oil Change")
        assert(carItems?.get(0)?.replacement_frequency == 30)
        assert(carItems?.get(0)?.due_status == false)

    }

    @Test
    fun saveCarItem() {
        val carItem =  createAndSaveAnyCar()

        // assert car info
        assert(carItem.name == "Oil Change")
        assert(carItem.replacement_frequency == 30)
        assert(carItem.due_status == false)
    }

    @Test
    fun getCarById() {
        val carItem =  createAndSaveAnyCar()

        // get car by id
        val carById = carItem.id?.let { carItemService?.getCarItemById(it) }

        // assert carItem info

    }

    @Test
    fun deleteCar() {
        val carItem =  createAndSaveAnyCar()

        // delete car by id
        carItem.id?.let { carItemService?.deleteCarItem(it) }

        // get car by id
        val carById = carItem.id?.let { carItemService?.getCarItemById(it) }

        // assert car info
        assert(carById?.isDeleted == true)

    }

    @Test
    fun deleteAllCars() {
        val carItem =  createAndSaveAnyCar()

        // delete all cars
        carItemService?.deleteAllCarItems()

        // get all cars
        val cars = carItemService?.getAllCarItems()

        // assert car info
        assert(cars?.isEmpty() == true)
    }

    @Test
    fun updateCar() {
        val carItem =  createAndSaveAnyCar()

        // update carItem
        val updateCarItem = carItem.copy(
            name = "Another name",
            last_change = LocalDate.now().plusMonths(1),
            replacement_frequency = 7,
            due_status = false
        )

        // update car using carItemService.updateCar
        updateCarItem.id?.let { carItemService?.updateCarItem(it, updateCarItem) }

        // get car by id
        val carItemById = carItem.id?.let { carItemService?.getCarItemById(it) }

        // assert updateCar new values
        assert(carItemById?.name == "Another name")
        assert(carItemById?.last_change == LocalDate.now().plusMonths(1))
        assert(carItemById?.replacement_frequency == 7)
        assert(carItemById?.due_status == false)

    }
}