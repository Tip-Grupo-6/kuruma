package com.tip.kuruma.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.tip.kuruma.EntityNotFoundException
import com.tip.kuruma.builders.CarItemBuilder
import com.tip.kuruma.dto.CarItemDTO
import com.tip.kuruma.models.CarItem
import com.tip.kuruma.services.CarItemService
import com.tip.kuruma.services.MaintenanceService
import io.vertx.core.json.jackson.DatabindCodec.mapper
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.LocalDate


@WebMvcTest(CarItemController::class)
class CarItemControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var carItemService: CarItemService
    @MockBean
    private lateinit var maintenanceService: MaintenanceService

    fun builtCarItem(): CarItem {
        return CarItemBuilder().build()
    }

    // GET /car_items

    @Test
    fun `fetching all carItems when there is one of the available `() {
        // Mock the behavior of the carItemService to return some dummy data
        `when`(carItemService.getAllCarItems()).thenReturn(listOf(builtCarItem()))

        // Perform the GET request to the /car_items endpoint and validate the response
        mockMvc.perform(get("/car_items")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].id").value(1))
            .andExpect(jsonPath("$[0].last_change").value(LocalDate.now().toString()))
            .andExpect(jsonPath("$[0].code").value("OIL"))
            .andExpect(jsonPath("$[0].name").value("Oil change"))
            .andExpect(jsonPath("$[0].replacement_frequency").value(6))

    }

    @Test
    fun `fetching all carItems when there is none of the available `() {
        // Mock the behavior of the carItemService to return some dummy data
        `when`(carItemService.getAllCarItems()).thenReturn(listOf())

        // Perform the GET request to the /car_items endpoint and validate the response
        mockMvc.perform(get("/car_items")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$").isEmpty)
    }

    // GET /car_items/{id}

    @Test
    fun `fetching a carItem by id when it exists`() {
        val carItem = builtCarItem()
        // Mock the behavior of the carItemService to return some dummy data
        `when`(carItemService.getCarItemById(1)).thenReturn(carItem)

        // Perform the GET request to the /carItems endpoint and validate the response
        mockMvc.perform(get("/car_items/1")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.last_change").value(LocalDate.now().toString()))
            .andExpect(jsonPath("$.code").value("OIL"))
            .andExpect(jsonPath("$.name").value("Oil change"))
            .andExpect(jsonPath("$.replacement_frequency").value(6))
    }

    @Test
    fun `fetching a carItem by id when it does not exist`() {
        // Mock the behavior of the carItemService to return some dummy data
        `when`(carItemService.getCarItemById(1)).thenThrow(EntityNotFoundException::class.java)

        // Perform the GET request to the /carItems endpoint and validate the response
        mockMvc.perform(get("/car_items/1")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound)
    }

    // POST /car_items



    @Test
    fun `sending a carItem body for creation and receiving a successful carItem response` () {
        val carItem = builtCarItem()
        val carItemDTO = CarItemDTO(
                car_id = 1,
                code = "OIL",
                name = "Oil change",
                replacement_frequency = 6,
                last_change = LocalDate.of(2021, 1, 1)
        )
        // Mock the behavior of the carItemService to return some dummy data
        `when`(carItemService.saveCarItem(carItemDTO.toCarItem())).thenReturn(carItem)

        // Perform the POST request to the /carItems endpoint and validate the response
        mockMvc.perform(post("/car_items")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(carItemDTO)))
                .andExpect(status().isCreated)
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.last_change").value(LocalDate.now().toString()))
                .andExpect(jsonPath("$.code").value("OIL"))
                .andExpect(jsonPath("$.name").value("Oil change"))
                .andExpect(jsonPath("$.replacement_frequency").value(6))
    }


    // DELETE /car_items/{id}

    @Test
    fun `deleting a carItem by id when it exists`() {
        val carItem = builtCarItem()
        // Mock the behavior of the carItemService to return some dummy data
        `when`(carItemService.getCarItemById(carItem.id!!)).thenReturn(carItem)

        // Perform the DELETE request to the /car_items/{id} endpoint and validate the response
        mockMvc.perform(MockMvcRequestBuilders.delete("/car_items/${carItem.id!!}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent)
    }

    @Test
    fun `deleting a carItem by id when it does not exist`() {
        // Mock the behavior of the carItemService to return Not Found CarItem
        `when`(carItemService.deleteCarItem(1L)).thenThrow(EntityNotFoundException::class.java)

        // Perform the DELETE request to the /car_items/{id} endpoint and validate the response
        mockMvc.perform(MockMvcRequestBuilders.delete("/car_items/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound)
    }

    private fun toJson(carItemDTO: CarItemDTO): String {
        val objectMapper = ObjectMapper()
        objectMapper.registerModule(JavaTimeModule())
        return objectMapper.writeValueAsString(carItemDTO)
    }

}