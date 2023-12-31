package com.tip.kuruma.repositories

import com.tip.kuruma.models.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserRepository : JpaRepository<User, Long> {
    fun findAllByIsDeletedIsFalse(): List<User>

    fun findByEmail(email: String): Optional<User>
}
