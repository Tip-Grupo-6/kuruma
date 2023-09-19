package com.tip.kuruma.models

import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "car_item")
data class CarItem(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var car_id: Long? = null,
    var isDeleted: Boolean? = false,
    var name: String? = null,
    var last_change: LocalDate = LocalDate.now(),
    var next_change_due: LocalDate? = null,
    var replacement_frequency: Int = 0,
    var due_status: Boolean? = false){

    override fun toString(): String {
        return "CarItem(id=$id, name=$name, last_change=$last_change, next_change_due=$next_change_due, replacement_frequency=$replacement_frequency, due_status=$due_status)"
    }
}