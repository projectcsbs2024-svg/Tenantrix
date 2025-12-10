package com.global.tenantrix.ui.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.global.tenantrix.ui.theme.*

@Composable
fun PrimaryButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        shape = AppShapes.medium,
        colors = ButtonDefaults.buttonColors(
            containerColor = Purple80,
            contentColor = Color.White
        )
    ) {
        Text(text = text, style = AppTypography.titleSmall)
    }
}
