package com.global.tenantrix.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey

@Entity(
    tableName = "payment_methods",
    foreignKeys = [
        ForeignKey(
            entity = PropertyEntity::class,
            parentColumns = ["propertyId"],
            childColumns = ["propertyId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class PaymentMethodEntity(
    @PrimaryKey(autoGenerate = true)
    val paymentMethodId: Long = 0,
    val propertyId: Long,
    val type: String,
    val details: String
)
