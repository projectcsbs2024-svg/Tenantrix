package com.global.tenantrix.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.global.tenantrix.ui.theme.*

data class BarEntry(
    val label: String,
    val value: Float,
    val color: Color
)

@Composable
fun BarChart(
    bars: List<BarEntry>,
    modifier: Modifier = Modifier
) {
    val max = bars.maxOfOrNull { it.value } ?: 1f

    Column(modifier = modifier) {
        bars.forEach { bar ->
            Column(Modifier.padding(vertical = 6.dp)) {
                Text(bar.label, style = AppTypography.bodySmall)
                Spacer(Modifier.height(4.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(20.dp)
                        .background(Purple20, AppShapes.small)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth(bar.value / max)
                            .background(bar.color, AppShapes.small)
                    )
                }
            }
        }
    }
}
