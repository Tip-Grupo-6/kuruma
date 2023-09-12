package com.tip.kuruma.models

import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "car_item")
data class CarItem(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    @ManyToOne(fetch = FetchType.LAZY) // Many car items can be associated with one car
    @JoinColumn(name = "car_id")
    var car: Car? = null,
    var isDeleted: Boolean? = false,
    var name: String? = null,
    var last_change: LocalDate = LocalDate.now(),
    var next_change_due: LocalDate = LocalDate.now(),
    var status: Boolean = false){
}