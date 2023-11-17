package com.tip.kuruma.repositories

import com.tip.kuruma.models.Suscription
import com.tip.kuruma.models.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface SuscriptionRepository : JpaRepository<Suscription, Long> {
    fun findAllByIsDeletedIsFalse(): List<Suscription>

    fun getAllSuscriptionsByUserId(userId: Long): List<Suscription>
}
