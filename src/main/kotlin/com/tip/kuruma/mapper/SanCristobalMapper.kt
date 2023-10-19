package com.tip.kuruma.mapper

import com.tip.kuruma.dto.car_data.CarMakeDTO
import com.tip.kuruma.dto.car_data.CarModelDTO
import com.tip.kuruma.dto.clients.san_cristobal.CarMakeSanCristobalDTO
import com.tip.kuruma.dto.clients.san_cristobal.CarModelSanCristobalDTO


class SanCristobalMapper: CarDataMapperInterface<CarMakeSanCristobalDTO, CarModelSanCristobalDTO> {

    override fun mapToCarMakeDTO(response: CarMakeSanCristobalDTO?): List<CarMakeDTO>? {
        return response?.brands?.map {
            CarMakeDTO(it.id, it.description)
        }
    }

    override fun mapToCarModelDTO(response: CarModelSanCristobalDTO): List<CarModelDTO>? {
        return response.models?.map {
            CarModelDTO(it.id, it.description)
        }
    }


}