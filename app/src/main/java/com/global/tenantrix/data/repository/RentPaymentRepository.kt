package com.global.tenantrix.data.repository

import kotlinx.coroutines.flow.Flow
import com.global.tenantrix.data.dao.RentPaymentDao
import com.global.tenantrix.data.entity.RentPaymentEntity
import javax.inject.Inject

class RentPaymentRepository @Inject constructor(
    private val rentPaymentDao: RentPaymentDao
) {

    suspend fun addPayment(payment: RentPaymentEntity): Long =
        rentPaymentDao.insertPayment(payment)


    suspend fun updatePayment(payment: RentPaymentEntity) =
        rentPaymentDao.updatePayment(payment)

    suspend fun deletePayment(payment: RentPaymentEntity) =
        rentPaymentDao.deletePayment(payment)

    fun getPaymentsByTenant(tenantId: Long) =
        rentPaymentDao.getPaymentsByTenant(tenantId)

    suspend fun getPaymentForMonth(tenantId: Long, month: Int, year: Int) =
        rentPaymentDao.getPaymentForMonth(tenantId, month, year)

    fun getPaymentsForMonth(month: Int, year: Int) =
        rentPaymentDao.getPaymentsForMonth(month, year)

    suspend fun getTotalPaidForMonth(tenantId: Long, month: Int, year: Int): Double =
        rentPaymentDao.getTotalPaidForMonth(tenantId, month, year) ?: 0.0

    suspend fun getTotalCollected(month: Int, year: Int): Double =
        rentPaymentDao.getTotalCollected(month, year) ?: 0.0
}
