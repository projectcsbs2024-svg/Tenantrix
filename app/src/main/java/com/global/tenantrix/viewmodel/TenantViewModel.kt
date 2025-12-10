package com.global.tenantrix.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.global.tenantrix.data.entity.TenantEntity
import com.global.tenantrix.data.repository.TenantRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TenantViewModel @Inject constructor(
    private val repo: TenantRepository
) : ViewModel() {

    // ---------------- ACTIVE TENANTS LIST ----------------
    val tenants: StateFlow<List<TenantEntity>> =
        repo.getActiveTenants()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // ---------------- FIND TENANT BY ID ----------------
    fun getTenantById(tenantId: Long): Flow<TenantEntity?> {
        return repo.getTenantById(tenantId)
    }

    // ---------------- ADD TENANT (RETURN ID) ----------------
    fun addTenant(tenant: TenantEntity, onTenantInserted: (Long) -> Unit) {
        viewModelScope.launch {
            val tenantId = repo.addTenant(tenant)   // DAO returns ID
            onTenantInserted(tenantId)
        }
    }

    // ---------------- UPDATE TENANT ----------------
    fun updateTenant(tenant: TenantEntity) {
        viewModelScope.launch {
            repo.updateTenant(tenant)
        }
    }

    // ---------------- DELETE TENANT ----------------
    fun deleteTenant(tenant: TenantEntity) {
        viewModelScope.launch {
            repo.deleteTenant(tenant)
        }
    }
}
