package com.global.tenantrix.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "rent_payments",
    foreignKeys = [
        ForeignKey(
            entity = TenantEntity::class,
            parentColumns = ["tenantId"],
            childColumns = ["tenantId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("tenantId")]
)
data class RentPaymentEntity(
    @PrimaryKey(autoGenerate = true)
    val paymentId: Long = 0,
    val tenantId: Long,
    val month: Int,
    val year: Int,
    val rentAmount: Double,
    val amountPaid: Double,
    val paymentMode: String,
    val paymentDate: Long,
    val remarks: String? = null
)
