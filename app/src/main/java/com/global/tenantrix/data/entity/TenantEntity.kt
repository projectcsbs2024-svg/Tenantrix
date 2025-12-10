package com.global.tenantrix.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "tenants",
    foreignKeys = [
        ForeignKey(
            entity = RoomEntity::class,
            parentColumns = ["roomId"],
            childColumns = ["roomId"],
            onDelete = ForeignKey.SET_NULL
        )
    ],
    indices = [Index("roomId")]
)
data class TenantEntity(
    @PrimaryKey(autoGenerate = true)
    val tenantId: Long = 0,
    val name: String,
    val phone: String,
    val idProof: String? = null,
    val joinDate: Long,
    val leaveDate: Long? = null,
    val depositAmount: Double?,
    val roomId: Long?
)


