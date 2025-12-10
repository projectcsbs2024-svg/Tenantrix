package com.global.tenantrix.data.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import com.global.tenantrix.data.entity.UtilityBillEntity

@Dao
interface UtilityBillDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBill(bill: UtilityBillEntity): Long

    @Update
    suspend fun updateBill(bill: UtilityBillEntity)

    @Delete
    suspend fun deleteBill(bill: UtilityBillEntity)

    @Query("""
        SELECT * FROM utility_bills
        WHERE roomId = :roomId
        ORDER BY year DESC, endMonth DESC
    """)
    fun getBillsByRoom(roomId: Long): Flow<List<UtilityBillEntity>>
}

