package com.global.tenantrix.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.global.tenantrix.data.entity.RoomEntity
import com.global.tenantrix.data.repository.RoomRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoomViewModel @Inject constructor(
    private val repo: RoomRepository
) : ViewModel() {

    // Get all rooms in a property
    fun getRooms(propertyId: Long): StateFlow<List<RoomEntity>> =
        repo.getRoomsByProperty(propertyId)
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                emptyList()
            )

    // Vacant rooms
    val vacantRooms: StateFlow<List<RoomEntity>> =
        repo.getVacantRooms()
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                emptyList()
            )

    // ⭐ REQUIRED — Get Room by ID (for rent auto-fill)
    fun getRoomById(roomId: Long): Flow<RoomEntity?> {
        return repo.getRoomById(roomId)
    }

    // ---------------- CRUD ----------------
    fun addRoom(
        room: RoomEntity,
        onRoomInserted: (Long) -> Unit
    ) {
        viewModelScope.launch {
            val roomId = repo.addRoom(room)
            onRoomInserted(roomId)
        }
    }


    fun updateRoom(room: RoomEntity) {
        viewModelScope.launch { repo.updateRoom(room) }
    }


}
