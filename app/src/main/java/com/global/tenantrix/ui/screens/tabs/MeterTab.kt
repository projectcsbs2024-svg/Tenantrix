package com.global.tenantrix.ui.screens.tabs

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.global.tenantrix.ui.components.*
import com.global.tenantrix.ui.screens.MeterReadingUI
import com.global.tenantrix.ui.theme.*

@Composable
fun MeterTab(readings: List<MeterReadingUI>) {

    Column(
        Modifier
            .fillMaxSize()
            .padding(horizontal = 12.dp)
    ) {

        SectionCard {
            Text("Total Units", style = AppTypography.bodySmall)
            Spacer(Modifier.height(6.dp))
            Text("${readings.sumOf { it.unitConsumed }} units", style = AppTypography.titleMedium)
        }

        Spacer(Modifier.height(12.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(readings) { reading ->
                MeterReadingCard(reading)
            }
        }
    }
}

@Composable
fun MeterReadingCard(reading: MeterReadingUI) {
    SectionCard {
        Column {
            Text("Units: ${reading.unitConsumed}", style = AppTypography.titleSmall)
            Spacer(Modifier.height(6.dp))
            Text("Bill: ₹${reading.billAmount}", style = AppTypography.bodyMedium)
        }
    }
}
