package com.global.tenantrix.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.global.tenantrix.data.entity.AppSettingEntity
import com.global.tenantrix.data.repository.AppSettingRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AppSettingsViewModel(
    private val repository: AppSettingRepository
) : ViewModel() {

    private val _settings = MutableStateFlow<List<AppSettingEntity>>(emptyList())
    val settings: StateFlow<List<AppSettingEntity>> = _settings

    fun loadSettings() {
        viewModelScope.launch {
            repository.getSettings().collect {
                _settings.value = it
            }
        }
    }

    fun insertSetting(entity: AppSettingEntity) =
        viewModelScope.launch { repository.insertSetting(entity) }

    fun updateSetting(entity: AppSettingEntity) =
        viewModelScope.launch { repository.updateSetting(entity) }
}

