package com.global.tenantrix.data.repository

import kotlinx.coroutines.flow.Flow
import com.global.tenantrix.data.dao.PropertyExpenseDao
import com.global.tenantrix.data.entity.PropertyExpenseEntity

class ExpenseRepository(
    private val expenseDao: PropertyExpenseDao
) {

    fun getExpenses(propertyId: Long): Flow<List<PropertyExpenseEntity>> =
        expenseDao.getExpenses(propertyId)

    suspend fun insertExpense(expense: PropertyExpenseEntity) {
        expenseDao.insertExpense(expense)
    }

    suspend fun updateExpense(expense: PropertyExpenseEntity) {
        expenseDao.updateExpense(expense)
    }

    suspend fun deleteExpense(expense: PropertyExpenseEntity) {
        expenseDao.deleteExpense(expense)
    }

    suspend fun getTotalExpenses(propertyId: Long): Double {
        return expenseDao.getTotalExpenses(propertyId)
    }
}
