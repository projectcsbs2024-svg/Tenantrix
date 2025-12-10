package com.global.tenantrix.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.global.tenantrix.ui.theme.Purple10
import com.global.tenantrix.ui.theme.Purple40
import com.global.tenantrix.ui.theme.Purple80
import com.global.tenantrix.ui.theme.AppTypography

@Composable
fun BottomBar(
    selected: Int,
    onPlaces: () -> Unit,
    onDashboard: () -> Unit,
    onTasks: () -> Unit
) {
    NavigationBar(
        containerColor = Purple10,
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
    ) {

        /** HOME - Properties */
        NavigationBarItem(
            selected = selected == 0,
            onClick = onPlaces,
            icon = {
                Icon(
                    Icons.Default.Home,
                    contentDescription = "Places",
                    tint = if (selected == 0) Purple80 else Purple40
                )
            },
            label = {
                Text(
                    "Places",
                    style = AppTypography.bodySmall
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Purple80,
                selectedTextColor = Purple80,
                unselectedIconColor = Purple40,
                unselectedTextColor = Purple40,
                indicatorColor = Color.Transparent
            )
        )

        /** DASHBOARD - Stats */
        NavigationBarItem(
            selected = selected == 1,
            onClick = onDashboard,
            icon = {
                Icon(
                    Icons.Default.Assessment,
                    contentDescription = "Dashboard",
                    tint = if (selected == 1) Purple80 else Purple40
                )
            },
            label = {
                Text(
                    "Dashboard",
                    style = AppTypography.bodySmall
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Purple80,
                selectedTextColor = Purple80,
                unselectedIconColor = Purple40,
                unselectedTextColor = Purple40,
                indicatorColor = Color.Transparent
            )
        )

        /** NOTIFICATIONS */
        NavigationBarItem(
            selected = selected == 2,
            onClick = onTasks,
            icon = {
                Icon(
                    Icons.Default.Notifications,
                    contentDescription = "Tasks",
                    tint = if (selected == 2) Purple80 else Purple40
                )
            },
            label = {
                Text(
                    "Tasks",
                    style = AppTypography.bodySmall
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Purple80,
                selectedTextColor = Purple80,
                unselectedIconColor = Purple40,
                unselectedTextColor = Purple40,
                indicatorColor = Color.Transparent
            )
        )
    }
}
