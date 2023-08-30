package com.tip.kuruma.models

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "car")
data class Car(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val name: String? = null,
    val brand: String? = null,
    val model: String? = null,
    val years: Int? = null,
    val color: String? = null,
    val image: String? = null,
    val isDeleted: Boolean? = false
)