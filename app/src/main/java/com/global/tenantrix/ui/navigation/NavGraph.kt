package com.global.tenantrix.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.compose.*
import com.global.tenantrix.ui.screens.*
import com.global.tenantrix.ui.screens.add.AddPaymentScreen
import com.global.tenantrix.ui.screens.add.AddPropertyScreen
import com.global.tenantrix.ui.screens.add.AddRoomScreen
import com.global.tenantrix.ui.screens.add.AddTenantScreen
import com.global.tenantrix.ui.screens.details.PaymentHistoryScreen

@Composable
fun AppNavGraph(modifier: Modifier = Modifier) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Route.Home.route,
        modifier = modifier
    ) {

        // ---------------- HOME SCREEN ----------------
        composable(Route.Home.route) {
            HomeScreen(
                properties = listOf(), // replace with ViewModel
                onAddProperty = { navController.navigate(Route.AddProperty.route) },
                onOpenProperty = { id -> navController.navigate(Route.PropertyDetails.create(id)) },
                onNavigateDashboard = { navController.navigate(Route.Stats.route) },
                onNavigateTasks = { navController.navigate(Route.Notifications.route) }
            )
        }

        // ---------------- ROOMS SCREEN ----------------
        composable(Route.Rooms.route) {
            RoomsScreen(
                rooms = listOf(),
                onAddRoom = { navController.navigate(Route.AddProperty.route) },
                onOpenRoom = { /* TODO */ },
                onBack = { navController.popBackStack() },   // ✅ FIXED
                onNavigatePlaces = { navController.navigate(Route.Home.route) },
                onNavigateDashboard = { navController.navigate(Route.Stats.route) },
                onNavigateTasks = { navController.navigate(Route.Notifications.route) }
            )
        }

        // ---------------- PROPERTY DETAILS ----------------
        composable(
            route = Route.PropertyDetails.route,
            arguments = listOf(navArgument("propertyId") { type = NavType.LongType })
        ) { entry ->
            val id = entry.arguments!!.getLong("propertyId")
            PropertyDetailsScreen(
                propertyId = id,
                onBack = { navController.popBackStack() }
            )
        }

        // ---------------- EXPENSES SCREEN ----------------
        composable(Route.Expenses.route) {
            ExpensesScreen(
                month = "November 2025",
                expenses = listOf(),
                meterReadings = listOf(),
                onMonthChange = {},
                onAddExpense = {},
                onBack = { navController.popBackStack() },    // ✅ FIXED
                onNavigatePlaces = { navController.navigate(Route.Home.route) },
                onNavigateDashboard = { navController.navigate(Route.Stats.route) },
                onNavigateTasks = { navController.navigate(Route.Notifications.route) }
            )
        }

        // ---------------- STATS SCREEN ----------------
        composable(Route.Stats.route) {
            StatsScreen(
                month = "November 2025",
                onMonthChange = {},
                categoryStats = listOf(),
                methodStats = listOf(),
                onBack = { navController.popBackStack() },    // ✅ FIXED
                onNavigatePlaces = { navController.navigate(Route.Home.route) },
                onNavigateDashboard = { navController.navigate(Route.Stats.route) },
                onNavigateTasks = { navController.navigate(Route.Notifications.route) }
            )
        }

        // ---------------- NOTIFICATIONS SCREEN ----------------
        composable(Route.Notifications.route) {
            NotificationSettingsScreen(
                onBack = { navController.popBackStack() },     // ⚡ Likely needed
                onNavigatePlaces = { navController.navigate(Route.Home.route) },
                onNavigateDashboard = { navController.navigate(Route.Stats.route) },
                onNavigateTasks = { navController.navigate(Route.Notifications.route) }
            )
        }

        // ======================================================
        // 📌 DAY 12 WORKFLOW: ADD PROPERTY → ROOM → TENANT → PAYMENT
        // ======================================================

        // ---------------- ADD PROPERTY ----------------
        composable(Route.AddProperty.route) {
            AddPropertyScreen(
                onPropertySaved = { propertyId ->
                    navController.navigate(Route.AddRoom.create(propertyId))
                },
                onBack = { navController.popBackStack() }
            )
        }

        // ---------------- ADD ROOM ----------------
        composable(
            route = Route.AddRoom.route,
            arguments = listOf(navArgument("propertyId") { type = NavType.LongType })
        ) { entry ->
            val propertyId = entry.arguments!!.getLong("propertyId")

            AddRoomScreen(
                propertyId = propertyId,
                onRoomSaved = { roomId ->
                    navController.navigate(Route.AddTenant.create(roomId))
                },
                onBack = { navController.popBackStack() }
            )
        }

        // ---------------- ADD TENANT ----------------
        composable(
            route = Route.AddTenant.route,
            arguments = listOf(navArgument("roomId") { type = NavType.LongType })
        ) { entry ->
            val roomId = entry.arguments!!.getLong("roomId")

            AddTenantScreen(
                roomId = roomId,
                onTenantSaved = { tenantId ->
                    navController.navigate(Route.AddPayment.create(tenantId))
                },
                onBack = { navController.popBackStack() }
            )
        }

        // ---------------- ADD PAYMENT ----------------
        composable(
            route = Route.AddPayment.route,
            arguments = listOf(navArgument("tenantId") { type = NavType.LongType })
        ) { entry ->
            val tenantId = entry.arguments!!.getLong("tenantId")

            AddPaymentScreen(
                tenantId = tenantId,
                onPaymentSaved = {
                    navController.navigate(Route.Home.route) {
                        popUpTo(Route.Home.route) { inclusive = false }
                    }
                },
                onBack = { navController.popBackStack() }
            )
        }

        // ---------------- SHARE RECEIPT ----------------
        composable(Route.ShareReceipt.route) {
            ShareReceiptSheet(
                onDismiss = { navController.popBackStack() },
                onShare = { /* TODO */ }
            )
        }

        // ---------------- PAYMENT HISTORY ----------------
        composable(
            Route.PaymentHistory.route,
            arguments = listOf(navArgument("tenantId") { type = NavType.LongType })
        ) {
            val tenantId = it.arguments!!.getLong("tenantId")
            PaymentHistoryScreen(
                tenantId = tenantId,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
