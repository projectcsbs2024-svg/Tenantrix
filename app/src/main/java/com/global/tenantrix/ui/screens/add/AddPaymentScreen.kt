package com.global.tenantrix.ui.screens.add

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.global.tenantrix.data.entity.RentPaymentEntity
import com.global.tenantrix.ui.components.CustomTextField
import com.global.tenantrix.ui.components.GradientHeader
import com.global.tenantrix.viewmodel.RentPaymentViewModel
import com.global.tenantrix.viewmodel.RoomViewModel
import com.global.tenantrix.viewmodel.TenantViewModel
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPaymentScreen(
    tenantId: Long,
    viewModel: RentPaymentViewModel = hiltViewModel(),
    tenantViewModel: TenantViewModel = hiltViewModel(),
    roomViewModel: RoomViewModel = hiltViewModel(),
    onPaymentSaved: () -> Unit,
    onBack: () -> Unit
) {

    // Fetch tenant
    val tenant by tenantViewModel.getTenantById(tenantId).collectAsState(initial = null)

    // STATES
    var rentAmount by remember { mutableStateOf("") }
    var amountPaid by remember { mutableStateOf("") }
    var paymentMode by remember { mutableStateOf("") }
    var remarks by remember { mutableStateOf("") }

    var selectedYear by remember { mutableStateOf(Calendar.getInstance().get(Calendar.YEAR)) }
    var selectedMonth by remember { mutableStateOf(Calendar.getInstance().get(Calendar.MONTH) + 1) }
    var paymentDate by remember { mutableStateOf(System.currentTimeMillis()) }

    val scroll = rememberScrollState()

    // Auto-fill rent based on ROOM
    LaunchedEffect(tenant) {
        tenant?.roomId?.let { roomId ->
            roomViewModel.getRoomById(roomId).collect { room ->
                room?.let {
                    rentAmount = it.rentAmount.toString()
                }
            }
        }
    }

    Scaffold(
        topBar = {
            GradientHeader(
                title = "Add Payment",
                onBack = onBack
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(scroll)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                shadowElevation = 6.dp,
                tonalElevation = 2.dp,
                color = Color.White
            ) {

                Column(
                    modifier = Modifier.padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {

                    // ----------------- MONTH & YEAR -----------------
                    MonthYearPicker(
                        selectedMonth = selectedMonth,
                        selectedYear = selectedYear,
                        onMonthChange = { selectedMonth = it },
                        onYearChange = { selectedYear = it }
                    )

                    // ----------------- RENT AMOUNT -----------------
                    CustomTextField(
                        value = rentAmount,
                        onValueChange = { rentAmount = it },
                        label = "Rent Amount",
                        keyboardType = KeyboardType.Number
                    )

                    // ----------------- AMOUNT PAID -----------------
                    CustomTextField(
                        value = amountPaid,
                        onValueChange = { amountPaid = it },
                        label = "Amount Paid",
                        keyboardType = KeyboardType.Number
                    )

                    // ----------------- PAYMENT MODE -----------------
                    CustomTextField(
                        value = paymentMode,
                        onValueChange = { paymentMode = it },
                        label = "Payment Method (Cash, UPI, Bank, etc.)"
                    )

                    // ----------------- REMARKS -----------------
                    CustomTextField(
                        value = remarks,
                        onValueChange = { remarks = it },
                        label = "Remarks (optional)"
                    )

                    // ----------------- SAVE BUTTON -----------------
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp),
                        onClick = {
                            if (amountPaid.isBlank() || paymentMode.isBlank()) return@Button

                            val entity = RentPaymentEntity(
                                tenantId = tenantId,
                                month = selectedMonth,
                                year = selectedYear,
                                rentAmount = rentAmount.toDoubleOrNull() ?: 0.0,
                                amountPaid = amountPaid.toDoubleOrNull() ?: 0.0,
                                paymentMode = paymentMode,
                                paymentDate = paymentDate,
                                remarks = remarks.ifBlank { null }
                            )

                            viewModel.addPayment(entity)
                            onPaymentSaved()
                        }
                    ) {
                        Text(
                            text = "Save Payment",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}


/****************************************************
Month-Year Picker Component
 ****************************************************/
@Composable
fun MonthYearPicker(
    selectedMonth: Int,
    selectedYear: Int,
    onMonthChange: (Int) -> Unit,
    onYearChange: (Int) -> Unit
) {
    val months = listOf(
        "Jan","Feb","Mar","Apr","May","Jun",
        "Jul","Aug","Sep","Oct","Nov","Dec"
    )

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {

        Text("Select Month & Year", fontWeight = FontWeight.Bold, fontSize = 16.sp)

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            // Month Dropdown
            var expandedMonth by remember { mutableStateOf(false) }

            Box {
                OutlinedButton(onClick = { expandedMonth = true }) {
                    Text(months[selectedMonth - 1])
                }
                DropdownMenu(
                    expanded = expandedMonth,
                    onDismissRequest = { expandedMonth = false }
                ) {
                    months.forEachIndexed { index, m ->
                        DropdownMenuItem(
                            text = { Text(m) },
                            onClick = {
                                onMonthChange(index + 1)
                                expandedMonth = false
                            }
                        )
                    }
                }
            }

            // Year Dropdown
            var expandedYear by remember { mutableStateOf(false) }
            val years = (2020..2030).toList()

            Box {
                OutlinedButton(onClick = { expandedYear = true }) {
                    Text(selectedYear.toString())
                }
                DropdownMenu(
                    expanded = expandedYear,
                    onDismissRequest = { expandedYear = false }
                ) {
                    years.forEach { y ->
                        DropdownMenuItem(
                            text = { Text(y.toString()) },
                            onClick = {
                                onYearChange(y)
                                expandedYear = false
                            }
                        )
                    }
                }
            }
        }
    }
}
