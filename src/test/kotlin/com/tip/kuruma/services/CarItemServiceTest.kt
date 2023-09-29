package com.tip.kuruma.services

import com.tip.kuruma.builders.CarItemBuilder
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
        return CarItemBuilder().build()
    }

    @Test
    @Transactional
    @Rollback(true)
    fun getAllCarItems() {
        carItemService?.deleteAllCarItems()
        val carItem = builtCarItem()
        every { carItemRepository.save(carItem) } returns carItem
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
        val carItem =  builtCarItem()
        every { carItemRepository.save(carItem) } returns carItem
        val car = carItemService?.saveCarItem(carItem)

        // assert car info
        assertNotNull(carItem)
        assertEquals(6, car?.maintenanceItem?.replacementFrequency)
        assertEquals(LocalDate.now(), car?.lastChange)
        assertEquals(false, car?.isDeleted)
        assertEquals("OIL", car?.maintenanceItem?.code)

        // verify that carItemRepository.save() is never called
        verify(exactly = 0) {
            carItemRepository.save(carItem)
        }
    }

    @Test
    @Transactional
    @Rollback(true)
    fun getCarById() {
        val carItem =  builtCarItem()
        every { carItemRepository.save(carItem) } returns carItem
        val savedCar = carItemService?.saveCarItem(carItem)

        // get car by id
        val carItemByID = savedCar?.id?.let { carItemService?.getCarItemById(it) }

        // assert carItem info
        assertNotNull(carItemByID)
        assertEquals(6, carItemByID?.maintenanceItem?.replacementFrequency)
        assertEquals(LocalDate.now(), carItemByID?.lastChange)
        assertEquals(false, carItemByID?.isDeleted)
        assertEquals("OIL", carItemByID?.maintenanceItem?.code)

        // Verify that the carItemRepository.save() is never called
        verify(exactly = 0) {
            carItemRepository.save(carItem)
        }

    }

    @Test
    @Transactional
    @Rollback(true)
    fun deleteCarItem() {
        val carItem =  builtCarItem()
        every { carItemRepository.save(carItem) } returns carItem.copy(isDeleted = true)
        val savedCar = carItemService?.saveCarItem(carItem)

        // delete carItem by id
       savedCar?.id?.let { carItemService?.deleteCarItem(it) }

        // get carItem by id
        val carById = savedCar?.id?.let { carItemService?.getCarItemById(it) }

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
        var carItem = builtCarItem()
        every { carItemRepository.save(carItem) } returns carItem.copy(isDeleted = true)
        every { carItemRepository.findAll() } returns listOf()

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
    fun updateCarItem() {/*
        var carItem =  builtCarItem()
        carItemService?.saveCarItem(carItem)
        // update carItem
        var updateCarItem = carItem.copy(
            last_change = LocalDate.now().plusMonths(1),
            isDeleted = true
        )


        // update carItem using carItemService.updateCar
        updateCarItem?.id?.let { carItemService?.updateCarItem(it, updateCarItem) }

        // get carItem by id
        var carItemById = carItem?.id?.let { carItemService?.getCarItemById(it) }

        println("HOLA")
        println(carItemById)

        // assert updateCar new values
        assert(carItemById?.last_change == LocalDate.now().plusMonths(1))
        assert(carItemById?.isDeleted == true)*/
    }
}