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
    @OneToOne
    @JoinColumn(referencedColumnName = "id")
    val maintenanceItem: MaintenanceItem? = null,
    var last_change: LocalDate = LocalDate.now(),
    var isDeleted: Boolean? = false){

    override fun toString(): String {
        return "CarItem(id=$id, card_id=$car_id, last_change=$last_change, maintenance_item_id=$maintenanceItem)"
    }
}