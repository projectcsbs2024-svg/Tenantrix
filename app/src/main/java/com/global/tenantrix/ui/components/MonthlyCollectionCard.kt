package com.global.tenantrix.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CurrencyRupee
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.Pending
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MonthlyCollectionCard(payments: List<Triple<String, Double, Double>>) {

    val totalExpected = payments.sumOf { it.second }
    val totalCollected = payments.sumOf { it.third }
    val remaining = totalExpected - totalCollected

    val progress = if (totalExpected == 0.0) 0f
    else (totalCollected / totalExpected).toFloat()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 160.dp),
        shape = RoundedCornerShape(18.dp),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Color(0xFF6B4EFF),
                            Color(0xFF8E72FF)
                        )
                    )
                )
                .padding(16.dp)
        ) {

            Column {

                Text(
                    text = "Monthly Collection",
                    color = Color.White,
                    fontSize = 20.sp
                )

                Spacer(Modifier.height(12.dp))

                // PROGRESS BAR
                LinearProgressIndicator(
                    progress = progress,
                    color = Color.White,
                    trackColor = Color.White.copy(alpha = 0.2f),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .clip(RoundedCornerShape(4.dp))
                )

                Spacer(Modifier.height(16.dp))

                // 3 VALUE ROWS
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    StatItem(
                        icon = Icons.Default.CurrencyRupee,
                        label = "Expected",
                        value = "₹${totalExpected.toInt()}"
                    )

                    StatItem(
                        icon = Icons.Default.Payments,
                        label = "Collected",
                        value = "₹${totalCollected.toInt()}"
                    )

                    StatItem(
                        icon = Icons.Default.Pending,
                        label = "Pending",
                        value = "₹${remaining.toInt()}"
                    )
                }
            }
        }
    }
}

@Composable
private fun StatItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    value: String
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {

        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(30.dp)
        )

        Spacer(Modifier.height(4.dp))

        Text(label, color = Color.White.copy(alpha = 0.8f), fontSize = 13.sp)
        Text(value, color = Color.White, fontSize = 15.sp)
    }
}
