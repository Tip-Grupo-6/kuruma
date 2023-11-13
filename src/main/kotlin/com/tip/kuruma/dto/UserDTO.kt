package com.tip.kuruma.dto

import com.tip.kuruma.models.User
import java.time.LocalDate

class UserDTO(
    var id: Long? = null,
    var email: String? = null,
    var password: String? = null,
    var name: String? = null,
    var is_deleted: Boolean? = false,
    var created_at: LocalDate? = LocalDate.now(),
    var updated_at: LocalDate? = LocalDate.now())
{
    companion object {
        fun fromUser(user: User): UserDTO {
            return UserDTO(
                id = user.id,
                email = user.email,
                password = user.password,
                name = user.name,
                is_deleted = user.isDeleted,
                created_at = user.created_at,
                updated_at = user.updated_at
            )
        }

        fun fromUsers(users: List<User>): List<UserDTO> {
            return users.map { fromUser(it) }
        }
    }

    fun toUser(): User {
        return User(
            email = this.email,
            password = this.password,
            name = this.name,
            isDeleted = this.is_deleted,
            created_at = this.created_at,
            updated_at = this.updated_at
        )
    }

    override fun toString(): String {
        return "UserDTO(email=$email, name=$name)"
    }
}