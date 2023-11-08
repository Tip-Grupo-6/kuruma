package com.tip.kuruma.services

import com.tip.kuruma.EntityNotFoundException
import com.tip.kuruma.models.User
import com.tip.kuruma.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
 class UserService @Autowired constructor(
    private val userRepository: UserRepository
) {
    fun getAllUsers(): List<User> = userRepository.findAllByIsDeletedIsFalse()

    fun saveUser(user: User): User = userRepository.save(user)

    fun getUserById(id: Long): User = userRepository.findById(id).orElseThrow { EntityNotFoundException("User with id $id not found") }

    fun updateUser(id: Long, user: User): User {
        val userToUpdate = getUserById(id)
        userToUpdate.let {
            it.name = user.name
            it.email = user.email
            it.password = user.password
            it.isDeleted = user.isDeleted
            it.updated_at = LocalDate.now()
            return saveUser(it)
        }

    }

    fun patchUser(id: Long, user: User): User {
        val userToUpdate = getUserById(id)
        userToUpdate.let {
            it.name = user.name ?: it.name
            it.email = user.email ?: it.email
            it.password = user.password ?: it.password
            it.isDeleted = user.isDeleted ?: it.isDeleted
            it.updated_at = LocalDate.now()
            return saveUser(it)
        }
    }

    fun deleteUser(id: Long) {
        val user = getUserById(id)
        user.isDeleted = true
        saveUser(user)
    }
}