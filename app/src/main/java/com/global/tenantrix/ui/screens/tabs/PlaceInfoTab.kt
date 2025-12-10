package com.global.tenantrix.ui.screens.tabs

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.global.tenantrix.ui.components.*

@Composable
fun PlaceInfoTab(propertyId: Long) {

    // FORM STATE (Replace with ViewModel state in real integration)
    var placeName by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var ownerName by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("Residential") }
    val categories = listOf("Residential", "Commercial", "Industrial")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {

        SectionCard {
            RoundedInputField(
                value = placeName,
                onValueChange = { placeName = it },
                label = "Place Name"
            )
            Spacer(Modifier.height(12.dp))

            RoundedInputField(
                value = address,
                onValueChange = { address = it },
                label = "Address"
            )
            Spacer(Modifier.height(12.dp))

            RoundedInputField(
                value = ownerName,
                onValueChange = { ownerName = it },
                label = "Owner Name"
            )
            Spacer(Modifier.height(12.dp))

            DropdownSelector(
                label = "Category",
                options = categories,
                selected = category,
                onSelected = { category = it }
            )
        }

        Spacer(Modifier.height(16.dp))

        ExpandableCard(title = "Additional Details") {
            RoundedInputField(
                value = "",
                onValueChange = {},
                label = "Landmark"
            )
            Spacer(Modifier.height(12.dp))

            RoundedInputField(
                value = "",
                onValueChange = {},
                label = "Notes"
            )
        }

        Spacer(Modifier.height(40.dp))

        PrimaryButton(
            text = "Save",
            modifier = Modifier.fillMaxWidth(),
            onClick = { /* Save to ViewModel */ }
        )
    }
}
