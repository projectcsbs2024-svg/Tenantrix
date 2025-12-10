package com.global.tenantrix.ui.screens.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.global.tenantrix.viewmodel.*
import com.global.tenantrix.ui.components.GradientHeader
import com.global.tenantrix.viewmodel.RentStatus
import java.util.*

@Composable
fun TenantDetailsScreen(
    tenantId: Long,
    onBack: () -> Unit,
    onAddPayment: (Long) -> Unit,
    onOpenPaymentHistory: (Long) -> Unit,
    tenantViewModel: TenantViewModel = viewModel(),
    paymentViewModel: RentPaymentViewModel = viewModel(),
    roomViewModel: RoomViewModel = viewModel()
) {
    val tenant by tenantViewModel.getTenantById(tenantId).collectAsState(initial = null)

    val year = Calendar.getInstance().get(Calendar.YEAR)
    val monthlyStatus by paymentViewModel.getMonthlyStatus(tenantId, year)
        .collectAsState(initial = emptyMap())

    var roomRent by remember { mutableStateOf<Double?>(null) }

    // Load rent from room
    LaunchedEffect(tenant) {
        tenant?.roomId?.let { id ->
            roomViewModel.getRoomById(id).collect { room ->
                roomRent = room?.rentAmount
            }
        }
    }

    val scroll = rememberScrollState()

    Scaffold(
        topBar = {
            GradientHeader(
                title = "Tenant Details",
                onBack = onBack
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(scroll)
                .padding(16.dp)
        ) {

            if (tenant == null) {
                Text("Loading...", fontSize = 18.sp)
                return@Column
            }

            // ---------------- TENANT INFO CARD ----------------
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                shadowElevation = 4.dp,
                color = Color.White
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = tenant!!.name,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(6.dp))

                    Text("Phone: ${tenant!!.phone}", fontSize = 16.sp)
                    tenant!!.idProof?.let {
                        Text("ID Proof: $it", fontSize = 16.sp)
                    }
                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Room Rent: ₹${roomRent ?: 0.0}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF1976D2)
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // ---------------- MONTHLY STATUS ----------------
            Text(
                text = "Payment Status ($year)",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(10.dp))

            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                shadowElevation = 3.dp,
                color = Color.White
            ) {

                Column(modifier = Modifier.padding(16.dp)) {

                    val months = listOf(
                        "Jan", "Feb", "Mar", "Apr", "May", "Jun",
                        "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
                    )

                    for (i in 1..12) {
                        val status = monthlyStatus[i] ?: RentStatus.UNPAID

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(months[i - 1], fontSize = 16.sp)

                            Text(
                                text = status.name,
                                color = when (status) {
                                    RentStatus.PAID -> Color(0xFF2E7D32)
                                    RentStatus.PARTIAL -> Color(0xFFFF9800)
                                    RentStatus.UNPAID -> Color(0xFFD32F2F)
                                },
                                fontSize = 15.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }

                        Divider(thickness = 0.5.dp, color = Color.LightGray)
                    }
                }
            }

            Spacer(modifier = Modifier.height(25.dp))

            // ---------------- ACTION BUTTONS ----------------

            Button(
                onClick = { onAddPayment(tenantId) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Add Payment", fontSize = 16.sp)
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedButton(
                onClick = { onOpenPaymentHistory(tenantId) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("View Payment History", fontSize = 16.sp)
            }
        }
    }
}
