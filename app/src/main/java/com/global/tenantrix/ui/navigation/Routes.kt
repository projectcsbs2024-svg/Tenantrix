package com.global.tenantrix.ui.navigation

sealed class Route(val route: String) {

    // ---------------- MAIN NAV SCREENS ----------------
    data object Home : Route("home")

    data object Rooms : Route("rooms")

    data object Stats : Route("stats")

    data object Notifications : Route("notifications")

    data object Expenses : Route("expenses")

    // ---------------- PROPERTY DETAILS ----------------
    data object PropertyDetails : Route("property/{propertyId}") {
        fun create(propertyId: Long) = "property/$propertyId"
    }

    // ---------------- DAY 12 WORKFLOW ----------------
    data object AddProperty : Route("add_property")

    data object AddRoom : Route("add_room/{propertyId}") {
        fun create(propertyId: Long) = "add_room/$propertyId"
    }

    data object AddTenant : Route("add_tenant/{roomId}") {
        fun create(roomId: Long) = "add_tenant/$roomId"
    }

    data object AddPayment : Route("add_payment/{tenantId}") {
        fun create(tenantId: Long) = "add_payment/$tenantId"
    }

    // ---------------- SHARING ----------------
    data object ShareReceipt : Route("share_receipt")

    data object PaymentHistory : Route("payment_history/{tenantId}") {
        fun create(tenantId: Long) = "payment_history/$tenantId"
    }

}
