package com.global.tenantrix.data.repository

import kotlinx.coroutines.flow.Flow
import com.global.tenantrix.data.dao.RoomDao
import com.global.tenantrix.data.entity.RoomEntity

class RoomRepository(
    private val roomDao: RoomDao
) {

    fun getRoomsByProperty(propertyId: Long) = roomDao.getRoomsByProperty(propertyId)
    fun getVacantRooms() = roomDao.getVacantRooms()
    fun getTotalRooms() = roomDao.getTotalRooms()
    fun getVacantRoomsCount() = roomDao.getVacantRoomsCount()
    fun getRoomById(roomId: Long) = roomDao.getRoomById(roomId)

    suspend fun addRoom(room: RoomEntity): Long {
        return roomDao.insertRoom(room)
    }

    suspend fun updateRoom(room: RoomEntity) = roomDao.updateRoom(room)
}
