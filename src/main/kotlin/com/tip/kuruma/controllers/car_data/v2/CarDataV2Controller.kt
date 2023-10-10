package com.tip.kuruma.controllers.car_data.v2

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate

@RestController
@RequestMapping("/v2/car_data")
class CarDataV2Controller {

    // Define the external API URL
    private val externalApiUrl = "https://api.sancristobal.com.ar/marketing-marketing/api/InfoAuto"

    // Create a RestTemplate instance to make HTTP requests
    private val restTemplate = RestTemplate()

    @GetMapping("/makes")
    fun getCarMakes(@RequestParam("year") year: Int): String {
        return try {
            // Make an HTTP GET request to the external API
            val response = restTemplate.getForObject("$externalApiUrl/brands-highlight-by-year-and-portal-category?year=${year.toString()}&portalCategory=A", String::class.java)

            response?.substringAfter("brands\":")?.dropLast(1) ?: "Failed to fetch car makes data from the external API."
        } catch (ex: Exception) {
            // Handle any exceptions that may occur during the HTTP request
            "An error occurred: ${ex.message}"
        }
    }

    @GetMapping("/models")
    fun getCarModels(@RequestParam("makeId") makeId: Int, @RequestParam("year") year: Int): String {

        return try {
            // Make an HTTP GET request to the external API
            val response = restTemplate.getForObject("$externalApiUrl/model-by-brand-year-and-portal-category?year=${year.toString()}&portalCategory=A&brandId=${makeId.toString()}", String::class.java)

            response?.substringAfter("models\":")?.dropLast(1) ?: // Handle the case where the response is null
            "Failed to fetch car models data from the external API."
        } catch (ex: Exception) {
            // Handle any exceptions that may occur during the HTTP request
            "An error occurred: ${ex.message}"
        }
    }
}
