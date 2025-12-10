package com.global.tenantrix.ui.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.global.tenantrix.ui.theme.*

@Composable
fun OutlineButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier,
        shape = AppShapes.medium,
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = Purple100
        )
    ) {
        Text(text = text, style = AppTypography.titleSmall)
    }
}
