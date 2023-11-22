package com.tip.kuruma.models

import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "maintenance_item")
data class MaintenanceItem(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null,
        val code: String? = null,
        val description: String? = null,
        @Column(name = "replacement_frequency")
        val replacementFrequency: Int? = null,
        @Column(name = "kilometers_frequency")
        val kilometersFrequency: Int? = null,
        var created_at: LocalDate? = LocalDate.now(),
        var updated_at: LocalDate? = LocalDate.now()
)
