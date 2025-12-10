package com.global.tenantrix.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "utility_bills",
    foreignKeys = [
        ForeignKey(
            entity = RoomEntity::class,
            parentColumns = ["roomId"],
            childColumns = ["roomId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("roomId")]
)
data class UtilityBillEntity(
    @PrimaryKey(autoGenerate = true)
    val billId: Long = 0,
    val roomId: Long,
    val utilityType: String,
    val startMonth: Int,
    val endMonth: Int,
    val year: Int,
    val unitsUsed: Int,
    val unitsCarriedForward: Int,
    val billAmount: Double,
    val amountPaid: Double,
    val billImagePath: String? = null,
    val remarks: String? = null
)
