package com.tip.kuruma.repositories

import com.tip.kuruma.models.CarItem
import com.tip.kuruma.models.UserItem
import org.springframework.data.jpa.repository.JpaRepository

interface  UserItemRepository : JpaRepository<UserItem, Long> {

    fun findByUserId(id: Long): List<UserItem>

}