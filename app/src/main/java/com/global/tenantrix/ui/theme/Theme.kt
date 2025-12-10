package com.global.tenantrix.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.foundation.isSystemInDarkTheme

private val LightColors = lightColorScheme(
    primary = Purple80,
    onPrimary = Purple10,
    primaryContainer = Purple20,
    onPrimaryContainer = Purple100,

    secondary = Purple60,
    onSecondary = Purple10,

    background = Purple10,
    onBackground = Purple100,

    surface = Purple10,
    onSurface = Purple100,

    surfaceVariant = SurfaceVariant,
    onSurfaceVariant = OnSurfaceVariant,
)

@Composable
fun TenantrixTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    // Currently we apply the light theme always,
    // but dark theme support can be added later
    val colors = LightColors

    MaterialTheme(
        colorScheme = colors,
        typography = AppTypography,
        shapes = AppShapes,
        content = content
    )
}
