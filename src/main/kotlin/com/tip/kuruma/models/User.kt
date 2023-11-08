package com.tip.kuruma.models

import jakarta.persistence.*
import org.hibernate.annotations.Where
import java.time.LocalDate

@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var name: String? = null,
    var email: String? = null,
    var password: String? = null,
    var isDeleted: Boolean? = false,
    var created_at: LocalDate? = LocalDate.now(),
    var updated_at: LocalDate? = LocalDate.now()
) {
    override fun toString(): String {
        return "User(id=$id, name=$name, email=$email, password=$password, isDeleted=$isDeleted, created_at=$created_at, updated_at=$updated_at)"
    }
}






