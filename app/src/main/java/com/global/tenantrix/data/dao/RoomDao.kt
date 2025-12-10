package com.global.tenantrix.data.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import com.global.tenantrix.data.entity.RoomEntity

@Dao
interface RoomDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRoom(room: RoomEntity): Long

    @Update
    suspend fun updateRoom(room: RoomEntity)

    @Query("SELECT * FROM rooms WHERE propertyId = :propertyId")
    fun getRoomsByProperty(propertyId: Long): Flow<List<RoomEntity>>

    @Query("SELECT * FROM rooms WHERE isOccupied = 0")
    fun getVacantRooms(): Flow<List<RoomEntity>>

    @Query("SELECT COUNT(*) FROM rooms")
    fun getTotalRooms(): Flow<Int>

    @Query("SELECT COUNT(*) FROM rooms WHERE isOccupied = 0")
    fun getVacantRoomsCount(): Flow<Int>

    @Query("SELECT * FROM rooms WHERE roomId = :roomId LIMIT 1")
    fun getRoomById(roomId: Long): Flow<RoomEntity?>
}
