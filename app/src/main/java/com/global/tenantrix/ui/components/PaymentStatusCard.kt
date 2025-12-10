package com.global.tenantrix.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PaymentStatusCard(
    tenantName: String,
    expectedRent: Double,
    paidAmount: Double
) {
    val status: String
    val statusColor: Color

    when {
        paidAmount == 0.0 -> {
            status = "Unpaid"
            statusColor = Color(0xFFE53935)  // Red
        }
        paidAmount < expectedRent -> {
            status = "Partial"
            statusColor = Color(0xFFFFA726)  // Orange
        }
        else -> {
            status = "Paid"
            statusColor = Color(0xFF43A047)  // Green
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {

            /** TOP ROW: Tenant Name + Status Badge */
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = tenantName,
                    style = MaterialTheme.typography.titleMedium.copy(fontSize = 18.sp),
                    fontWeight = FontWeight.SemiBold
                )

                StatusBadge(status = status, bgColor = statusColor)
            }

            Spacer(Modifier.height(8.dp))

            Text("Expected: ₹$expectedRent", style = MaterialTheme.typography.bodyMedium)
            Text("Paid: ₹$paidAmount", style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
fun StatusBadge(status: String, bgColor: Color) {
    Box(
        modifier = Modifier
            .background(bgColor.copy(alpha = 0.15f), shape = MaterialTheme.shapes.small)
            .padding(horizontal = 12.dp, vertical = 4.dp)
    ) {
        Text(
            text = status,
            color = bgColor,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp
        )
    }
}
