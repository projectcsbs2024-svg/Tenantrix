package com.global.tenantrix.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "rooms",
    foreignKeys = [
        ForeignKey(
            entity = PropertyEntity::class,
            parentColumns = ["propertyId"],
            childColumns = ["propertyId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("propertyId")]
)
data class RoomEntity(
    @PrimaryKey(autoGenerate = true)
    val roomId: Long = 0,
    val propertyId: Long,
    val roomNumber: String,
    val rentAmount: Double,
    val meterNumber: String? = null,
    val isOccupied: Boolean
)
