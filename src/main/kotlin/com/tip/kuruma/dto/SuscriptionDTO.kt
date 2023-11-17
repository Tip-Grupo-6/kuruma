package com.tip.kuruma.dto

import com.tip.kuruma.models.Suscription
import java.time.LocalDate

data class SuscriptionDTO(
    var id: Long? = null,
    var user_id : Long? = null,
    var auth : String? = null,
    var endpoint : String? = null,
    var key : String? = null,
    var is_deleted : Boolean? = false,
    var created_at: LocalDate? = LocalDate.now(),
    var updated_at: LocalDate? = LocalDate.now()

) {
    companion object {
        fun fromSuscription(suscription: Suscription): SuscriptionDTO {
            return SuscriptionDTO(
                id = suscription.id,
                user_id = suscription.userId,
                auth = suscription.auth,
                endpoint = suscription.endpoint,
                key = suscription.key,
                is_deleted = suscription.isDeleted,
                created_at = suscription.created_at,
                updated_at = suscription.updated_at
            )
        }

        fun fromSuscriptions(suscriptions: List<Suscription>): List<SuscriptionDTO> {
            return suscriptions.map { fromSuscription(it) }
        }

    }

    fun toSuscription(): Suscription {
        return Suscription(
            userId = this.user_id,
            auth = this.auth,
            endpoint = this.endpoint,
            key = this.key,
            isDeleted = this.is_deleted,
            created_at = this.created_at,
            updated_at = this.updated_at
        )
    }

}
