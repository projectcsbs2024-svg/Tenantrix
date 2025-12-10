package com.global.tenantrix.di

import android.content.Context
import androidx.room.Room
import com.global.tenantrix.data.database.AppDatabase
import com.global.tenantrix.data.dao.*
import com.global.tenantrix.data.repository.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // ---------------- DATABASE ----------------
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "tenantrix_db"
        ).build()

    // ---------------- DAOs ----------------
    @Provides fun provideTenantDao(db: AppDatabase): TenantDao = db.tenantDao()
    @Provides fun provideRoomDao(db: AppDatabase): RoomDao = db.roomDao()
    @Provides fun providePaymentDao(db: AppDatabase): PaymentDao = db.paymentDao()
    @Provides fun provideExpenseDao(db: AppDatabase): PropertyExpenseDao = db.expenseDao()

    @Provides fun provideRentPaymentDao(db: AppDatabase): RentPaymentDao = db.rentPaymentDao()

    // ⭐ REQUIRED — YOU WERE MISSING THIS
    @Provides fun providePropertyDao(db: AppDatabase): PropertyDao = db.propertyDao()

    // ---------------- REPOSITORIES ----------------
    @Provides @Singleton
    fun provideTenantRepository(dao: TenantDao): TenantRepository =
        TenantRepository(dao)

    @Provides @Singleton
    fun provideRoomRepository(dao: RoomDao): RoomRepository =
        RoomRepository(dao)

    @Provides @Singleton
    fun providePaymentRepository(dao: PaymentDao): PaymentRepository =
        PaymentRepository(dao)

    @Provides @Singleton
    fun provideExpenseRepository(dao: PropertyExpenseDao): ExpenseRepository =
        ExpenseRepository(dao)

    @Provides @Singleton
    fun provideRentPaymentRepository(dao: RentPaymentDao): RentPaymentRepository =
        RentPaymentRepository(dao)

    // ⭐ REQUIRED — YOU WERE MISSING THIS
    @Provides @Singleton
    fun providePropertyRepository(dao: PropertyDao): PropertyRepository =
        PropertyRepository(dao)
}
