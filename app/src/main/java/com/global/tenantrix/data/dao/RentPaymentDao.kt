package com.global.tenantrix.data.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import com.global.tenantrix.data.entity.RentPaymentEntity

@Dao
interface RentPaymentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPayment(payment: RentPaymentEntity): Long

    @Update
    suspend fun updatePayment(payment: RentPaymentEntity)

    @Delete
    suspend fun deletePayment(payment: RentPaymentEntity)

    @Query("""
        SELECT * FROM rent_payments
        WHERE tenantId = :tenantId
        ORDER BY year DESC, month DESC
    """)
    fun getPaymentsByTenant(tenantId: Long): Flow<List<RentPaymentEntity>>

    @Query("""
        SELECT * FROM rent_payments
        WHERE tenantId = :tenantId AND month = :month AND year = :year
        LIMIT 1
    """)
    suspend fun getPaymentForMonth(tenantId: Long, month: Int, year: Int): RentPaymentEntity?

    @Query("SELECT SUM(amountPaid) FROM rent_payments WHERE month = :month AND year = :year")
    suspend fun getTotalCollected(month: Int, year: Int): Double?

    @Query("SELECT * FROM rent_payments WHERE month = :month AND year = :year")
    fun getPaymentsForMonth(month: Int, year: Int): Flow<List<RentPaymentEntity>>

    @Query("SELECT SUM(amountPaid) FROM rent_payments WHERE tenantId = :tenantId AND month = :month AND year = :year")
    suspend fun getTotalPaidForMonth(tenantId: Long, month: Int, year: Int): Double?
}
