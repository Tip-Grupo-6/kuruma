package com.tip.kuruma.models

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDate

@Entity
@Table(name = "car")
data class Car(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var brand: String? = null,
    var model: String? = null,
    var years: Int? = null,
    var color: String? = null,
    var image: String? = null,
    var isDeleted: Boolean? = false,
    var lastOilChange: LocalDate = LocalDate.now(),
    var lastWaterCheck: LocalDate = LocalDate.now(),
    var lastTirePressureCheck: LocalDate = LocalDate.now()) {

    fun getName(): String {
        return "$brand $model"
    }
}