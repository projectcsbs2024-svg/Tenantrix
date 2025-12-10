package com.global.tenantrix.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MonthPicker(
    currentMonth: Int,
    currentYear: Int,
    onMonthChanged: (Int) -> Unit,
    onYearChanged: (Int) -> Unit
) {
    val months = listOf(
        "January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December"
    )

    val years = (2020..2035).toList()

    var expandedYear by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        /** LEFT ARROW (Previous Month) */
        IconButton(
            onClick = {
                var newMonth = currentMonth - 1
                var newYear = currentYear

                if (newMonth < 1) {
                    newMonth = 12
                    newYear -= 1
                    onYearChanged(newYear)
                }

                onMonthChanged(newMonth)
            }
        ) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowLeft,
                contentDescription = "Previous Month",
                tint = MaterialTheme.colorScheme.primary
            )
        }

        /** Current Month Name */
        Text(
            text = months[currentMonth - 1],
            style = MaterialTheme.typography.titleMedium
        )

        /** Year Dropdown */
        Box {
            OutlinedButton(
                onClick = { expandedYear = true }
            ) {
                Text(currentYear.toString())
            }

            DropdownMenu(
                expanded = expandedYear,
                onDismissRequest = { expandedYear = false }
            ) {
                years.forEach { year ->
                    DropdownMenuItem(
                        text = { Text(year.toString()) },
                        onClick = {
                            onYearChanged(year)
                            expandedYear = false
                        }
                    )
                }
            }
        }

        /** RIGHT ARROW (Next Month) */
        IconButton(
            onClick = {
                var newMonth = currentMonth + 1
                var newYear = currentYear

                if (newMonth > 12) {
                    newMonth = 1
                    newYear += 1
                    onYearChanged(newYear)
                }

                onMonthChanged(newMonth)
            }
        ) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = "Next Month",
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}
