package com.tip.kuruma.repositories

import com.tip.kuruma.models.Notification
import com.tip.kuruma.models.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
    fun findAllByIsDeletedIsFalse(): List<User>
}
