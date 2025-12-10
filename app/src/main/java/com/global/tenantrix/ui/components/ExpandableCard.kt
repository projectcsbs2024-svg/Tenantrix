package com.global.tenantrix.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.global.tenantrix.ui.theme.*

@Composable
fun ExpandableCard(
    title: String,
    expandedDefault: Boolean = false,
    content: @Composable () -> Unit
) {
    var expanded by remember { mutableStateOf(expandedDefault) }

    SectionCard {
        Text(title, style = AppTypography.titleSmall)
        Spacer(Modifier.height(10.dp))

        PrimaryButton(
            text = if (expanded) "Hide" else "Show",
            onClick = { expanded = !expanded },
            modifier = Modifier.fillMaxWidth()
        )

        AnimatedVisibility(
            visible = expanded,
            enter = fadeIn() + expandVertically(),
            exit = fadeOut()
        ) {
            Column(Modifier.padding(top = 12.dp)) {
                content()
            }
        }
    }
}
