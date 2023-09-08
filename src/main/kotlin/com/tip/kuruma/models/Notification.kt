package com.tip.kuruma.models

import jakarta.persistence.*

@Entity
@Table(name = "notification")
data class Notification(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var message: String? = null,

    @ManyToOne(fetch = FetchType.LAZY) // Many notifications can be associated with one car
    @JoinColumn(name = "car_id")
    var car: Car? = null,
    var isDeleted: Boolean? = false
)