package com.global.tenantrix.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.global.tenantrix.data.dao.*
import com.global.tenantrix.data.entity.*

@Database(
    entities = [
        TenantEntity::class,
        PropertyEntity::class,
        RoomEntity::class,
        RentPaymentEntity::class,
        UtilityBillEntity::class,
        PropertyExpenseEntity::class,
        AppSettingEntity::class,
        PaymentMethodEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun tenantDao(): TenantDao
    abstract fun roomDao(): RoomDao
    abstract fun propertyDao(): PropertyDao
    abstract fun rentPaymentDao(): RentPaymentDao
    abstract fun utilityBillDao(): UtilityBillDao
    abstract fun expenseDao(): PropertyExpenseDao
    abstract fun appSettingDao(): AppSettingDao
    abstract fun paymentDao(): PaymentDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "tenantrix_db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}
