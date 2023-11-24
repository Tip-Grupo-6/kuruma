package com.tip.kuruma.builders

import com.tip.kuruma.models.MaintenanceItem

class MaintenanceItemBuilder {
    private var id: Long = 1L
    private var code = "OIL"
    private var description = "Oil change"
    private var replacementFrequency = 6
    private var kilometersFrequency = 10000

    fun withCode(code: String): MaintenanceItemBuilder {
        this.code = code
        return this
    }

    fun withDescription(description: String): MaintenanceItemBuilder {
        this.description = description
        return this
    }

    fun withReplacementFrequency(replacementFrequency: Int): MaintenanceItemBuilder {
        this.replacementFrequency = replacementFrequency
        return this
    }

    fun withKilometersFrequency(kilometersFrequency: Int): MaintenanceItemBuilder {
        this.kilometersFrequency = kilometersFrequency
        return this
    }

    fun build(): MaintenanceItem {
        return MaintenanceItem(
            id = id,
            code = code,
            description = description,
            replacementFrequency = replacementFrequency,
            kilometersFrequency = kilometersFrequency
        )
    }
}