package com.tip.kuruma.services

import com.tip.kuruma.EntityNotFoundException
import com.tip.kuruma.models.UserItem
import com.tip.kuruma.repositories.UserItemRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.context.annotation.Lazy
import java.time.LocalDate

@Service
class UserItemService(
    private val userItemRepository: UserItemRepository,
    private val maintenanceService: MaintenanceService,
    @Lazy
    private val userService: UserService
) {

    companion object {
        private val LOGGER = LoggerFactory.getLogger(UserItemService::class.java)
    }

    fun getAllUserItems(): List<UserItem> {
        LOGGER.info("Find all user items")
        val userItems = userItemRepository.findAll()
        return userItems
    }


    fun saveUserItem(userItem: UserItem): UserItem {
        LOGGER.info("Saving user item $userItem")
        val maintenanceItem = maintenanceService.findByCode(userItem.maintenanceItem?.code!!)
        return userItemRepository.save(userItem.copy(maintenanceItem = maintenanceItem))
    }

    fun getUserItemById(id: Long): UserItem {
        LOGGER.info("Find user item with id $id")
        val userItem = userItemRepository.findById(id)
            .orElseThrow { EntityNotFoundException("User item with id $id not found") }

        return userItem
    }


    fun updateUserItem(id: Long, userItem: UserItem): UserItem {
        val userItemToUpdate = getUserItemById(id)
        return userItemRepository.save(userItemToUpdate.copy(lastChange = userItem.lastChange, updated_at = LocalDate.now())).also {
            LOGGER.info("User Item with id $id has been updated")
        }
    }

    fun deleteUserItem(id: Long) {
        val userItemToDelete = getUserItemById(id)
        doLogicDelete(userItemToDelete)
    }

    private fun doLogicDelete(userItemToDelete: UserItem) {
        val updatedUserItem = userItemToDelete.copy(isDeleted = true)
        userItemRepository.save(updatedUserItem)
        LOGGER.info("User Item with id ${userItemToDelete.id} has been deleted")
    }

    fun deleteAllUserItems() = userItemRepository.deleteAll()

    fun updateUserItems(userId: Long, userItems: List<UserItem>): List<UserItem> {
        val userItemsFromDB = userItemRepository.findByUserId(userId)
        userItemsFromDB.forEach { doLogicDelete(it) }
        return userItems.map { this.saveUserItem(it.copy(userId = userId)) }
    }

}