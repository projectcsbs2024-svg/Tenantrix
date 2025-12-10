package com.global.tenantrix.data.repository

import kotlinx.coroutines.flow.Flow
import com.global.tenantrix.data.dao.TenantDao
import com.global.tenantrix.data.entity.TenantEntity

class TenantRepository(
    private val tenantDao: TenantDao
) {

    fun getActiveTenants(): Flow<List<TenantEntity>> =
        tenantDao.getActiveTenants()

    fun getTotalTenantCount(): Flow<Int> =
        tenantDao.getTotalTenantCount()

    fun getTenantById(tenantId: Long): Flow<TenantEntity?> =
        tenantDao.getTenantById(tenantId)   // ⭐ REQUIRED

    suspend fun addTenant(tenant: TenantEntity): Long {
        return tenantDao.insertTenant(tenant)
    }


    suspend fun updateTenant(tenant: TenantEntity) =
        tenantDao.updateTenant(tenant)

    suspend fun deleteTenant(tenant: TenantEntity) =
        tenantDao.deleteTenant(tenant)

    suspend fun getTenantByRoom(roomId: Long): TenantEntity? =
        tenantDao.getTenantByRoom(roomId)
}
