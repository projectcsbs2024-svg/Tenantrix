package com.global.tenantrix.data.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import com.global.tenantrix.data.entity.PropertyEntity

@Dao
interface PropertyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProperty(property: PropertyEntity): Long

    @Query("SELECT * FROM properties")
    fun getAllProperties(): Flow<List<PropertyEntity>>
}
