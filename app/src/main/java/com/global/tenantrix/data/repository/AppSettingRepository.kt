package com.global.tenantrix.data.repository

import com.global.tenantrix.data.dao.AppSettingDao
import com.global.tenantrix.data.entity.AppSettingEntity

class AppSettingRepository(
    private val dao: AppSettingDao
) {

    fun getSettings() = dao.getSettings()

    suspend fun insertSetting(entity: AppSettingEntity) {
        dao.insertSetting(entity)
    }

    suspend fun updateSetting(entity: AppSettingEntity) {
        dao.updateSetting(entity)
    }
}

