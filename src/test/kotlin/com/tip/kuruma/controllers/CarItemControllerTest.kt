package com.tip.kuruma.controllers

import com.tip.kuruma.builders.CarItemBuilder
import com.tip.kuruma.builders.MaintenanceItemBuilder
import com.tip.kuruma.dto.CarItemDTO
import com.tip.kuruma.models.CarItem
import com.tip.kuruma.services.CarItemService
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import java.time.LocalDate

class CarItemControllerTest {

    private val carItemService: CarItemService = mockk()
    private val carItemController = CarItemController(carItemService)

    @BeforeEach
    fun setup() {
        clearAllMocks()
    }

    fun builtCarItem(): CarItem {
        return CarItemBuilder().build()
    }



    @Test
    fun getAllCarItems() {
        val carItem = builtCarItem()

        every { carItemService.getAllCarItems() } returns listOf(carItem)

        val responseEntity = carItemController.getAllCarItems()

        assertTrue(responseEntity.body?.isNotEmpty()!!)
        assertEquals(LocalDate.now(), responseEntity.body?.get(0)?.last_change)
        assertFalse(responseEntity.body?.get(0)?.due_status!!)
        assertEquals(LocalDate.now().plusMonths(6), responseEntity.body?.get(0)?.next_change_due)

        verify(exactly = 1) {
            carItemService.getAllCarItems()
        }

    }

    @Test
    fun createCarItem() {
        val maintenanceItem = MaintenanceItemBuilder().withCode("WATER").withReplacementFrequency(2).build()
        val carItem = CarItemBuilder().withLastChange(LocalDate.now().plusMonths(1)).withMaintenanceItem(maintenanceItem).build()

        every { carItemService.saveCarItem(any()) } returns carItem

        val responseEntity = carItemController.createCarItem(CarItemDTO.fromCarItem(carItem))

        // car item assertions
        assertEquals(HttpStatus.CREATED, responseEntity.statusCode)
        assertFalse(responseEntity.body?.due_status!!)
        assertEquals(LocalDate.now().plusMonths(1), responseEntity.body?.last_change)
        assertEquals("WATER", responseEntity.body?.code)

        verify(exactly = 1) {
            carItemService.saveCarItem(any())
        }

    }

    @Test
    fun getCarItemById() {
        val carItem = builtCarItem()

        every { carItemService.getCarItemById(carItem.id!!) } returns carItem

        // Get an existing car item by id
        val response = carItemController.getCarItemByID(carItem.id!!)


        // Check if the response is not null and has an OK status
        assertEquals(HttpStatus.OK, response.statusCode)

        // Cast the response body to CarItemDTO
        with(response.body!!) {
            // Now you can access the properties of the CarItemDTO
            assertEquals("Oil change", this.name) // from db
            assertEquals(LocalDate.now(), this.last_change, "last change")
            assertFalse(this.due_status)
            assertEquals(LocalDate.now().plusMonths(6), this.next_change_due, "next change due")
        }

        verify(exactly = 1) {
            carItemService.getCarItemById(carItem.id!!)
        }
    }

    @Test
    fun updateCarItem() {
        val carItem =  builtCarItem()

        every { carItemService.updateCarItem(carItem.id!!, any()) } returns carItem

        carItemController.updateCarItem(carItem.id!!, CarItemDTO.fromCarItem(carItem))

        verify(exactly = 1) {
            carItemService.updateCarItem(carItem.id!!, any())
        }
    }

    @Test
    fun deleteCarItem() {
        val carItem =  builtCarItem()
        every { carItemService.deleteCarItem(carItem.id!!) } returns Unit

        // delete carItem by id
        carItemController.deleteCarItem(carItem.id!!)

        verify(exactly = 1) {
            carItemService.deleteCarItem(carItem.id!!)
        }
    }
}