package com.tip.kuruma.services

import com.tip.kuruma.builders.CarItemBuilder
import com.tip.kuruma.builders.MaintenanceItemBuilder
import com.tip.kuruma.models.CarItem
import com.tip.kuruma.repositories.CarItemRepository
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Rollback
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.util.*

@SpringBootTest
class CarItemServiceTest {
    private val carItemRepository: CarItemRepository  = mockk()
    @Autowired
    private val carItemService: CarItemService? = null

    @BeforeEach
    fun setup() {
        clearAllMocks()
    }

    private fun builtCarItem(): CarItem {
        var  carItem = CarItemBuilder().build()
        every { carItemRepository.save(carItem) } returns carItem
        val savedCar = carItemService?.saveCarItem(carItem)
        return savedCar!!
    }

    @Test
    @Transactional
    @Rollback(true)
    fun getAllCarItems() {
        val carItem = builtCarItem()

        every { carItemRepository.findAll() } returns listOf(carItem)

        carItemService?.saveCarItem(carItem)

        val carItems = carItemService?.getAllCarItems()

        // assert car info
        assertTrue(carItems?.isNotEmpty()!!)
        assertEquals(6, carItems[0].maintenanceItem?.replacementFrequency)
        assertEquals(LocalDate.now(), carItems[0].lastChange)
        assertEquals(false, carItems[0].isDeleted)
        assertEquals("OIL", carItems[0].maintenanceItem?.code)

        // Verify that the carItemRepository.findAll() is never called
        verify(exactly = 0) {
            carItemRepository.findAll()
        }
    }

    @Test
    @Transactional
    @Rollback(true)
    fun saveCarItem() {
        val maintenanceItem = MaintenanceItemBuilder().withCode("WATER").withReplacementFrequency(2).build()
        val carItem = CarItemBuilder().withLastChange(LocalDate.now().plusMonths(1)).withMaintenanceItem(maintenanceItem).build()
        every { carItemRepository.save(carItem) } returns carItem

        // assert car info
        assertNotNull(carItem)
        assertEquals(LocalDate.now().plusMonths(1), carItem.lastChange)
        assertEquals(false, carItem.isDeleted)
        assertEquals(2, carItem.maintenanceItem?.replacementFrequency)
        assertEquals("WATER", carItem.maintenanceItem?.code)

        // verify that carItemRepository.save() is never called
        verify(exactly = 0) {
            carItemRepository.save(carItem)
        }
    }

    @Test
    @Transactional
    @Rollback(true)
    fun getCarItemById() {
        val carItem = builtCarItem()

        every { carItemRepository.findById(carItem.id!!) } returns Optional.of(carItem)

        // get car by id
        val carItemSaved = carItem.id?.let { carItemService?.getCarItemById(it) }

        // assert carItem info
        assertEquals(LocalDate.now(), carItemSaved?.lastChange)
        assertEquals(false, carItemSaved?.isDeleted)
        assertEquals(6, carItemSaved?.maintenanceItem?.replacementFrequency)
        assertEquals("OIL", carItemSaved?.maintenanceItem?.code)

        // Verify that the carItemRepository.save() is never called
        verify(exactly = 0) {
            carItemRepository.save(carItem)
        }

    }

    @Test
    @Transactional
    @Rollback(true)
    fun updateCarItem() {
        var carItem =  builtCarItem()

        // update carItem
        var updateCarItem = carItem.copy(
                lastChange = LocalDate.now().plusMonths(5)
        )

        every { carItemRepository.save(updateCarItem) } returns updateCarItem

        // update carItem using carItemService.updateCar
        updateCarItem.id?.let { carItemService?.updateCarItem(it, updateCarItem) }

        // get carItem by id
        var carItemById = carItem.id?.let { carItemService?.getCarItemById(it) }

        // assert updateCar new values
        assert(carItemById?.lastChange == LocalDate.now().plusMonths(5))
    }

    @Test
    @Transactional
    @Rollback(true)
    fun deleteCarItem() {
        val carItem =  builtCarItem()
        every { carItemRepository.save(carItem) } returns carItem.copy(isDeleted = true)

        // delete carItem by id
       carItem.id?.let { carItemService?.deleteCarItem(it) }

        // get carItem by id
        val carById = carItem.id?.let { carItemService?.getCarItemById(it) }

        // assert carItem info
        assertTrue(carById?.isDeleted!!)

        // verify that carItemRepository.save() is never called
        verify(exactly = 0) {
            carItemRepository.save(carItem)
        }

    }

    @Test
    @Transactional
    @Rollback(true)
    fun deleteAllCarItems() {
        builtCarItem()

        // delete all carItems
        carItemService?.deleteAllCarItems()

        // get all carItems
        val carItems = carItemService?.getAllCarItems()

        // assert carItem info
        assertTrue(carItems?.isEmpty()!!)
    }
}