package com.tip.kuruma.services

import com.tip.kuruma.EntityNotFoundException
import com.tip.kuruma.builders.UserBuilder
import com.tip.kuruma.models.User
import com.tip.kuruma.repositories.UserRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Rollback
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
class UserServiceTest  {
    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var userService: UserService

    private fun builtUser(): User {
        return UserBuilder().withId(null).build()
    }

    @BeforeEach
    fun clearDatabase() {
        userRepository.deleteAll()
    }

    @Test
    @Transactional
    @Rollback
    fun `fetching all users when there is none of the available `() {
        val foundUsers = userService.getAllUsers()
        assertEquals(0, foundUsers.size)
    }

    @Test
    @Transactional
    @Rollback
    fun `fetching all users when there is one of the available `() {
        val user = builtUser()
        val savedUser = userService.saveUser(user)
        val foundUsers = userService.getAllUsers()
        assertEquals(1, foundUsers.size)
        assertEquals(savedUser.id, foundUsers[0].id)
    }

    @Test
    @Transactional
    @Rollback
    fun `fetching a user by id when it exists`() {
        val user = builtUser()
        val savedUser = userService.saveUser(user)
        val foundUser = userService.getUserById(savedUser.id!!)
        assertEquals(savedUser.id, foundUser.id)
    }

    @Test
    @Transactional
    @Rollback
    fun `fetching a user by id when it does not exist`() {
        val user = builtUser()
        val savedUser = userService.saveUser(user)
        assertThrows(EntityNotFoundException::class.java) {
            userService.getUserById(savedUser.id!! + 1)
        }
    }

    @Test
    @Transactional
    @Rollback
    fun `saving a user`() {
        val user = builtUser()
        val savedUser = userService.saveUser(user)
        // id is not null
        assert(savedUser.id != null)
        assertEquals(user.name, savedUser.name)
        assertEquals(user.email, savedUser.email)
        assertEquals(user.password, savedUser.password)
        assertEquals(user.isDeleted, savedUser.isDeleted)
    }

    @Test
    @Transactional
    @Rollback
    fun `deleting a user by id when it exists`() {
        val user = builtUser()
        val savedUser = userService.saveUser(user)
        userService.deleteUser(savedUser.id!!)
        val deletedUser = userService.getUserById(savedUser.id!!)
        assertEquals(true, deletedUser.isDeleted)
    }

    @Test
    @Transactional
    @Rollback
    fun `deleting a user by id when it does not exist`() {
        val user = builtUser()
        val savedUser = userService.saveUser(user)
        assertThrows(EntityNotFoundException::class.java) {
            userService.deleteUser(savedUser.id!! + 1)
        }
    }

    @Test
    @Transactional
    @Rollback
    fun `updating a user by id when it exists`() {
        val user = builtUser()
        val savedUser = userService.saveUser(user)
        val updatedUser = userService.updateUser(savedUser.id!!, user.copy(name = "New Name"))
        assertEquals("New Name", updatedUser.name)
    }

    @Test
    @Transactional
    @Rollback
    fun `updating a user by id when it does not exist`() {
        val user = builtUser()
        val savedUser = userService.saveUser(user)
        assertThrows(EntityNotFoundException::class.java) {
            userService.updateUser(savedUser.id!! + 1, user.copy(name = "New Name"))
        }
    }
}
