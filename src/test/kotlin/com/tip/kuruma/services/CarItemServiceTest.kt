package com.tip.kuruma.services

import com.tip.kuruma.models.CarItem
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Rollback
import org.springframework.transaction.annotation.Transactional
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

    private fun createAndSaveAnyCarItem(): CarItem {
        val carItem = createAnyCarItem()
        return carItemService?.saveCarItem(carItem)!!
    }

    @Test
    @Transactional
    @Rollback(true)
    fun getAllCarItems() {
        createAndSaveAnyCarItem()

        val carItems = carItemService?.getAllCarItems()

        // assert car info
        assert(carItems?.isNotEmpty() == true)
        assert(carItems?.get(0)?.name == "Oil Change")
        assert(carItems?.get(0)?.replacement_frequency == 30)
        assert(carItems?.get(0)?.due_status == false)

    }

    @Test
    @Transactional
    @Rollback(true)
    fun saveCarItem() {
        val carItem =  createAndSaveAnyCarItem()

        // assert car info
        assert(carItem.name == "Oil Change")
        assert(carItem.replacement_frequency == 30)
        assert(carItem.due_status == false)
    }

    @Test
    @Transactional
    @Rollback(true)
    fun getCarById() {
        val carItem =  createAndSaveAnyCarItem()

        // get car by id
        carItem.id?.let { carItemService?.getCarItemById(it) }

        // assert carItem info
        assert(carItem.name == "Oil Change")
        assert(carItem.replacement_frequency == 30)
        assert(carItem.due_status == false)

    }

    @Test
    @Transactional
    @Rollback(true)
    fun deleteCarItem() {
        val carItem =  createAndSaveAnyCarItem()

        // delete carItem by id
        carItem.id?.let { carItemService?.deleteCarItem(it) }

        // get carItem by id
        val carById = carItem.id?.let { carItemService?.getCarItemById(it) }

        // assert carItem info
        assert(carById?.isDeleted == true)

    }

    @Test
    @Transactional
    @Rollback(true)
    fun deleteAllCarItems() {
        createAndSaveAnyCarItem()

        // delete all carItems
        carItemService?.deleteAllCarItems()

        // get all carItems
        val carItems = carItemService?.getAllCarItems()

        // assert carItem info
        assert(carItems?.isEmpty() == true)
    }

    @Test
    @Transactional
    @Rollback(true)
    fun updateCarItem() {
        val carItem =  createAndSaveAnyCarItem()

        // update carItem
        val updateCarItem = carItem.copy(
            name = "Another name",
            last_change = LocalDate.now().plusMonths(1),
            replacement_frequency = 7,
            due_status = false
        )

        // update carItem using carItemService.updateCar
        updateCarItem.id?.let { carItemService?.updateCarItem(it, updateCarItem) }

        // get carItem by id
        val carItemById = carItem.id?.let { carItemService?.getCarItemById(it) }

        // assert updateCar new values
        assert(carItemById?.name == "Another name")
        assert(carItemById?.last_change == LocalDate.now().plusMonths(1))
        assert(carItemById?.replacement_frequency == 7)
        assert(carItemById?.due_status == false)

    }
}