package com.tip.kuruma.controllers.car_data.v1

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate

@RestController
@RequestMapping("/v1/car_data")
class CarDataV1Controller {

    // Define the external API URL
    private val externalApiUrl = "https://carapi.app/api"

    // Create a RestTemplate instance to make HTTP requests
    private val restTemplate = RestTemplate()

    @GetMapping("/makes")
    fun getCarMakes(@RequestParam("year") year: Int): String {
        return try {
            // Make an HTTP GET request to the external API
            val response = restTemplate.getForObject("$externalApiUrl/makes?year=${year.toString()}", String::class.java)

            response?.substringAfter("data\":")?.dropLast(1) ?: // Handle the case where the response is null
            "Failed to fetch car makes data from the external API."
        } catch (ex: Exception) {
            // Handle any exceptions that may occur during the HTTP request
            "An error occurred: ${ex.message}"
        }
    }

    @GetMapping("/models")
    fun getCarModels(@RequestParam("makeId") makeId: Int): String {
        return try {
            // Make an HTTP GET request to the external API
            val response = restTemplate.getForObject("$externalApiUrl/models?year=2020&make_id=${makeId.toString()}", String::class.java)

            response?.substringAfter("data\":")?.dropLast(1) ?: // Handle the case where the response is null
            "Failed to fetch car models data from the external API."
        } catch (ex: Exception) {
            // Handle any exceptions that may occur during the HTTP request
            "An error occurred: ${ex.message}"
        }
    }
}
