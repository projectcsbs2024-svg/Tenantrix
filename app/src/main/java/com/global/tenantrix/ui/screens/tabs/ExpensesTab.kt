package com.global.tenantrix.ui.screens.tabs

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.global.tenantrix.ui.components.*
import com.global.tenantrix.ui.screens.ExpenseUI
import com.global.tenantrix.ui.theme.*

@Composable
fun ExpensesTab(expenses: List<ExpenseUI>) {

    val total = expenses.sumOf { it.amount }

    Column(
        Modifier
            .fillMaxSize()
            .padding(horizontal = 12.dp)
    ) {

        /** TOTAL CARD */
        SectionCard {
            Text("Total Expense", style = AppTypography.bodySmall)
            Spacer(Modifier.height(8.dp))
            Text("₹$total", style = AppTypography.titleMedium)
        }

        Spacer(Modifier.height(12.dp))

        /** EXPENSE LIST */
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(expenses) { item ->
                ExpenseCard(item)
            }
        }
    }
}

/** EXPENSE CARD */
@Composable
fun ExpenseCard(expense: ExpenseUI) {
    SectionCard {
        Column(Modifier.fillMaxWidth()) {

            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(expense.title, style = AppTypography.titleSmall)
                Text("₹${expense.amount}", style = AppTypography.titleSmall)
            }

            Spacer(Modifier.height(8.dp))

            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                StatusChip(
                    text = expense.category,
                    color = Purple80
                )
                if (expense.ownerOnly) {
                    StatusChip(
                        text = "Owner Only",
                        color = ErrorRed
                    )
                }
            }
        }
    }
}
