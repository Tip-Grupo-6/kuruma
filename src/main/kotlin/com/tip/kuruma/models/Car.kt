package com.tip.kuruma.models

import jakarta.persistence.*

@Entity
@Table(name = "car")
data class Car(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var brand: String? = null,
    var model: String? = null,
    var year: Int? = null,
    var color: String? = null,
    var image: String? = null,
    var isDeleted: Boolean? = false,

    @OneToMany(mappedBy = "car", cascade = [CascadeType.ALL], orphanRemoval = true,  fetch = FetchType.EAGER)
    var carItems: List<CarItem> = mutableListOf(),  // Use MutableList instead of List

    @OneToMany(mappedBy = "car", cascade = [CascadeType.ALL], orphanRemoval = true,  fetch = FetchType.EAGER)
    val notifications: MutableList<Notification> = mutableListOf()  // Use MutableList instead of List
){

    fun getName(): String {
        return "$brand $model"
    }

    override fun toString(): String {
        return "Car(id=$id, brand=$brand, model=$model, years=$year, color=$color, image=$image, isDeleted=$isDeleted)"
    }
}