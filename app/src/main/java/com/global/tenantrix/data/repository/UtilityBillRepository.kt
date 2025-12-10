package com.global.tenantrix.data.repository

import kotlinx.coroutines.flow.Flow
import com.global.tenantrix.data.dao.UtilityBillDao
import com.global.tenantrix.data.entity.UtilityBillEntity

class UtilityBillRepository(
    private val dao: UtilityBillDao
) {

    fun getBillsByRoom(roomId: Long): Flow<List<UtilityBillEntity>> =
        dao.getBillsByRoom(roomId)


    suspend fun insertBill(bill: UtilityBillEntity) =
        dao.insertBill(bill)

    suspend fun updateBill(bill: UtilityBillEntity) =
        dao.updateBill(bill)

    suspend fun deleteBill(bill: UtilityBillEntity) =
        dao.deleteBill(bill)
}
