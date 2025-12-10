package com.global.tenantrix.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.global.tenantrix.ui.components.*
import com.global.tenantrix.ui.theme.*

@Composable
fun NotificationSettingsScreen(
    onBack: () -> Unit,                 // ✅ FIX ADDED
    onNavigatePlaces: () -> Unit,
    onNavigateDashboard: () -> Unit,
    onNavigateTasks: () -> Unit
) {
    var rentReminder by remember { mutableStateOf(true) }
    var dueDateReminder by remember { mutableStateOf(false) }
    var backupReminder by remember { mutableStateOf(false) }

    var selectedChannel by remember { mutableStateOf("Push Notification") }
    val channels = listOf("Push Notification", "SMS", "Email")

    var reminderDays by remember { mutableStateOf(3f) }

    Scaffold(
        bottomBar = {
            BottomBar(
                selected = 2,
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

            GradientHeader(title = "Notification Settings", onBack = onBack)

            Spacer(Modifier.height(16.dp))

            SectionCard {
                Text("General Notifications", style = AppTypography.titleSmall)
                ToggleRow("Rent Due Reminder", rentReminder) { rentReminder = it }
                ToggleRow("Payment Due Date", dueDateReminder) { dueDateReminder = it }
                ToggleRow("Backup Reminder", backupReminder) { backupReminder = it }
            }

            Spacer(Modifier.height(16.dp))

            SectionCard {
                Text("Notification Channel", style = AppTypography.titleSmall)
                channels.forEach { option ->
                    Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                        RadioButton(selected = selectedChannel == option, onClick = { selectedChannel = option })
                        Spacer(Modifier.width(8.dp))
                        Text(option)
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            SectionCard {
                Text("Reminder Interval", style = AppTypography.titleSmall)
                Text("Reminder $reminderDays days before")
                Slider(
                    value = reminderDays,
                    onValueChange = { reminderDays = it },
                    valueRange = 1f..10f,
                    steps = 8
                )
            }

            Spacer(Modifier.height(16.dp))

            ExpandableCard("Advanced Settings") {
                ToggleRow("Silent Mode", false, {})
                ToggleRow("High Priority Alerts", true, {})
                RoundedInputField("", {}, "Custom Reminder Message")
            }
        }
    }
}
