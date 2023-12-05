package com.tip.kuruma.repositories

import com.tip.kuruma.models.Suscription
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

interface SuscriptionRepository : JpaRepository<Suscription, Long> {
    fun findAllByIsDeletedIsFalse(): List<Suscription>

    fun getAllSuscriptionsByUserId(userId: Long): List<Suscription>

    fun getByUserIdAndEndpointAndIsDeletedFalse(userId: Long, endpoint: String): Suscription?

    @Query(nativeQuery = true, value = "SELECT s.* FROM suscription s WHERE s.user_id in (:usersId) AND s.is_deleted = false")
    fun getAllByUsersId(@Param("usersId") usersId: Set<Long>): List<Suscription>
}
