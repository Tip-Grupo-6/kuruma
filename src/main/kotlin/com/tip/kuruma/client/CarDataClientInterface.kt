package com.tip.kuruma.client

import com.tip.kuruma.dto.clients.san_cristobal.CarModelDetailSanCristobalDTO


interface CarDataClientInterface<T, S> {

    fun getCarMakes(year: Int): T

    fun getCarModel(year: Int, makeId: Int): S

    fun getCarModelDetails(year: Int, makeId: Int, modelId: Int): CarModelDetailSanCristobalDTO
}