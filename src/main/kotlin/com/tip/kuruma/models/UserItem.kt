package com.tip.kuruma.models

import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "user_item")
data class UserItem(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    @Column(name = "user_id")
    var userId: Long? = null,
    @OneToOne
    @JoinColumn(referencedColumnName = "id")
    val maintenanceItem: MaintenanceItem? = null,
    @Column(name = "last_change")
    var lastChange: LocalDate = LocalDate.now(),
    @Column(name = "is_deleted")
    var isDeleted: Boolean? = false,
    var created_at: LocalDate? = LocalDate.now(),
    var updated_at: LocalDate? = LocalDate.now()){

    override fun toString(): String {
        return "UserItem(id=$id, user_id=$userId, last_change=$lastChange, maintenance_item_id=$maintenanceItem)"
    }
}