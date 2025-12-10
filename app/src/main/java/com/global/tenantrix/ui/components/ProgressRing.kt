package com.global.tenantrix.ui.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ProgressRing(
    progress: Float,
    modifier: Modifier = Modifier
) {
    CircularProgressIndicator(
        progress = progress,
        strokeWidth = 6.dp,
        modifier = modifier
    )
}
