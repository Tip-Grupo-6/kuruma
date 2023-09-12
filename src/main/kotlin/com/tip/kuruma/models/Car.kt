package com.tip.kuruma.models

import jakarta.persistence.*
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
    // car items
    @ManyToMany(fetch = FetchType.LAZY)
    var carItems: List<CarItem>? = null,) {

    fun getName(): String {
        return "$brand $model"
    }
}