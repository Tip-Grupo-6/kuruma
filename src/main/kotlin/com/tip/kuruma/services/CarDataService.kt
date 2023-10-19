package com.tip.kuruma.services

import com.tip.kuruma.client.CarDataClientInterface
import com.tip.kuruma.dto.car_data.CarMakeDTO
import com.tip.kuruma.dto.car_data.CarModelDTO
import com.tip.kuruma.mapper.CarDataMapperInterface

class CarDataService<T, S>(
        private val client: CarDataClientInterface<T, S>,
        private val mapper: CarDataMapperInterface<T, S>
) {

    fun getCarMakes(year: Int): List<CarMakeDTO>? {
        val carMakes = client.getCarMakes(year)
        return mapper.mapToCarMakeDTO(carMakes)
    }

    fun getCarModel(year: Int, makeId: Int): List<CarModelDTO>? {
        val carMakes = client.getCarModel(year, makeId)
        return mapper.mapToCarModelDTO(carMakes)
    }
}