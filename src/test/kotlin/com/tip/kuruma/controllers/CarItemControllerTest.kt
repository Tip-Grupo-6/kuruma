package com.tip.kuruma.controllers

import com.tip.kuruma.EntityNotFoundException
import com.tip.kuruma.builders.CarItemBuilder
import com.tip.kuruma.builders.MaintenanceItemBuilder
import com.tip.kuruma.dto.CarItemDTO
import com.tip.kuruma.models.CarItem
import com.tip.kuruma.models.MaintenanceItem
import com.tip.kuruma.services.CarItemService
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.test.annotation.Rollback
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@SpringBootTest
class CarItemControllerTest {
    private val carItemService: CarItemService = mockk()

    @Autowired
    private val carItemController: CarItemController? = null

    @BeforeEach
    fun setup() {
        clearAllMocks()
    }

    fun builtCarItem(): CarItem {
        val carItem = CarItemBuilder().build()
        every { carItemService.saveCarItem(carItem) } returns carItem
        val savedCarItem = carItemController?.createCarItem(CarItemDTO.fromCarItem(carItem))
        return savedCarItem?.body!!.toCarItem()
    }



    @Test
    fun getAllCarItems() {
        val carItem = builtCarItem()

        every { carItemService.getAllCarItems() } returns listOf(carItem)

        val carItems = carItemController?.getAllCarItems()

        assert(carItems?.body?.isNotEmpty() == true)
        assert(carItems?.body?.get(0)?.last_change == LocalDate.now())
        assert(carItems?.body?.get(0)?.due_status == false)
        assert(carItems?.body?.get(0)?.next_change_due == LocalDate.now().plusMonths(6))

        // Verify that carItemService.getAllCarItems() is never called
        verify(exactly = 0) {
            carItemService.getAllCarItems()
        }

    }

    @Test
    @Transactional
    @Rollback(true)
    fun createCarItem() {
    val maintenanceItem = MaintenanceItemBuilder().withCode("WATER").withReplacementFrequency(2).build()
    val carItem = CarItemBuilder().withLastChange(LocalDate.now().plusMonths(1)).withMaintenanceItem(maintenanceItem).build()

    every { carItemService.saveCarItem(carItem) } returns carItem

    val createdCarItem = carItemController?.createCarItem(CarItemDTO.fromCarItem(carItem)) as ResponseEntity<CarItemDTO>

    // car item assertions
    assert(createdCarItem.statusCode == HttpStatus.CREATED)
    assert(createdCarItem.body?.due_status == false)
    assert(createdCarItem.body?.last_change == LocalDate.now().plusMonths(1))
    assert(createdCarItem.body?.code == "WATER")

    // Verify that carItemService.saveCarItem(carItem) is never called
    verify(exactly = 0) {
        carItemService.saveCarItem(carItem)
    }

    }

    @Test
    @Transactional
    @Rollback(true)
    fun getCarItemById() {
        val carItem = builtCarItem()

        every { carItemService.getCarItemById(carItem.id!!) } returns carItem

        // Get an existing car item by id
        val response = carItemController?.getCarItemByID(carItem.id!!)


        // Check if the response is not null and has an OK status
        assertEquals(response?.statusCode, HttpStatus.OK)

        // Cast the response body to CarItemDTO
        with(response?.body!!) {
            // Now you can access the properties of the CarItemDTO
            assertEquals("Aceite", this.name) // from db
            assertEquals(LocalDate.now(), this.last_change, "last change")
            assertFalse(this.due_status)
            assertEquals(LocalDate.now().plusMonths(6), this.next_change_due, "next change due")
        }

        // verify that carItemService.getCarItemById(carItem.id!!) is never called
        verify(exactly = 0) {
            carItemService.getCarItemById(carItem.id!!)
        }



        // get an unexisting car item by id
        assertThrows<EntityNotFoundException> {
            carItemController?.getCarItemByID(10000L)
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

        every { carItemService.saveCarItem(updateCarItem) } returns updateCarItem

        updateCarItem.id?.let { carItemController?.updateCarItem(it, CarItemDTO.fromCarItem(updateCarItem)) }

        // get carItem by id
        var carItemById = carItem.id?.let { carItemController?.getCarItemByID(it) }

        // assert updateCar new values
        assert(carItemById?.body?.last_change == LocalDate.now().plusMonths(5))
    }

    @Test
    @Transactional
    @Rollback(true)
    fun deleteCarItem() {
        val carItem =  builtCarItem()
        every { carItemService.saveCarItem(carItem) } returns carItem.copy(isDeleted = true)

        // delete carItem by id
        carItem.id?.let { carItemController?.deleteCarItem(it) }

        // get carItem by id
        val carById = carItem.id?.let { carItemController?.getCarItemByID(it) }

        // assert carItem info
        Assertions.assertTrue(carById?.body?.is_deleted!!)

        verify(exactly = 0) {
            carItemService.saveCarItem(carItem)
        }

        assertThrows<EntityNotFoundException> {
            carItemController?.deleteCarItem(10000L)
        }
    }
}