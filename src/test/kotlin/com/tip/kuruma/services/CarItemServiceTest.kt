package com.tip.kuruma.services

import com.tip.kuruma.*
import com.tip.kuruma.builders.CarItemBuilder
import com.tip.kuruma.builders.MaintenanceItemBuilder
import com.tip.kuruma.models.CarItem
import com.tip.kuruma.models.MaintenanceItem
import com.tip.kuruma.repositories.CarItemRepository
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.util.*

class CarItemServiceTest {

    private val carItemRepository: CarItemRepository = mockk()
    private val maintenanceService: MaintenanceService = mockk()
    private val carItemService = CarItemService(
            carItemRepository, maintenanceService
    )

    @BeforeEach
    fun setup() {
        clearAllMocks()
    }

    private fun builtCarItem(): CarItem {
        return CarItemBuilder().build()
    }

    @Test
    fun `get all car items should no throw exception`() {
        val carItem = builtCarItem()
        var carItems: List<CarItem> = listOf()

        GIVEN {
            every { carItemRepository.findAll() } returns listOf(carItem)
        }

        EXPECT_NOT_THROW {
            carItems = carItemService.getAllCarItems()
        }

        AND {
            assertTrue(carItems.isNotEmpty())
            with(carItems[0]) {
                assertEquals(6, this.maintenanceItem?.replacementFrequency)
                assertEquals(LocalDate.now(), this.lastChange)
                assertEquals(false, this.isDeleted)
                assertEquals("OIL", this.maintenanceItem?.code)
            }
        }

        AND {
            verify(exactly = 1) {
                carItemRepository.findAll()
            }
        }
    }

    @Test
    fun saveCarItem() {
        val maintenanceItemFromDB = MaintenanceItem(code = "WATER")
        val maintenanceItem = MaintenanceItemBuilder().withCode("WATER").withReplacementFrequency(2).build()
        val carItem = CarItemBuilder().withLastChange(LocalDate.now().plusMonths(1)).withMaintenanceItem(maintenanceItem).build()

        every { maintenanceService.findByCode(any()) } returns maintenanceItemFromDB
        every { carItemRepository.save(carItem.copy(maintenanceItem = maintenanceItemFromDB)) } returns carItem

        // save carItem
        val savedCarItem = carItemService.saveCarItem(carItem)

        // assert car info
        assertNotNull(savedCarItem)
        assertEquals(LocalDate.now().plusMonths(1), savedCarItem.lastChange)
        assertEquals(false, savedCarItem.isDeleted)
        assertEquals("WATER", savedCarItem.maintenanceItem?.code)

        verify(exactly = 1) {
            maintenanceService.findByCode(any())
            carItemRepository.save(carItem.copy(maintenanceItem = maintenanceItemFromDB))
        }
    }

    @Test
    fun `get car item by id should no throw exception`() {
        val carItem = builtCarItem()
        var carItemSaved: CarItem? = null

        GIVEN {
            every { carItemRepository.findById(carItem.id!!) } returns Optional.of(carItem)
        }

        EXPECT_NOT_THROW {
            carItemSaved = carItemService.getCarItemById(carItem.id!!)
        }

        AND {
            with(carItemSaved!!) {
                assertEquals(LocalDate.now(), this.lastChange)
                assertEquals(false, this.isDeleted)
                assertEquals(6, this.maintenanceItem?.replacementFrequency)
                assertEquals("OIL", this.maintenanceItem?.code)
            }
        }

        verify(exactly = 1) {
            carItemRepository.findById(carItem.id!!)
        }
    }

    @Test
    fun `get car item should throw EntityNotFoundException when is not found on db`() {
        val anyCarItemId = 1L

        GIVEN {
            every { carItemRepository.findById(anyCarItemId) } returns Optional.empty()
        }

        EXPECT_TO_THROW<EntityNotFoundException> {
            carItemService.getCarItemById(anyCarItemId)
        }
        AND {
            verify(exactly = 1) {
                carItemRepository.findById(anyCarItemId)
            }
        }
    }

    @Test
    fun `update car should no throw exception`() {
        val anyCarItemId = 1L
        val carItemFromDB =  builtCarItem()
        val updateCarItem = carItemFromDB.copy(
                lastChange = LocalDate.now().plusMonths(5)
        )

        GIVEN {
            every { carItemRepository.findById(anyCarItemId) } returns Optional.of(carItemFromDB)
            every { carItemRepository.save(updateCarItem.copy(lastChange = updateCarItem.lastChange)) } returns updateCarItem.copy(lastChange = updateCarItem.lastChange)
        }

        EXPECT_NOT_THROW {
            carItemService.updateCarItem(anyCarItemId, updateCarItem)
        }

        AND {
            verify(exactly = 1) {
                carItemRepository.findById(anyCarItemId)
                carItemRepository.save(withArg {
                    assertEquals(updateCarItem.lastChange, it.lastChange)
                })
            }
        }
    }

    @Test
    fun `when try update car not existent should throw exception and not update`() {
        val anyCarItemId = 1L
        val anyCarItem = CarItem()

        GIVEN {
            every { carItemRepository.findById(anyCarItemId) } returns Optional.empty()
        }

        EXPECT_TO_THROW<EntityNotFoundException> {
            carItemService.updateCarItem(anyCarItemId, anyCarItem)
        }

        AND {
            verify(exactly = 1) {
                carItemRepository.findById(anyCarItemId)
            }
            verify(exactly = 0) {
                carItemRepository.save(any())
            }
        }
    }

    @Test
    fun `delete car item should no throw exception`() {
        val anyCarItemId = 1L
        val carItem =  builtCarItem().copy(isDeleted = false)

        every { carItemRepository.findById(anyCarItemId) } returns Optional.of(carItem)
        every { carItemRepository.save(any()) } returns carItem.copy(isDeleted = true)

        EXPECT_NOT_THROW {
            carItemService.deleteCarItem(anyCarItemId)
        }

        AND {
            verify(exactly = 1) {
                carItemRepository.findById(anyCarItemId)
                carItemRepository.save(withArg {
                    assertTrue(it.isDeleted!!)
                })
            }
        }
    }

    @Test
    fun `delete car item should throw exception when car not exists`() {
        val anyCarItemId = 1L

        every { carItemRepository.findById(anyCarItemId) } returns Optional.empty()

        EXPECT_TO_THROW<EntityNotFoundException> {
            carItemService.deleteCarItem(anyCarItemId)
        }

        AND {
            verify(exactly = 1) {
                carItemRepository.findById(anyCarItemId)
            }
            verify(exactly = 0) {
                carItemRepository.save(any())
            }
        }
    }

    @Test
    fun `delete all car items should no throw exception`() {
        GIVEN {
            every { carItemRepository.deleteAll() } returns Unit
        }

        EXPECT_NOT_THROW {
            carItemService.deleteAllCarItems()
        }

        AND {
            verify(exactly = 1) {
                carItemRepository.deleteAll()
            }
        }
    }

    @Test
    fun `update a car items of car id should do logical delete for car items before, save new ones and no throw exception`() {
        val anyCarId = 1L
        val newCarItems = listOf(CarItem(maintenanceItem = MaintenanceItem(code = "OIL"), lastChange = LocalDate.now()))
        val carItemFromDB = listOf(CarItem(maintenanceItem = MaintenanceItem(code = "OIL"), lastChange = LocalDate.now().minusMonths(1)))
        val maintenanceItemFromDB = MaintenanceItem(code = "OIL")

        GIVEN {
            every { carItemRepository.findByCarId(anyCarId) } returns carItemFromDB
            every { carItemRepository.save(any()) } returns CarItem() //cualquier car item, no necesitamos la respuesta
            every { maintenanceService.findByCode(any()) } returns maintenanceItemFromDB
            every { carItemRepository.save(any()) } returns CarItem() //cualquier car item, no necesitamos la respuesta
        }

        EXPECT_NOT_THROW {
            carItemService.updateCarItems(anyCarId, newCarItems)
        }

        AND {
            verify(exactly = 1) {
                carItemRepository.findByCarId(anyCarId)
                carItemRepository.save(withArg {
                    assertTrue(it.isDeleted!!)
                })
                maintenanceService.findByCode(any())
                carItemRepository.save(withArg {
                    assertEquals(anyCarId, it.carId)
                })
            }
        }
    }

    @Test
    fun `update a car items of car id should do not logical delete if has not car items in db and save new ones and no throw exception`() {
        val anyCarId = 1L
        val newCarItems = listOf(CarItem(maintenanceItem = MaintenanceItem(code = "OIL"), lastChange = LocalDate.now()))
        val maintenanceItemFromDB = MaintenanceItem(code = "OIL")

        GIVEN {
            every { carItemRepository.findByCarId(anyCarId) } returns listOf()
            every { maintenanceService.findByCode(any()) } returns maintenanceItemFromDB
            every { carItemRepository.save(any()) } returns CarItem() //cualquier car item, no necesitamos la respuesta
        }

        EXPECT_NOT_THROW {
            carItemService.updateCarItems(anyCarId, newCarItems)
        }

        AND {
            verify(exactly = 1) {
                carItemRepository.findByCarId(anyCarId)
                maintenanceService.findByCode(any())
                carItemRepository.save(withArg {
                    assertEquals(anyCarId, it.carId)
                })
            }
            verify(exactly = 0) {
                carItemRepository.save(withArg {
                    assertTrue(it.isDeleted!!)
                })
            }
        }
    }
}