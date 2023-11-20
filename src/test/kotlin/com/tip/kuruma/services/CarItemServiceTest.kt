package com.tip.kuruma.services

import com.tip.kuruma.*
import com.tip.kuruma.builders.CarItemBuilder
import com.tip.kuruma.builders.MaintenanceItemBuilder
import com.tip.kuruma.models.Car
import com.tip.kuruma.models.CarItem
import com.tip.kuruma.models.MaintenanceItem
import com.tip.kuruma.repositories.CarItemRepository
import com.tip.kuruma.repositories.CarRepository
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
    private lateinit var carItemService: CarItemService

    @Autowired
    private lateinit var carRepository: CarRepository

    @Autowired
    private lateinit var carItemRepository: CarItemRepository

    @Test
    @Transactional
    @Rollback
    fun `get all car items should no throw exception`() {
        var carItems: List<CarItem>? = null

        EXPECT_NOT_THROW {
            carItems = carItemService.getAllCarItems()
        }

        AND {
            assertTrue(carItems!!.isEmpty())
        }
    }


    @Test
    @Transactional
    @Rollback
    fun `when create a car, this should be returned with id`() {
        val maintenanceItem = MaintenanceItemBuilder().withCode("WATER").withReplacementFrequency(2).build()
        val carItem = CarItemBuilder().withLastChange(LocalDate.now().plusMonths(1)).withMaintenanceItem(maintenanceItem).build()

        // save carItem
        val savedCarItem = carItemService.saveCarItem(carItem)

        // assert car info
        assertNotNull(savedCarItem)
        assertNotNull(savedCarItem.id)
        assertEquals(LocalDate.now().plusMonths(1), savedCarItem.lastChange)
        assertEquals(false, savedCarItem.isDeleted)
        assertEquals("WATER", savedCarItem.maintenanceItem?.code)
    }


    @Test
    @Transactional
    @Rollback
    fun `get car item by id should return a car previous saved with his data and no throw exception`() {
        val carItem = CarItem(lastChange = LocalDate.now(), maintenanceItem = MaintenanceItem(code = "OIL"))
        var carId = 0L
        var carItemFromDB: CarItem? = null

        GIVEN {
            val savedCarItem = carItemService.saveCarItem(carItem)
            carId = savedCarItem.id!!
        }

        EXPECT_NOT_THROW {
            carItemFromDB = carItemService.getCarItemById(carId)
        }

        AND {
            with(carItemFromDB!!) {
                assertEquals(carItem.lastChange, this.lastChange)
                assertEquals(carItem.maintenanceItem?.code, this.maintenanceItem?.code)
                assertNotNull(this.maintenanceItem?.replacementFrequency)
                assertFalse(this.isDeleted!!)
            }
        }

    }


    @Test
    fun `get car item should throw EntityNotFoundException when is not found on db`() {
        val carItemIdNotFound = 99999999L

        EXPECT_TO_THROW<EntityNotFoundException> {
            carItemService.getCarItemById(carItemIdNotFound)
        }
    }


    @Test
    @Transactional
    @Rollback
    fun `update car should impact on db and no throw exception`() {
        val carItem = CarItem(lastChange = LocalDate.now().minusMonths(1), maintenanceItem = MaintenanceItem(code = "OIL"))
        var carId = 0L
        val updateCarItem = carItem.copy(
                lastChange = LocalDate.now().plusMonths(5)
        )

        GIVEN {
            val savedCarItem = carItemService.saveCarItem(carItem)
            carId = savedCarItem.id!!
        }

        EXPECT_NOT_THROW {
            carItemService.updateCarItem(carId, updateCarItem)
        }

        AND {
            val carFromDB = carItemService.getCarItemById(carId)
            assertEquals(updateCarItem.lastChange, carFromDB.lastChange)
        }
    }

    @Test
    fun `when try update car not existent should throw exception`() {
        val anyCarItemIdNotFound = 999999L
        val anyCarItem = CarItem()

        EXPECT_TO_THROW<EntityNotFoundException> {
            carItemService.updateCarItem(anyCarItemIdNotFound, anyCarItem)
        }
    }


    @Test
    @Transactional
    @Rollback
    fun `delete car item should do logic delete and no throw exception`() {
        val carItem = CarItem(lastChange = LocalDate.now(), maintenanceItem = MaintenanceItem(code = "OIL"))
        var carId = 0L

        GIVEN {
            val savedCarItem = carItemService.saveCarItem(carItem)
            carId = savedCarItem.id!!
        }

        EXPECT_NOT_THROW {
            carItemService.deleteCarItem(carId)
        }

        AND {
            val carFromDB = carItemService.getCarItemById(carId)
            assertTrue(carFromDB.isDeleted!!)
        }
    }

    @Test
    fun `delete car item should throw exception when car not exists`() {
        val anyCarItemIdNotFound = 99999L

        EXPECT_TO_THROW<EntityNotFoundException> {
            carItemService.deleteCarItem(anyCarItemIdNotFound)
        }
    }

    @Test
    fun `delete all car items should no throw exception`() {
        val carItem = CarItem(lastChange = LocalDate.now())

        GIVEN {
            carItemService.saveCarItem(carItem.copy(maintenanceItem = MaintenanceItem(code = "OIL")))
            carItemService.saveCarItem(carItem.copy(maintenanceItem = MaintenanceItem(code = "WATER")))
            assertEquals(carItemService.getAllCarItems().size, 2)
        }

        EXPECT_NOT_THROW {
            carItemService.deleteAllCarItems()
        }

        AND {
            assertTrue(carItemService.getAllCarItems().isEmpty())
        }
    }

    @Test
    @Transactional
    @Rollback
    fun `update a car items of car id should do logical delete for car items before, save new ones and no throw exception`() {
        var carSavedId: Long? = null
        val carItem = CarItem(lastChange = LocalDate.now().minusMonths(1), maintenanceItem = MaintenanceItem(code = "OIL"))
        val newCarItems = listOf(carItem.copy(lastChange = LocalDate.now()))

        GIVEN {
            val car = carRepository.save(Car(userId = 1L, brand = "Test"))
            carSavedId = car.id
            carItemService.saveCarItem(carItem.copy(carId = carSavedId))
        }

        EXPECT_NOT_THROW {
            carItemService.updateCarItems(carSavedId!!, newCarItems)
        }

        AND("should have car items with code 'OIL' and one should be deleted and another not") {
            val carItems = carItemRepository.findByCarId(carSavedId!!)
            assertEquals(2, carItems.size)
            assertNotNull(carItems.find { it.maintenanceItem?.code == "OIL" && it.isDeleted!! })
            assertNotNull(carItems.find { it.maintenanceItem?.code == "OIL" && !it.isDeleted!! })
        }
    }
}