package com.global.tenantrix.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.global.tenantrix.data.entity.RentPaymentEntity
import com.global.tenantrix.data.repository.PaymentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaymentViewModel @Inject constructor(
    private val repository: PaymentRepository
) : ViewModel() {

    private val _payments = MutableStateFlow<List<RentPaymentEntity>>(emptyList())
    val payments: StateFlow<List<RentPaymentEntity>> = _payments

    fun loadPayments(tenantId: Long) {
        viewModelScope.launch {
            repository.getPaymentsForTenant(tenantId).collect { list ->
                _payments.value = list
            }
        }
    }

    fun addPayment(entity: RentPaymentEntity) =
        viewModelScope.launch { repository.insertPayment(entity) }

    fun updatePayment(entity: RentPaymentEntity) =
        viewModelScope.launch { repository.updatePayment(entity) }

    fun deletePayment(entity: RentPaymentEntity) =
        viewModelScope.launch { repository.deletePayment(entity) }
}
