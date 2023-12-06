package com.tip.kuruma.builders

import com.tip.kuruma.enums.Role
import com.tip.kuruma.models.Suscription
import com.tip.kuruma.models.User
import com.tip.kuruma.models.UserItem

class SuscriptionBuilder {

    private var id: Long? = null
    private var userId : Long? = 1L
    private var key : String? = "key"
    private var auth : String? = "auth"
    private var endpoint : String? = "endpoint"
    private var isDeleted: Boolean = false

    fun withId(id: Long?): SuscriptionBuilder {
        this.id = id
        return this
    }

    fun withUserId(userId: Long?): SuscriptionBuilder {
        this.userId = userId
        return this
    }

    fun withKey(key: String?): SuscriptionBuilder {
        this.key = key
        return this
    }

    fun withAuth(auth: String?): SuscriptionBuilder {
        this.auth = auth
        return this
    }

    fun withEndpoint(endpoint: String?): SuscriptionBuilder {
        this.endpoint = endpoint
        return this
    }



    fun build(): Suscription {
        return Suscription(
            id = id,
            userId = userId,
            key = key,
            auth = auth,
            endpoint = endpoint,
            isDeleted = isDeleted

        )
    }

}