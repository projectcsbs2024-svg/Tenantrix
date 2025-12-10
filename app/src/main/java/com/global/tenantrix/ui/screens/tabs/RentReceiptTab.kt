package com.global.tenantrix.ui.screens.tabs

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.global.tenantrix.ui.components.MonthPicker
import com.global.tenantrix.ui.components.MonthlyCollectionCard
import com.global.tenantrix.ui.components.PaymentStatusCard
import com.global.tenantrix.ui.components.PrimaryButton
import com.global.tenantrix.ui.components.ShareDialog
import com.global.tenantrix.util.PdfGenerator
import com.global.tenantrix.viewmodel.RentPaymentViewModel
import com.global.tenantrix.viewmodel.RoomViewModel
import com.global.tenantrix.viewmodel.TenantViewModel
import kotlinx.coroutines.flow.firstOrNull
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun RentReceiptTab(propertyId: Long) {

    val roomVM: RoomViewModel = hiltViewModel()
    val tenantVM: TenantViewModel = hiltViewModel()
    val paymentVM: RentPaymentViewModel = hiltViewModel()

    val context = LocalContext.current

    val rooms by roomVM.getRooms(propertyId).collectAsState(initial = emptyList())

    var tenantPayments by remember { mutableStateOf<List<Triple<String, Double, Double>>>(emptyList()) }

    var selectedMonth by remember { mutableStateOf(Calendar.getInstance().get(Calendar.MONTH) + 1) }
    var selectedYear by remember { mutableStateOf(Calendar.getInstance().get(Calendar.YEAR)) }

    var pdfFileToShare by remember { mutableStateOf<java.io.File?>(null) }

    /**
     * FIXED: DO NOT USE collectAsState() INSIDE LaunchedEffect.
     * We now use suspend collection via firstOrNull()
     */
    LaunchedEffect(rooms, selectedMonth, selectedYear) {
        val list = mutableListOf<Triple<String, Double, Double>>()

        for (room in rooms) {

            // Get tenant synchronously (suspend)
            val tenant = tenantVM.getTenantById(room.roomId).firstOrNull()
            if (tenant != null) {

                val expectedRent = room.rentAmount

                val paid = paymentVM.getTotalPaidForMonth(
                    tenantId = tenant.tenantId,
                    month = selectedMonth,
                    year = selectedYear
                )

                list.add(Triple(tenant.name, expectedRent, paid))
            }
        }

        tenantPayments = list
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {

        MonthPicker(
            currentMonth = selectedMonth,
            currentYear = selectedYear,
            onMonthChanged = { selectedMonth = it },
            onYearChanged = { selectedYear = it }
        )

        Spacer(Modifier.height(12.dp))

        MonthlyCollectionCard(tenantPayments)

        Spacer(Modifier.height(16.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            items(tenantPayments) { (tenantName, expected, paid) ->
                PaymentStatusCard(
                    tenantName = tenantName,
                    expectedRent = expected,
                    paidAmount = paid
                )
            }
        }

        Spacer(Modifier.height(20.dp))

        PrimaryButton(
            text = "Generate Monthly Receipt PDF",
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                if (tenantPayments.isNotEmpty()) {

                    val monthName = SimpleDateFormat("MMMM", Locale.getDefault()).format(
                        SimpleDateFormat("M").parse(selectedMonth.toString())!!
                    )

                    val today = SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(Date())

                    val file = PdfGenerator.generateRentReceipt(
                        context = context,
                        tenantName = "All Tenants",
                        propertyName = "Property #$propertyId",
                        month = monthName,
                        year = selectedYear,
                        rentAmount = tenantPayments.sumOf { it.second },
                        amountPaid = tenantPayments.sumOf { it.third },
                        paymentMethod = "Mixed",
                        paymentDate = today,
                        notes = "Auto-generated monthly summary receipt.",
                        fineAmount = 0.0
                    )

                    if (file != null) pdfFileToShare = file
                }
            }
        )
    }

    pdfFileToShare?.let { file ->
        ShareDialog(file = file, onDismiss = { pdfFileToShare = null })
    }
}
