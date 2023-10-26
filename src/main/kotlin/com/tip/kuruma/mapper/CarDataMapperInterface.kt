package com.tip.kuruma.mapper

import com.tip.kuruma.dto.car_data.CarDetailDTO
import com.tip.kuruma.dto.car_data.CarMakeDTO
import com.tip.kuruma.dto.car_data.CarModelDTO
import com.tip.kuruma.dto.clients.san_cristobal.CarModelDetailSanCristobalDTO

interface CarDataMapperInterface<T, S> {

    fun mapToCarMakeDTO(response: T?): List<CarMakeDTO>?

    fun mapToCarModelDTO(response: S): List<CarModelDTO>?

    fun mapToCarDetailDTO(response: CarModelDetailSanCristobalDTO):  List<CarDetailDTO>?
}