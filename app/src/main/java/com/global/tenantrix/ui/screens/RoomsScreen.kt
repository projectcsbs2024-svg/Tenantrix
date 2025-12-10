package com.global.tenantrix.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.global.tenantrix.ui.components.*
import com.global.tenantrix.ui.theme.*

data class RoomUI(
    val id: Long,
    val roomNo: String,
    val roomType: String,
    val rentAmount: Double,
    val balance: Double,
    val tenantName: String
)

@Composable
fun RoomsScreen(
    rooms: List<RoomUI>,
    onAddRoom: () -> Unit,
    onOpenRoom: (Long) -> Unit,
    onBack: () -> Unit,               // ✅ FIX ADDED
    onNavigatePlaces: () -> Unit,
    onNavigateDashboard: () -> Unit,
    onNavigateTasks: () -> Unit
) {
    var query by remember { mutableStateOf("") }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onAddRoom, containerColor = Purple80) {
                Icon(Icons.Default.Add, contentDescription = null)
            }
        },
        bottomBar = {
            BottomBar(
                selected = 0,
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

            GradientHeader(title = "Rooms", onBack = onBack)

            Spacer(Modifier.height(12.dp))

            SearchBarRounded(query, { query = it })

            Spacer(Modifier.height(12.dp))

            val filtered = rooms.filter {
                it.roomNo.contains(query, true) ||
                        it.tenantName.contains(query, true) ||
                        it.roomType.contains(query, true)
            }

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(bottom = 100.dp)
            ) {
                items(filtered) { room ->
                    RoomCardItem(room) { onOpenRoom(room.id) }
                }
            }
        }
    }
}
