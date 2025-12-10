package com.global.tenantrix.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.global.tenantrix.data.entity.UtilityBillEntity
import com.global.tenantrix.data.repository.RoomRepository
import com.global.tenantrix.data.repository.TenantRepository
import com.global.tenantrix.data.repository.UtilityBillRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject
import com.global.tenantrix.data.repository.PaymentRepository


class UtilityBillViewModel(
    private val repository: UtilityBillRepository
) : ViewModel() {

    private val _bills = MutableStateFlow<List<UtilityBillEntity>>(emptyList())
    val bills: StateFlow<List<UtilityBillEntity>> = _bills

    fun loadBills(roomId: Long) {
        viewModelScope.launch {
            repository.getBillsByRoom(roomId).collect { list ->
                _bills.value = list
            }
        }
    }

    fun addBill(entity: UtilityBillEntity) =
        viewModelScope.launch { repository.insertBill(entity) }

    fun updateBill(entity: UtilityBillEntity) =
        viewModelScope.launch { repository.updateBill(entity) }

    fun deleteBill(entity: UtilityBillEntity) =
        viewModelScope.launch { repository.deleteBill(entity) }
}



@HiltViewModel
class DashboardViewModel @Inject constructor(
    tenantRepo: TenantRepository,
    roomRepo: RoomRepository,
    paymentRepo: PaymentRepository
) : ViewModel() {

    val totalTenants = tenantRepo.getTotalTenantCount()
    val totalRooms = roomRepo.getTotalRooms()
    val vacantRooms = roomRepo.getVacantRoomsCount()

    private val calendar = Calendar.getInstance()
    private val currentMonth = calendar.get(Calendar.MONTH) + 1
    private val currentYear = calendar.get(Calendar.YEAR)

    val monthlyCollection = paymentRepo.getMonthlyCollection(currentMonth, currentYear)
}