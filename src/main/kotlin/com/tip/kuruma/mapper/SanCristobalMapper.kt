package com.tip.kuruma.mapper

import com.tip.kuruma.dto.car_data.CarDetailDTO
import com.tip.kuruma.dto.car_data.CarMakeDTO
import com.tip.kuruma.dto.car_data.CarModelDTO
import com.tip.kuruma.dto.clients.san_cristobal.CarMakeSanCristobalDTO
import com.tip.kuruma.dto.clients.san_cristobal.CarModelDetailSanCristobalDTO
import com.tip.kuruma.dto.clients.san_cristobal.CarModelSanCristobalDTO


class SanCristobalMapper: CarDataMapperInterface<CarMakeSanCristobalDTO, CarModelSanCristobalDTO, CarModelDetailSanCristobalDTO> {

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

    override fun mapToCarDetailDTO(response: CarModelDetailSanCristobalDTO): List<CarDetailDTO>? {
        return response.versions?.map {
            CarDetailDTO(it.referencePrice0km,
                    it.used0KmPrice,
                    it.statedAmount,
                    it.fullCarDescripcion,
                    it.makeDescription,
                    it.modelDescription,
                    it.year,
                    it.infoAutoCode,
                    it.category,
                    it.fuelCode,
                    it.isImported,
                    it.panoramicCrystalCeiling,
                    it.carBrand)
        }
    }
}