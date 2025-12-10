package com.global.tenantrix.data.repository

import kotlinx.coroutines.flow.Flow
import com.global.tenantrix.data.dao.PropertyExpenseDao
import com.global.tenantrix.data.entity.PropertyExpenseEntity

class PropertyExpenseRepository(
    private val expenseDao: PropertyExpenseDao
) {

    fun getExpenses(propertyId: Long): Flow<List<PropertyExpenseEntity>> =
        expenseDao.getExpenses(propertyId)

    suspend fun addExpense(expense: PropertyExpenseEntity) =
        expenseDao.insertExpense(expense)
}
