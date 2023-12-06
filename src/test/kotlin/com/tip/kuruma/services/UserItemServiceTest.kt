package com.tip.kuruma.services

import com.tip.kuruma.EntityNotFoundException
import com.tip.kuruma.builders.MaintenanceItemBuilder
import com.tip.kuruma.builders.UserItemBuilder
import com.tip.kuruma.models.UserItem
import com.tip.kuruma.repositories.MaintenanceItemRepository
import com.tip.kuruma.repositories.UserItemRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Rollback
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@SpringBootTest
class UserItemServiceTest  {
    @Autowired
    private lateinit var userItemRepository: UserItemRepository

    @Autowired
    private lateinit var userItemService: UserItemService

    @Autowired
    private lateinit var maintenanceService: MaintenanceService

    @Autowired
    private lateinit var maitenanceRepository: MaintenanceItemRepository

    private fun builtUserItem(): UserItem {
        var maitenanceItem = MaintenanceItemBuilder().build()
        maitenanceItem = maintenanceService.saveMaintenanceItem(maitenanceItem)
        return UserItemBuilder().withId(null).withMaintenanceItem(maitenanceItem).build()
    }

    @BeforeEach
    fun clearDatabase() {
        maitenanceRepository.deleteAll()
        userItemRepository.deleteAll()
    }

    @Test
    @Transactional
    @Rollback
    fun `fetching all userItems when there is none of the available `() {
        val foundUserItems = userItemService.getAllUserItems()
        assertEquals(0, foundUserItems.size)
    }

    @Test
    @Transactional
    @Rollback
    fun `fetching all userItems when there is one of the available `() {
        val userItem = builtUserItem()
        val savedUserItem = userItemService.saveUserItem(userItem)
        val foundUserItems = userItemService.getAllUserItems()
        assertEquals(1, foundUserItems.size)
        assertEquals(savedUserItem.id, foundUserItems[0].id)
    }

    @Test
    @Transactional
    @Rollback
    fun `fetching a userItem by id when it exists`() {
        val userItem = builtUserItem()
        val savedUserItem = userItemService.saveUserItem(userItem)
        val foundUserItem = userItemService.getUserItemById(savedUserItem.id!!)
        assertEquals(savedUserItem.id, foundUserItem.id)
    }

    @Test
    @Transactional
    @Rollback
    fun `fetching a userItem by id when it does not exist`() {
        val userItem = builtUserItem()
        val savedUserItem = userItemService.saveUserItem(userItem)
        assertThrows(EntityNotFoundException::class.java) {
            userItemService.getUserItemById(savedUserItem.id!! + 1)
        }
    }

    @Test
    @Transactional
    @Rollback
    fun `saving a userItem`() {
        val userItem = builtUserItem()
        val savedUserItem = userItemService.saveUserItem(userItem)
        // id is not null
        assert(savedUserItem.id != null)
        assertEquals(userItem.userId, savedUserItem.userId)
        assertEquals(userItem.maintenanceItem?.code, savedUserItem.maintenanceItem?.code)
        assertEquals(userItem.lastChange, savedUserItem.lastChange)
        assertEquals(userItem.isDeleted, savedUserItem.isDeleted)
    }

    @Test
    @Transactional
    @Rollback
    fun `deleting a userItem by id when it exists`() {
        val userItem = builtUserItem()
        val savedUserItem = userItemService.saveUserItem(userItem)
        userItemService.deleteUserItem(savedUserItem.id!!)
        val deletedUserItem = userItemService.getUserItemById(savedUserItem.id!!)
        assertEquals(true, deletedUserItem.isDeleted)
    }

    @Test
    @Transactional
    @Rollback
    fun `deleting a userItem by id when it does not exist`() {
        val userItem = builtUserItem()
        val savedUserItem = userItemService.saveUserItem(userItem)
        assertThrows(EntityNotFoundException::class.java) {
            userItemService.deleteUserItem(savedUserItem.id!! + 1)
        }
    }

    @Test
    @Transactional
    @Rollback
    fun `updating a userItem by id when it exists`() {
        val userItem = builtUserItem()
        val savedUserItem = userItemService.saveUserItem(userItem)
        val updatedUserItem = userItemService.updateUserItem(savedUserItem.id!!, userItem.copy(lastChange = LocalDate.now().plusMonths(5)))
        assertEquals(LocalDate.now().plusMonths(5), updatedUserItem.lastChange)
    }

    @Test
    @Transactional
    @Rollback
    fun `updating a userItem by id when it does not exist`() {
        val userItem = builtUserItem()
        val savedUserItem = userItemService.saveUserItem(userItem)
        assertThrows(EntityNotFoundException::class.java) {
            userItemService.updateUserItem(savedUserItem.id!! + 1, userItem.copy(lastChange = LocalDate.now().plusMonths(5)))
        }
    }
}
