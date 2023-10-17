package com.tip.kuruma.client

import com.tip.kuruma.dto.clients.car_api.CarApiListBrandDTO
import com.tip.kuruma.dto.clients.car_api.CarApiListModelDTO
import org.slf4j.LoggerFactory
import org.springframework.web.client.RestTemplate

class CarApiClient: CarDataClientInterface<CarApiListBrandDTO, CarApiListModelDTO> {

    // Define the external API URL
    private val externalApiUrl = "https://carapi.app/api"

    // Create a RestTemplate instance to make HTTP requests
    private val restTemplate = RestTemplate()

    companion object {
        private val LOGGER = LoggerFactory.getLogger(CarApiClient::class.java)
    }

    override fun getCarMakes(year: Int): CarApiListBrandDTO {
        LOGGER.info("Calling to car api to get car makes for year $year")
        try {
            // Make an HTTP GET request to the external API
            return restTemplate.getForObject("$externalApiUrl/makes?year=${year}", CarApiListBrandDTO::class.java)!!
        } catch (ex: Exception) {
            LOGGER.error("An error occurred: ${ex.message}")
            throw RuntimeException("An error occurred: ${ex.message}")
        }
    }

    override fun getCarModel(year: Int, makeId: Int): CarApiListModelDTO {
        LOGGER.info("Calling to car api to get car models for year $year and make_id $makeId")
        try {
            val url = "$externalApiUrl/models?year=$year&make_id=${makeId}"
            // Make an HTTP GET request to the external API
            return restTemplate.getForObject(url, CarApiListModelDTO::class.java)!!
        } catch (ex: Exception) {
            LOGGER.error("An error occurred: ${ex.message}")
            throw RuntimeException("An error occurred: ${ex.message}")
        }
    }
}