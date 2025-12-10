package com.global.tenantrix.data.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import com.global.tenantrix.data.entity.RentPaymentEntity

@Dao
interface PaymentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPayment(payment: RentPaymentEntity)

    @Update
    suspend fun updatePayment(payment: RentPaymentEntity)

    @Delete
    suspend fun deletePayment(payment: RentPaymentEntity)

    @Query("""
    SELECT IFNULL(SUM(rentAmount), 0)
    FROM rent_payments
    WHERE month = :month AND year = :year
""")
    fun getMonthlyCollection(month: Int, year: Int): Flow<Double>

    @Query("""
    SELECT * FROM rent_payments
    WHERE tenantId = :tenantId
    ORDER BY paymentDate DESC
""")
    fun getPaymentsForTenant(tenantId: Long): Flow<List<RentPaymentEntity>>

}


