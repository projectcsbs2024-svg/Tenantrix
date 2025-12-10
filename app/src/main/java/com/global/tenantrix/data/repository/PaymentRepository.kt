package com.global.tenantrix.data.repository

import com.global.tenantrix.data.dao.PaymentDao
import com.global.tenantrix.data.entity.RentPaymentEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PaymentRepository @Inject constructor(
    private val paymentDao: PaymentDao
) {

    fun getPaymentsForTenant(tenantId: Long): Flow<List<RentPaymentEntity>> {
        return paymentDao.getPaymentsForTenant(tenantId)
    }

    fun getMonthlyCollection(month: Int, year: Int): Flow<Double> {
        return paymentDao.getMonthlyCollection(month, year)
    }

    suspend fun insertPayment(entity: RentPaymentEntity) {
        paymentDao.insertPayment(entity)
    }

    suspend fun updatePayment(entity: RentPaymentEntity) {
        paymentDao.updatePayment(entity)
    }

    suspend fun deletePayment(entity: RentPaymentEntity) {
        paymentDao.deletePayment(entity)
    }
}
