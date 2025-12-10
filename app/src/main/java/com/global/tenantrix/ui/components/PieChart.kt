package com.global.tenantrix.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.global.tenantrix.ui.theme.AppTypography

data class PieSlice(
    val value: Float,
    val color: Color,
    val label: String
)

@Composable
fun PieChart(
    slices: List<PieSlice>,
    modifier: Modifier = Modifier.size(180.dp)
) {
    val total = slices.sumOf { it.value.toDouble() }.toFloat()
    var startAngle = -90f

    Canvas(modifier = modifier) {
        val diameter = size.minDimension
        val area = Size(diameter, diameter)
        val offset = Offset(
            (size.width - diameter) / 2f,
            (size.height - diameter) / 2f
        )

        slices.forEach { slice ->
            val sweep = if (total == 0f) 0f else (slice.value / total) * 360f

            drawArc(
                color = slice.color,
                startAngle = startAngle,
                sweepAngle = sweep,
                useCenter = true,
                size = area,
                topLeft = offset
            )

            startAngle += sweep
        }
    }
}

@Composable
fun PieChartLegend(slices: List<PieSlice>) {
    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        slices.forEach { slice ->
            Row(
                verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(14.dp)
                        .background(slice.color)
                )
                Spacer(Modifier.width(8.dp))
                Text(slice.label, style = AppTypography.bodyMedium)
            }
        }
    }
}
