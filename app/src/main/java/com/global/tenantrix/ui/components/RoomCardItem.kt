package com.global.tenantrix.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.global.tenantrix.ui.screens.RoomUI
import com.global.tenantrix.ui.theme.AppTypography
import com.global.tenantrix.ui.theme.Purple40
import com.global.tenantrix.ui.theme.Purple80

@Composable
fun RoomCardItem(
    room: RoomUI,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = Purple80.copy(alpha = 0.15f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Text("Room No: ${room.roomNo}", style = AppTypography.titleMedium, color = Purple40)
            Spacer(Modifier.height(4.dp))

            Text("Type: ${room.roomType}", style = AppTypography.bodyMedium)
            Text("Tenant: ${room.tenantName}", style = AppTypography.bodyMedium)

            Spacer(Modifier.height(6.dp))

            Text("Rent: ₹${room.rentAmount}", style = AppTypography.bodySmall)
            Text("Balance: ₹${room.balance}", style = AppTypography.bodySmall)
        }
    }
}
