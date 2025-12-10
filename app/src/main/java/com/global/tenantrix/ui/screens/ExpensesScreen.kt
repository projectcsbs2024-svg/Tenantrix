package com.global.tenantrix.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.global.tenantrix.ui.components.*
import com.global.tenantrix.ui.screens.tabs.ExpensesTab
import com.global.tenantrix.ui.screens.tabs.MeterTab
import com.global.tenantrix.ui.theme.*

data class ExpenseUI(
    val id: Long,
    val title: String,
    val amount: Double,
    val category: String,
    val ownerOnly: Boolean
)

data class MeterReadingUI(
    val id: Long,
    val unitConsumed: Int,
    val billAmount: Double
)

@Composable
fun ExpensesScreen(
    expenses: List<ExpenseUI>,
    meterReadings: List<MeterReadingUI>,
    month: String,
    onMonthChange: (Boolean) -> Unit,
    onAddExpense: () -> Unit,
    onBack: () -> Unit,              // ✅ FIX ADDED
    onNavigateDashboard: () -> Unit,
    onNavigatePlaces: () -> Unit,
    onNavigateTasks: () -> Unit
) {
    var selectedTab by remember { mutableStateOf(0) }

    val tabs = listOf("Expenses", "Meter")

    Scaffold(
        floatingActionButton = {
            if (selectedTab == 0) {
                FloatingActionButton(
                    onClick = onAddExpense,
                    containerColor = Purple80,
                    contentColor = Color.White
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add Expense")
                }
            }
        },
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
            Modifier
                .fillMaxSize()
                .background(Purple10)
                .padding(padding)
        ) {

            GradientHeader(title = "Expenses", onBack = onBack)

            Spacer(Modifier.height(16.dp))

            MonthSelector(
                month = month,
                onPrevious = { onMonthChange(false) },
                onNext = { onMonthChange(true) }
            )

            Spacer(Modifier.height(12.dp))

            TabRow(selectedTabIndex = selectedTab, containerColor = Purple10) {
                tabs.forEachIndexed { index, text ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        selectedContentColor = Purple80,
                        unselectedContentColor = Purple40,
                        text = { Text(text) }
                    )
                }
            }

            Spacer(Modifier.height(12.dp))

            when (selectedTab) {
                0 -> ExpensesTab(expenses)
                1 -> MeterTab(meterReadings)
            }
        }
    }
}
