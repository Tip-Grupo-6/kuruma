package com.tip.kuruma.models

import jakarta.persistence.*
import org.hibernate.annotations.Where
import java.time.LocalDate

@Entity
@Table(name = "suscription")
data class Suscription(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    @Column(name = "user_id")
    var userId: Long? = null,
    var endpoint : String? = null,
    var key : String? = null,
    var auth : String? = null,
    var isDeleted: Boolean? = false,
    var created_at: LocalDate? = LocalDate.now(),
    var updated_at: LocalDate? = LocalDate.now()

) {
}