package com.global.tenantrix.data.dao

import androidx.room.*
import com.global.tenantrix.data.entity.AppSettingEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AppSettingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSetting(setting: AppSettingEntity)

    @Query("SELECT * FROM app_settings")
    fun getSettings(): Flow<List<AppSettingEntity>>

    @Update
    suspend fun updateSetting(setting: AppSettingEntity)
}
