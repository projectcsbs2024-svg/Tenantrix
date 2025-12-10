package com.global.tenantrix.ui.screens.details

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.global.tenantrix.viewmodel.RentPaymentViewModel
import com.global.tenantrix.ui.components.GradientHeader
import com.global.tenantrix.data.entity.RentPaymentEntity
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun PaymentHistoryScreen(
    tenantId: Long,
    onBack: () -> Unit,
    viewModel: RentPaymentViewModel = viewModel()
) {
    val payments by viewModel.getPayments(tenantId).collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            GradientHeader(
                title = "Payment History",
                onBack = onBack
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {

            if (payments.isEmpty()) {
                Text(
                    text = "No payments recorded.",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
                return@Column
            }

            payments.forEach { payment ->
                PaymentHistoryItem(payment)
                Spacer(modifier = Modifier.height(14.dp))
            }
        }
    }
}


/****************************************************
PAYMENT HISTORY CARD
 ****************************************************/
@Composable
fun PaymentHistoryItem(payment: RentPaymentEntity) {

    val monthNames = listOf(
        "Jan", "Feb", "Mar", "Apr", "May", "Jun",
        "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
    )

    val paymentDateFormatted = remember(payment.paymentDate) {
        val sdf = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        sdf.format(Date(payment.paymentDate))
    }

    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        shadowElevation = 5.dp,
        color = Color.White
    ) {

        Column(modifier = Modifier.padding(16.dp)) {

            // Month & Year
            Text(
                text = "${monthNames[payment.month - 1]} ${payment.year}",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(6.dp))

            // Rent & Paid Amount
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Column {
                    Text("Rent Amount", fontWeight = FontWeight.Medium)
                    Text("₹${payment.rentAmount}", fontSize = 16.sp)
                }

                Column(horizontalAlignment = androidx.compose.ui.Alignment.End) {
                    Text("Paid", fontWeight = FontWeight.Medium)
                    Text(
                        "₹${payment.amountPaid}",
                        fontSize = 16.sp,
                        color = if (payment.amountPaid >= payment.rentAmount)
                            Color(0xFF2E7D32) else Color(0xFFD32F2F)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Payment Mode
            Text("Payment Mode: ${payment.paymentMode}", fontSize = 14.sp)

            // Payment Date
            Text("Date: $paymentDateFormatted", fontSize = 14.sp)

            // Remarks
            payment.remarks?.let {
                if (it.isNotBlank()) {
                    Text("Remarks: $it", fontSize = 14.sp)
                }
            }
        }
    }
}
