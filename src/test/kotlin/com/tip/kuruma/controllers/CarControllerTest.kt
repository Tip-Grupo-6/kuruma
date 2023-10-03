package com.tip.kuruma.controllers

import com.tip.kuruma.EntityNotFoundException
import com.tip.kuruma.builders.CarBuilder
import com.tip.kuruma.dto.CarDTO
import com.tip.kuruma.models.Car
import com.tip.kuruma.services.CarService
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(CarController::class)
class CarControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var carService: CarService

    private fun builtCar(): Car {
        return CarBuilder().build()
    }

    // GET /cars

    @Test
    fun `fetching all cars when there is one of the available `() {
        // Mock the behavior of the carService to return some dummy data
        `when`(carService.getAllCars()).thenReturn(listOf(builtCar()))

        // Perform the GET request to the /cars endpoint and validate the response
        mockMvc.perform(get("/cars")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].id").value(1))
            .andExpect(jsonPath("$[0].brand").value("Honda"))
            .andExpect(jsonPath("$[0].model").value("Civic"))
            .andExpect(jsonPath("$[0].year").value(2023))
    }

    @Test
    fun `fetching all cars when there is none of the available `() {
        // Mock the behavior of the carService to return some dummy data
        `when`(carService.getAllCars()).thenReturn(listOf())

        // Perform the GET request to the /cars endpoint and validate the response
        mockMvc.perform(get("/cars")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$").isEmpty)
    }

    // GET /cars/{id}

    @Test
    fun `fetching a car by id when it exists`() {
        val car = builtCar()
        // Mock the behavior of the carService to return some dummy data
        `when`(carService.getCarById(car.id!!)).thenReturn(car)

        // Perform the GET request to the /cars/{id} endpoint and validate the response
        mockMvc.perform(get("/cars/${car.id!!}")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.brand").value("Honda"))
            .andExpect(jsonPath("$.model").value("Civic"))
            .andExpect(jsonPath("$.year").value(2023))
    }

    @Test
    fun `fetching a car by id when it does not exist`() {
        // Mock the behavior of the carService to return Not Found Car
        `when`(carService.getCarById(1L)).thenThrow(EntityNotFoundException::class.java)

        // Perform the GET request to the /cars/{id} endpoint and validate the response
        mockMvc.perform(get("/cars/1")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound)
    }

    // POST /cars

    @Test
    fun `sending a car body for creation and receiving a successful car response`() {
        val car = CarBuilder()
                .withBrand("Honda")
                .withModel("Civic")
                .withColor("White")
                .withYear(2023)
                .build()


        val carDTO = CarDTO(
                brand = "Honda",
                model = "Civic",
                year = 2023,
                color = "Red",
                image = "car_url"
        )

        println("HOLA")
        println(car)
        println(car.copy(id = 1L))

        // Mock the behavior of the carService to return a valid Car object
        `when`(carService.saveCar(car).thenReturn(car.copy(id = 1L))

        // Perform the POST request to the /cars endpoint and validate the response
        mockMvc.perform(post("/cars")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""{
                "brand": "Honda",
                "model": "Civic",
                "year": 2023,
                "color": "Red"
            }""".trimIndent()))
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.brand").value("Honda"))
            .andExpect(jsonPath("$.model").value("Civic"))
            .andExpect(jsonPath("$.color").value("White"))
            .andExpect(jsonPath("$.year").value(2023))

}
}