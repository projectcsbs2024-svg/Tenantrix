package com.global.tenantrix.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.global.tenantrix.data.entity.PropertyEntity
import com.global.tenantrix.data.repository.PropertyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PropertyViewModel @Inject constructor(
    private val repository: PropertyRepository
) : ViewModel() {

    // -----------------------------------------------------
    // LIVE LIST OF PROPERTIES (FOR HOME SCREEN)
    // -----------------------------------------------------

    val properties: StateFlow<List<PropertyEntity>> =
        repository.getAllProperties()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )

    // -----------------------------------------------------
    // ADD PROPERTY — NEW ID MUST RETURN TO UI
    // -----------------------------------------------------

    fun addProperty(property: PropertyEntity, onSaved: (Long) -> Unit) {
        viewModelScope.launch {
            val id = repository.addProperty(property) // repository returns Long ID
            onSaved(id)
        }
    }

    // -----------------------------------------------------
    // UPDATE PROPERTY (OPTIONAL FUTURE USE)
    // -----------------------------------------------------

    fun updateProperty(property: PropertyEntity) {
        viewModelScope.launch {
            repository.addProperty(property) // same DAO method (Upsert behavior)
        }
    }
}
