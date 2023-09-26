package com.tip.kuruma.models

import jakarta.persistence.*

@Entity
@Table(name = "maintenance_item")
data class MaintenanceItem(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null,
        val code: String? = null,
        val description: String? = null,
        @Column(name = "replacement_frequency")
        val replacementFrequency: Int? = null
)
