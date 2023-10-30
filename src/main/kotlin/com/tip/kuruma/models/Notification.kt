package com.tip.kuruma.models

import jakarta.persistence.*

@Entity
@Table(name = "notification")
data class Notification(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    @Column(name = "car_id")
    var carId: Long? = null,
    var isDeleted: Boolean? = false
)