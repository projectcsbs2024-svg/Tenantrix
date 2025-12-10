package com.global.tenantrix.data.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import com.global.tenantrix.data.entity.PropertyExpenseEntity

@Dao
interface PropertyExpenseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExpense(expense: PropertyExpenseEntity): Long

    @Update
    suspend fun updateExpense(expense: PropertyExpenseEntity)

    @Delete
    suspend fun deleteExpense(expense: PropertyExpenseEntity)

    @Query("SELECT * FROM property_expenses WHERE propertyId = :propertyId ORDER BY expenseDate DESC")
    fun getExpenses(propertyId: Long): Flow<List<PropertyExpenseEntity>>

    @Query("SELECT IFNULL(SUM(amount),0) FROM property_expenses WHERE propertyId = :propertyId")
    suspend fun getTotalExpenses(propertyId: Long): Double
}

