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
    @Column(name = "current_kms_since_last_change")
    var currentKmsSinceLastChange: Int? = null,
    @Column(name = "is_deleted")
    var isDeleted: Boolean? = false,
    @Column(name = "initial_car_kilometers")
    val initialCarKilometers: Int? = null,
    var created_at: LocalDate? = LocalDate.now(),
    var updated_at: LocalDate? = LocalDate.now()){

    override fun toString(): String {
        return "CarItem(id=$id, car_id=$carId, last_change=$lastChange, maintenance_item_id=$maintenanceItem)"
    }
}