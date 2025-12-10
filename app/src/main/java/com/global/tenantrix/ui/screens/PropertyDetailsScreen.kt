package com.global.tenantrix.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.global.tenantrix.ui.theme.*
import com.global.tenantrix.ui.components.*
import com.global.tenantrix.ui.screens.tabs.PlaceInfoTab
import com.global.tenantrix.ui.screens.tabs.RentReceiptTab

@Composable
fun PropertyDetailsScreen(
    propertyId: Long,
    onBack: () -> Unit
) {
    var selectedTab by remember { mutableStateOf(0) }

    val tabs = listOf("Place Info", "Rent Receipt")

    Scaffold(
        topBar = {
            GradientHeader(
                title = "Property Details",
                onBack = onBack   // ✅ FIXED
            )
        }
    ) { padding ->

        Column(
            Modifier
                .fillMaxSize()
                .background(Purple10)
                .padding(padding)
        ) {

            TabRow(
                selectedTabIndex = selectedTab,
                modifier = Modifier.fillMaxWidth(),
                containerColor = Purple10
            ) {
                tabs.forEachIndexed { index, text ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = {
                            Text(
                                text = text,
                                style = if (selectedTab == index)
                                    AppTypography.titleSmall
                                else AppTypography.bodyMedium
                            )
                        },
                        selectedContentColor = Purple80,
                        unselectedContentColor = Purple40
                    )
                }
            }

            Spacer(Modifier.height(12.dp))

            when (selectedTab) {
                0 -> PlaceInfoTab(propertyId)
                1 -> RentReceiptTab(propertyId)
            }
        }
    }
}
