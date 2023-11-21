package com.tip.kuruma.models

import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "car_item")
data class CarItem(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    @Column(name = "car_id")
    var carId: Long? = null,
    @OneToOne
    @JoinColumn(referencedColumnName = "id")
    val maintenanceItem: MaintenanceItem? = null,
    @Column(name = "last_change")
    var lastChange: LocalDate = LocalDate.now(),
    @Column(name = "is_deleted")
    val initialCarKilometers: Int? = null,
    var currentKmsSinceLastChange: Int? = null,
    var isDeleted: Boolean? = false,
    var created_at: LocalDate? = LocalDate.now(),
    var updated_at: LocalDate? = LocalDate.now()){

    override fun toString(): String {
        return "CarItem(id=$id, car_id=$carId, last_change=$lastChange, maintenance_item_id=$maintenanceItem)"
    }
}