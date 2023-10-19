package com.tip.kuruma.client


interface CarDataClientInterface<T, S> {

    fun getCarMakes(year: Int): T

    fun getCarModel(year: Int, makeId: Int): S
}