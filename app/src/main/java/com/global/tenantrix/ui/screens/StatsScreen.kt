package com.global.tenantrix.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.global.tenantrix.ui.components.*
import com.global.tenantrix.ui.theme.*

/* ---------------- Data Models used by StatsScreen ---------------- */

data class StatsSliceUI(val label: String, val amount: Float, val color: Color)
data class MethodStatUI(val label: String, val amount: Float, val color: Color)

/* ---------------- Temporary Sample Data (replace later with real data) ---------------- */

val sampleCategoryStats = listOf(
    StatsSliceUI("Rent", 5000f, Purple80),
    StatsSliceUI("Electricity", 1200f, Purple60),
    StatsSliceUI("Water", 300f, Purple40)
)

val sampleMethodStats = listOf(
    MethodStatUI("UPI", 4000f, Purple80),
    MethodStatUI("Cash", 1500f, Purple60),
    MethodStatUI("Bank", 1000f, Purple40)
)

/* ---------------- STATS SCREEN ---------------- */

@Composable
fun StatsScreen(
    month: String,
    onMonthChange: (Boolean) -> Unit,
    categoryStats: List<StatsSliceUI>,
    methodStats: List<MethodStatUI>,
    onBack: () -> Unit,
    onNavigatePlaces: () -> Unit,
    onNavigateDashboard: () -> Unit,
    onNavigateTasks: () -> Unit
) {
    Scaffold(
        bottomBar = {
            BottomBar(
                selected = 1,
                onPlaces = onNavigatePlaces,
                onDashboard = onNavigateDashboard,
                onTasks = onNavigateTasks
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Purple10)
                .padding(padding)
        ) {

            GradientHeader(title = "Statistics", onBack = onBack)


            Spacer(Modifier.height(12.dp))

            /* ---------------- MONTH SELECTOR ---------------- */
            MonthSelector(
                month = month,
                onPrevious = { onMonthChange(false) },
                onNext = { onMonthChange(true) }
            )

            Spacer(Modifier.height(16.dp))

            /* ---------------- PIE CHART CARD ---------------- */
            SectionCard {
                Text("Category Breakdown", style = AppTypography.titleSmall)
                Spacer(Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    PieChart(
                        slices = categoryStats.map {
                            PieSlice(it.amount, it.color, it.label)
                        }
                    )
                    PieChartLegend(
                        slices = categoryStats.map {
                            PieSlice(it.amount, it.color, it.label)
                        }
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            /* ---------------- PAYMENT METHODS BAR CHART ---------------- */
            SectionCard {
                Text("Payment Methods", style = AppTypography.titleSmall)
                Spacer(Modifier.height(12.dp))

                BarChart(
                    bars = methodStats.map {
                        BarEntry(it.label, it.amount, it.color)
                    }
                )
            }

            Spacer(Modifier.height(16.dp))

            /* ---------------- MONTHLY REPORT ---------------- */
            ExpandableCard(title = "Monthly Report") {

                Text(
                    text = "Generated Report Summary:",
                    style = AppTypography.bodyMedium
                )

                Spacer(Modifier.height(8.dp))

                Text(
                    text =
                        "• Total Income: ₹${categoryStats.sumOf { it.amount.toDouble() }}\n" +
                                "• Total Paid via UPI: ₹${methodStats.find { it.label == "UPI" }?.amount ?: 0f}",
                    style = AppTypography.bodySmall
                )
            }

            Spacer(Modifier.height(16.dp))
        }
    }
}
