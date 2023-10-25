package com.tip.kuruma.client

import com.tip.kuruma.dto.clients.san_cristobal.CarMakeSanCristobalDTO
import com.tip.kuruma.dto.clients.san_cristobal.CarModelDetailSanCristobalDTO
import com.tip.kuruma.dto.clients.san_cristobal.CarModelSanCristobalDTO
import org.slf4j.LoggerFactory
import org.springframework.web.client.RestTemplate

class SanCristobalClient: CarDataClientInterface<CarMakeSanCristobalDTO, CarModelSanCristobalDTO> {

    private val restTemplate = RestTemplate()
    private val externalApiUrl = "https://api.sancristobal.com.ar/marketing-marketing/api/InfoAuto"

    companion object {
        private val LOGGER = LoggerFactory.getLogger(SanCristobalClient::class.java)
    }

    override fun getCarMakes(year: Int): CarMakeSanCristobalDTO {
        LOGGER.info("Calling to san cristobal api to get car makes for year $year")
        try {
            // Make an HTTP GET request to the external API
            return restTemplate.getForObject("$externalApiUrl/brands-highlight-by-year-and-portal-category?year=${year}&portalCategory=A", CarMakeSanCristobalDTO::class.java)!!
        } catch (ex: Exception) {
            LOGGER.error("An error occurred: ${ex.message}")
            throw RuntimeException("An error occurred: ${ex.message}")
        }
    }

    override fun getCarModel(year: Int, makeId: Int): CarModelSanCristobalDTO {
        LOGGER.info("Calling to san cristobal api to get car models for year $year and make_id $makeId")
        try {
            val url = "$externalApiUrl/model-by-brand-year-and-portal-category?year=${year}&portalCategory=A&brandId=${makeId}"
            // Make an HTTP GET request to the external API
            return restTemplate.getForObject(url, CarModelSanCristobalDTO::class.java)!!
        } catch (ex: Exception) {
            LOGGER.error("An error occurred: ${ex.message}")
            throw RuntimeException("An error occurred: ${ex.message}")
        }
    }

    override fun getCarModelDetails(year: Int, makeId: Int, modelId: Int): CarModelDetailSanCristobalDTO {
        LOGGER.info("Calling to san cristobal api to get car model details for year $year, make_id $makeId and model_id $modelId")
        try {
            val url = "$externalApiUrl/versions-by-brand-model-year-and-portal-category?year=${year}&portalCategory=A&brandId=${makeId}&modelId=${modelId}&portalCategory=A"
            // Make an HTTP GET request to the external API
            return restTemplate.getForObject(url, CarModelDetailSanCristobalDTO::class.java)!!
        } catch (ex: Exception) {
            LOGGER.error("An error occurred: ${ex.message}")
            throw RuntimeException("An error occurred: ${ex.message}")
        }
    }
}