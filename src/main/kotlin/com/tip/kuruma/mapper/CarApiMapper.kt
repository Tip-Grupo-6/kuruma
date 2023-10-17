package com.tip.kuruma.mapper

import com.tip.kuruma.dto.car_data.CarMakeDTO
import com.tip.kuruma.dto.car_data.CarModelDTO
import com.tip.kuruma.dto.clients.car_api.CarApiListBrandDTO
import com.tip.kuruma.dto.clients.car_api.CarApiListModelDTO

class CarApiMapper: CarDataMapperInterface<CarApiListBrandDTO, CarApiListModelDTO> {

    override fun mapToCarMakeDTO(response: CarApiListBrandDTO?): List<CarMakeDTO>? {
        return response?.data?.map {
            CarMakeDTO(it.id, it.name)
        }
    }

    override fun mapToCarModelDTO(response: CarApiListModelDTO): List<CarModelDTO>? {
        return response.data?.map {
            CarModelDTO(it.id, it.name)
        }
    }
}