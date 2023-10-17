package com.tip.kuruma.mapper

import com.tip.kuruma.dto.car_data.CarMakeDTO
import com.tip.kuruma.dto.car_data.CarModelDTO

interface CarDataMapperInterface<T, S> {

    fun mapToCarMakeDTO(response: T?): List<CarMakeDTO>?

    fun mapToCarModelDTO(response: S): List<CarModelDTO>?
}