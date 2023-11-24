package com.tip.kuruma.builders

import com.tip.kuruma.models.Car
import com.tip.kuruma.models.CarItem

class CarBuilder {

    private var id: Long? = null
    private var userId: Long? = 1L
    private var brand: String = "Honda"
    private var model: String = "Civic"
    private var year: Int = 2023
    private var color: String = "white"
    private var kilometers: String = "500"
    private var isDeleted: Boolean = false
    private var carItems: List<CarItem>? = listOf(CarItemBuilder().build())

    fun withId(id: Long?): CarBuilder {
        this.id = id
        return this
    }

    fun withUserId(id: Long): CarBuilder {
        this.userId = id
        return this
    }

    fun withBrand(brand: String): CarBuilder {
        this.brand = brand
        return this
    }

    fun withModel(model: String): CarBuilder {
        this.model = model
        return this
    }

    fun withYear(year: Int): CarBuilder {
        this.year = year
        return this
    }

    fun withColor(color: String): CarBuilder {
        this.color = color
        return this
    }

    fun withIsDeleted(isDeleted: Boolean): CarBuilder {
        this.isDeleted = isDeleted
        return this
    }

    fun withCarItems(carItems: List<CarItem>?): CarBuilder {
        this.carItems = carItems
        return this
    }

    fun build(): Car {
        return Car(
            id = id,
            userId = userId,
            brand = brand,
            model = model,
            year = year,
            color = color,
            kilometers = kilometers,
            isDeleted = isDeleted,
            carItems = carItems
        )
    }

}