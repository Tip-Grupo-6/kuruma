package com.tip.kuruma.services

import com.tip.kuruma.models.CarItem
import com.tip.kuruma.models.MaintenanceItem
import org.junit.jupiter.api.Assertions.*
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
        lastChange = LocalDate.now(),
        maintenanceItem = MaintenanceItem(
            id = 1,
            code = "OIL",
            description = "Oil change",
            replacementFrequency = 30
        )
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
        assertTrue(carItems?.isNotEmpty()!!)
    }

    @Test
    @Transactional
    @Rollback(true)
    fun saveCarItem() {
        val carItem =  createAndSaveAnyCarItem()

        // assert car info
        assertNotNull(carItem)
    }

    @Test
    @Transactional
    @Rollback(true)
    fun getCarById() {
        val carItem =  createAndSaveAnyCarItem()

        // get car by id
        carItem.id?.let { carItemService?.getCarItemById(it) }

        // assert carItem info
        assertNotNull(carItem)

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
        assertTrue(carById?.isDeleted!!)

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
        assertTrue(carItems?.isEmpty()!!)
    }

    @Test
    @Transactional
    @Rollback(true)
    fun updateCarItem() {
        val carItem =  createAndSaveAnyCarItem()

        // update carItem
        val updateCarItem = carItem.copy(
            lastChange = LocalDate.now().plusMonths(1),
        )

        // update carItem using carItemService.updateCar
        updateCarItem.id?.let { carItemService?.updateCarItem(it, updateCarItem) }

        // get carItem by id
        val carItemById = carItem.id?.let { carItemService?.getCarItemById(it) }

        // assert updateCar new values
        assertEquals(LocalDate.now().plusMonths(1), carItemById?.lastChange)
    }
}