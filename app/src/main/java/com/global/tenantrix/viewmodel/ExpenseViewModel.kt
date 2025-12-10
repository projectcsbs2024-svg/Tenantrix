package com.global.tenantrix.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.global.tenantrix.data.entity.PropertyExpenseEntity
import com.global.tenantrix.data.repository.ExpenseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExpenseViewModel @Inject constructor(
    private val repository: ExpenseRepository
) : ViewModel() {

    fun getExpenses(propertyId: Long): StateFlow<List<PropertyExpenseEntity>> =
        repository.getExpenses(propertyId)
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                emptyList()
            )

    fun addExpense(expense: PropertyExpenseEntity) {
        viewModelScope.launch { repository.insertExpense(expense) }
    }

    fun updateExpense(expense: PropertyExpenseEntity) {
        viewModelScope.launch { repository.updateExpense(expense) }
    }

    fun deleteExpense(expense: PropertyExpenseEntity) {
        viewModelScope.launch { repository.deleteExpense(expense) }
    }
}
