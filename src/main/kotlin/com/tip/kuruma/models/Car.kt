package com.tip.kuruma.models

import jakarta.persistence.*
import org.hibernate.annotations.Where

@Entity
@Table(name = "car")
data class Car(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val brand: String? = null,
    val model: String? = null,
    @Column(name = "`year`")
    val year: Int? = null,
    val color: String? = null,
    val image: String? = null,
    val kilometers: String? = null,
    val isDeleted: Boolean? = false,
    @OneToMany(mappedBy = "carId", fetch = FetchType.EAGER)
    @Where(clause = "is_deleted = false")
    val carItems: List<CarItem>? = null
) {

    fun getName(): String {
        return "$brand $model"
    }

    override fun toString(): String {
        return "Car(id=$id, brand=$brand, model=$model, years=$year, color=$color, image=$image, isDeleted=$isDeleted)"
    }
}