package com.global.tenantrix.data.entity


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "properties")
data class PropertyEntity(
    @PrimaryKey(autoGenerate = true)
    val propertyId: Long = 0,
    val name: String,
    val address: String,
    val ownerName: String,
    val ownerPhone: String,
    val ownerEmail: String? = null,
    val perUnitElectricityCost: Double
)
