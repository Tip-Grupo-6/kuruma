package com.tip.kuruma.models

import jakarta.persistence.*
import org.hibernate.annotations.Where
import java.time.LocalDate

@Entity
@Table(name = "car")
data class Car(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Column(name = "user_id")
    val userId: Long? = null,
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
    val carItems: List<CarItem>? = null,
    var created_at: LocalDate? = LocalDate.now(),
    var updated_at: LocalDate? = LocalDate.now()
) {

    fun getName(): String {
        return "$brand $model"
    }

    override fun toString(): String {
        return "Car(id=$id, brand=$brand, model=$model, year=$year, color=$color, image=$image, isDeleted=$isDeleted, kilometers=$kilometers)"
    }
}