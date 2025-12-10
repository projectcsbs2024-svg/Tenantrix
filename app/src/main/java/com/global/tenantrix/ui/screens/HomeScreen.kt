package com.global.tenantrix.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.global.tenantrix.ui.theme.*
import com.global.tenantrix.ui.components.*
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.draw.clip

// UI Model for HomeScreen (map DB entity → UI model in ViewModel)
data class PropertyUI(
    val id: Long,
    val name: String,
    val address: String,
    val occupancyPercent: Int
)

/**
 * MAIN HOME SCREEN
 */
@Composable
fun HomeScreen(
    properties: List<PropertyUI>,
    onAddProperty: () -> Unit,
    onOpenProperty: (Long) -> Unit,
    onNavigateDashboard: () -> Unit,
    onNavigateTasks: () -> Unit
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddProperty,
                containerColor = Purple80,
                contentColor = Color.White
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Property")
            }
        },
        bottomBar = {
            BottomBar(
                selected = 0,
                onPlaces = { /* Already Home */ },
                onDashboard = onNavigateDashboard,
                onTasks = onNavigateTasks
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Purple10)
                .padding(padding)
        ) {

            /** HEADER */
            HomeHeader()

            Spacer(Modifier.height(8.dp))

            /** Last Backup Info */
            Text(
                text = "Last Backup On: Mon, 10 Nov [7:53 AM]",
                style = AppTypography.bodySmall,
                modifier = Modifier.padding(horizontal = 16.dp),
            )

            Spacer(Modifier.height(12.dp))

            /** PROPERTY LIST */
            LazyColumn(
                contentPadding = PaddingValues(bottom = 100.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(properties) { p ->
                    PropertyItemCard(
                        property = p,
                        onClick = { onOpenProperty(p.id) }
                    )
                }
            }
        }
    }
}

/**
 * HEADER
 */
@Composable
fun HomeHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(horizontal = 12.dp)
            .clip(AppShapes.large)
            .background(
                Brush.verticalGradient(
                    colors = listOf(Purple80, Purple60)
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = ".. RentGet ..",
            style = AppTypography.titleLarge.copy(color = Color.White)
        )
    }
}

/**
 * PROPERTY CARD ITEM
 */
@Composable
fun PropertyItemCard(
    property: PropertyUI,
    onClick: () -> Unit
) {
    SectionCard(
        modifier = Modifier.padding(horizontal = 12.dp)
    ) {
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // Left: Icon
            Box(
                modifier = Modifier
                    .size(54.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .background(Purple20),
                contentAlignment = Alignment.Center
            ) {
                Text("🏠", style = AppTypography.titleMedium)
            }

            Spacer(Modifier.width(12.dp))

            // Middle: Text Info
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(property.name, style = AppTypography.titleMedium)
                Text(property.address, style = AppTypography.bodySmall)
            }

            Spacer(Modifier.width(12.dp))

            // Right: Progress Ring
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                ProgressRing(
                    progress = property.occupancyPercent / 100f,
                    modifier = Modifier.size(45.dp)
                )
                Text(
                    "${property.occupancyPercent}%",
                    style = AppTypography.bodySmall
                )
            }
        }
    }
}
