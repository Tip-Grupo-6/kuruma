package com.tip.kuruma.models

import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "notification")
data class Notification(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    @Column(name = "car_id")
    var carId: Long? = null,
    @Column(name = "maintenance_item_id")
    var maintenanceItemId: Long? = null,
    var frequency: Int? = null,
    var message: String? = null,
    var isDeleted: Boolean? = false,
    var created_at: LocalDate? = LocalDate.now(),
    var updated_at: LocalDate? = LocalDate.now()
)