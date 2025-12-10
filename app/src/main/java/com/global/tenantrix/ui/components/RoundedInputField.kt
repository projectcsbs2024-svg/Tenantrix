package com.global.tenantrix.ui.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.global.tenantrix.ui.theme.*

@Composable
fun RoundedInputField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = modifier,
        shape = AppShapes.medium,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Purple80,
            unfocusedBorderColor = Purple40
        )
    )
}
