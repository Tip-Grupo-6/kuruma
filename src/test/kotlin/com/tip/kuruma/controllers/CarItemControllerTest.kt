package com.tip.kuruma.controllers

import com.tip.kuruma.EntityNotFoundException
import com.tip.kuruma.dto.CarItemDTO
import com.tip.kuruma.models.CarItem
import com.tip.kuruma.models.MaintenanceItem
import com.tip.kuruma.services.CarItemService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
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
    @Autowired
    private val carItemService: CarItemService? = null

    @Autowired
    private val carItemController: CarItemController? = null


    @Test
    fun getAllCarItems() {
        val carItem = createAnyCarItem()
        carItemController?.createCarItem(CarItemDTO.fromCarItem(carItem))

        // get all carItems
        val carItems = carItemController?.getAllCarItems()

        // assert that the list of car items is not empty
        assert(carItems?.body?.isNotEmpty() == true)
//        assert(carItems?.body?.get(0)?.name == "Oil Change")
        assert(carItems?.body?.get(0)?.last_change == LocalDate.now())
    }

    @Test
    @Transactional
    @Rollback(true)
    fun createCarItem() {
    // create a new car using carItemController.createCarItem
    val carItem = createAnyCarItem()
    val createdCarItem = carItemController?.createCarItem(CarItemDTO.fromCarItem(carItem))

    // car item assertions
    assert(createdCarItem?.statusCode == HttpStatus.CREATED)
//    assert(createdCarItem?.body?.name == "Oil Change")
    assert(createdCarItem?.body?.due_status == false)
    assert(createdCarItem?.body?.last_change == LocalDate.now())
//    assert(createdCarItem?.body?.next_change_due == LocalDate.now().plusMonths(30.toLong()))
    }

    @Test
    @Transactional
    @Rollback(true)
    fun getCarItemById() {
        // create a new car using carItemController.createCarItem
        val carItem = createAnyCarItem()
        val responseEntity = carItemController?.createCarItem(CarItemDTO.fromCarItem(carItem))
        val carItemSaved = responseEntity?.body!!

        // Get an existing car item by id
        val response = carItemController?.getCarItemByID(carItemSaved.id!!)
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



        // get an unexisting car item by id
        assertThrows<EntityNotFoundException> {
            carItemController?.getCarItemByID(10000L)
        }


    }

    @Test
    @Transactional
    @Rollback(true)
    fun updateCarItem() {
        val responseEntity = carItemController?.createCarItem(CarItemDTO.fromCarItem(createAnyCarItem()))
        val carItemSaved = responseEntity?.body!!

        // update car
        val dto = CarItemDTO(
                name = "Another name",
                last_change = LocalDate.now().minusMonths(2)
        )

        // update car using carItemController.updateCarItem
        carItemController?.updateCarItem(carItemSaved.id!!, dto)

        var updatedCarItem = carItemService?.getCarItemById(carItemSaved.id!!)

        // assert updateCarItem new values
        assert(updatedCarItem?.lastChange == LocalDate.now().minusMonths(2))
    }

    @Test
    @Transactional
    @Rollback(true)
    fun deleteCarItem() {
        val response = carItemController?.createCarItem(CarItemDTO.fromCarItem(createAnyCarItem()))
        val carItemSaved = response?.body!!

        // delete car item saved
        val responseEntity: ResponseEntity<Any> = carItemController?.deleteCarItem(carItemSaved.id!!) as ResponseEntity<Any>

        // Check if the response is not null and has an OK status
        assert(responseEntity.statusCode == HttpStatus.NO_CONTENT)

        // Check if the car saved with is deleted
        val carItem = carItemService?.getCarItemById(carItemSaved.id!!)
        assert(carItem?.isDeleted == true)

        assertThrows<EntityNotFoundException> {
            carItemController.deleteCarItem(10000L)
        }
    }


    private fun createAnyCarItem(): CarItem = CarItem(
        lastChange = LocalDate.now(),
        maintenanceItem = MaintenanceItem(
                id = 1,
                code = "OIL",
                description = "Oil Change",
                replacementFrequency = 6
        )
    )
}