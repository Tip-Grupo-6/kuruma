package com.tip.kuruma.models

import jakarta.persistence.*

@Entity
@Table(name = "car")
data class Car(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val brand: String? = null,
    val model: String? = null,
    val year: Int? = null,
    val color: String? = null,
    val image: String? = null,
    val isDeleted: Boolean? = false,
    @OneToMany(mappedBy = "car_id", fetch = FetchType.LAZY)
    val carItems: List<CarItem>? = null,
    @OneToMany(mappedBy = "car", cascade = [CascadeType.ALL], orphanRemoval = true,  fetch = FetchType.EAGER)
    val notifications: MutableList<Notification> = mutableListOf()  // Use MutableList instead of List
) {

    fun getName(): String {
        return "$brand $model"
    }

    override fun toString(): String {
        return "Car(id=$id, brand=$brand, model=$model, years=$year, color=$color, image=$image, isDeleted=$isDeleted)"
    }
}