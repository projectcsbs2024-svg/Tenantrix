package com.global.tenantrix.data.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import com.global.tenantrix.data.entity.TenantEntity

@Dao
interface TenantDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTenant(tenant: TenantEntity): Long

    @Update
    suspend fun updateTenant(tenant: TenantEntity)

    @Delete
    suspend fun deleteTenant(tenant: TenantEntity)

    @Query("SELECT * FROM tenants WHERE leaveDate IS NULL")
    fun getActiveTenants(): Flow<List<TenantEntity>>

    @Query("SELECT * FROM tenants WHERE roomId = :roomId")
    suspend fun getTenantByRoom(roomId: Long): TenantEntity?

    @Query("SELECT COUNT(*) FROM tenants")
    fun getTotalTenantCount(): Flow<Int>

    @Query("SELECT * FROM tenants WHERE tenantId = :tenantId LIMIT 1")
    fun getTenantById(tenantId: Long): Flow<TenantEntity?>
}
