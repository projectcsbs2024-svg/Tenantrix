package com.global.tenantrix.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.Print
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.PictureAsPdf
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.global.tenantrix.ui.components.*
import com.global.tenantrix.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShareReceiptSheet(
    onDismiss: () -> Unit,
    onShare: (String) -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        containerColor = Purple10
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {

            /** HANDLE BAR */
            Box(
                Modifier
                    .size(width = 50.dp, height = 6.dp)
                    .background(Color.Gray, RoundedCornerShape(10.dp))
                    .align(Alignment.CenterHorizontally)
            )

            Spacer(Modifier.height(20.dp))

            Text(
                text = "Share Receipt",
                style = AppTypography.titleMedium
            )

            Spacer(Modifier.height(20.dp))

            ShareGrid(
                onOptionSelected = { option ->
                    onShare(option)
                }
            )

            Spacer(Modifier.height(20.dp))

            ExpandableCard(title = "Advanced Options") {
                RoundedInputField(
                    value = "",
                    onValueChange = {},
                    label = "Custom Message"
                )
                Spacer(Modifier.height(12.dp))

                ToggleRow(
                    title = "Send Copy to Email",
                    checked = false,
                    onCheckedChange = {}
                )
            }

            Spacer(Modifier.height(30.dp))
        }
    }
}

@Composable
fun ShareGrid(
    onOptionSelected: (String) -> Unit
) {
    val options = listOf(
        ShareOption("WhatsApp", Icons.Default.Share),
        ShareOption("Email", Icons.Default.Email),
        ShareOption("SMS", Icons.Default.Message),
        ShareOption("PDF", Icons.Default.PictureAsPdf),
        ShareOption("Print", Icons.Default.Print),
        ShareOption("More", Icons.Default.Share)
    )

    Column {
        val rows = options.chunked(3)

        rows.forEach { row ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                row.forEach { item ->
                    ShareButton(item = item, onOptionSelected)
                }
            }
        }
    }
}

data class ShareOption(val title: String, val icon: Any)

@Composable
fun ShareButton(
    item: ShareOption,
    onClick: (String) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(90.dp)
    ) {
        Surface(
            color = Purple20,
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier
                .size(70.dp)
                .clickable { onClick(item.title) }
        ) {
            Box(contentAlignment = Alignment.Center) {
                when (item.icon) {
                    is androidx.compose.ui.graphics.vector.ImageVector ->
                        Icon(item.icon, contentDescription = item.title, tint = Purple80)
                }
            }
        }

        Spacer(Modifier.height(8.dp))

        Text(item.title, style = AppTypography.bodySmall)
    }
}
