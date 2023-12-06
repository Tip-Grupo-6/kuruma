package com.tip.kuruma.builders

import com.tip.kuruma.enums.Role
import com.tip.kuruma.models.User
import com.tip.kuruma.models.UserItem

class UserBuilder {

    private var id: Long? = null
    private var name: String = "John"
    private var email: String = ""
    private var password: String = "password"
    private var role: Role = Role.USER
    private var isDeleted: Boolean = false

    fun withId(id: Long?): UserBuilder {
        this.id = id
        return this
    }

    fun withName(name: String): UserBuilder {
        this.name = name
        return this
    }

    fun withEmail(email: String): UserBuilder {
        this.email = email
        return this
    }

    fun withPassword(password: String): UserBuilder {
        this.password = password
        return this
    }

    fun withRole(role: Role): UserBuilder {
        this.role = role
        return this
    }



    fun build(): User {
        return User(
            id = id,
            name = name,
            email = email,
            password = password,
            role = role,
            isDeleted = isDeleted

        )
    }

}