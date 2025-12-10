package com.global.tenantrix.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.global.tenantrix.data.entity.RentPaymentEntity
import com.global.tenantrix.data.repository.RentPaymentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RentPaymentViewModel @Inject constructor(
    private val repo: RentPaymentRepository
) : ViewModel() {

    /* ------------------- CRUD ------------------- */

    fun addPayment(
        payment: RentPaymentEntity,
        onPaymentInserted: (Long) -> Unit = {}
    ) {
        viewModelScope.launch {
            val id = repo.addPayment(payment)   // FIXED
            onPaymentInserted(id)
        }
    }

    fun updatePayment(payment: RentPaymentEntity) {
        viewModelScope.launch {
            repo.updatePayment(payment)
        }
    }

    fun deletePayment(payment: RentPaymentEntity) {
        viewModelScope.launch {
            repo.deletePayment(payment)
        }
    }

    /* ------------------- FETCH PAYMENTS ------------------- */

    fun getPayments(tenantId: Long): Flow<List<RentPaymentEntity>> {
        return repo.getPaymentsByTenant(tenantId)
    }

    suspend fun getPaymentForMonth(
        tenantId: Long,
        month: Int,
        year: Int
    ): RentPaymentEntity? {
        return repo.getPaymentForMonth(tenantId, month, year)
    }

    /* ------------------- MONTH STATUS ------------------- */

    fun getMonthlyStatus(
        tenantId: Long,
        year: Int
    ): Flow<Map<Int, RentStatus>> {

        return repo.getPaymentsByTenant(tenantId).map { list ->
            (1..12).associateWith { month ->

                val paymentsThisMonth = list
                    .filter { it.month == month && it.year == year }

                if (paymentsThisMonth.isEmpty()) {
                    RentStatus.UNPAID
                } else {
                    val totalPaid = paymentsThisMonth.sumOf { it.amountPaid }
                    val expectedRent = paymentsThisMonth.first().rentAmount

                    when {
                        totalPaid == 0.0 -> RentStatus.UNPAID
                        totalPaid < expectedRent -> RentStatus.PARTIAL
                        else -> RentStatus.PAID
                    }
                }
            }
        }
    }

    /* ------------------- SUMMARY ------------------- */

    suspend fun getTotalPaidForMonth(
        tenantId: Long,
        month: Int,
        year: Int
    ): Double {
        return repo.getTotalPaidForMonth(tenantId, month, year)
    }

    suspend fun getTotalCollected(month: Int, year: Int): Double {
        return repo.getTotalCollected(month, year)
    }
}

/* ------------------- ENUM: STATUS ------------------- */

enum class RentStatus {
    PAID,
    PARTIAL,
    UNPAID
}
