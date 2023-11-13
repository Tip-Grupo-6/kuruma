package com.tip.kuruma.models

import com.tip.kuruma.enums.Role
import jakarta.persistence.*
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.time.LocalDate

@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var name: String? = null,
    var email: String? = null,
    @get:JvmName("password_user")
    var password: String? = null,
    @Enumerated(EnumType.STRING)
    var role: Role? = null,
    var isDeleted: Boolean? = false,
    var created_at: LocalDate? = LocalDate.now(),
    var updated_at: LocalDate? = LocalDate.now()
): UserDetails {
    override fun toString(): String {
        return "User(id=$id, name=$name, email=$email, password=$password, isDeleted=$isDeleted, created_at=$created_at, updated_at=$updated_at)"
    }

    override fun getAuthorities(): List<SimpleGrantedAuthority> {
        return listOf(SimpleGrantedAuthority((this.role?.name)))
    }

    override fun getPassword(): String {
        return this.password!!
    }

    override fun getUsername(): String {
        return this.email!!
    }

    override fun isAccountNonExpired(): Boolean {
        return !this.isDeleted!!
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}






