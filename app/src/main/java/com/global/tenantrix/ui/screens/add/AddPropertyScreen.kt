package com.global.tenantrix.ui.screens.add

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.global.tenantrix.data.entity.PropertyEntity
import com.global.tenantrix.viewmodel.PropertyViewModel
import com.global.tenantrix.ui.components.GradientHeader
import com.global.tenantrix.ui.components.CustomTextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPropertyScreen(
    viewModel: PropertyViewModel = hiltViewModel(),    onPropertySaved: (Long) -> Unit,
    onBack: () -> Unit
) {
    val focusManager = LocalFocusManager.current

    // ---------------- FORM STATE ----------------
    var name by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var ownerName by remember { mutableStateOf("") }
    var ownerPhone by remember { mutableStateOf("") }
    var ownerEmail by remember { mutableStateOf("") }
    var electricityRate by remember { mutableStateOf("") }

    var isSaving by remember { mutableStateOf(false) }
    val scroll = rememberScrollState()

    Scaffold(
        topBar = {
            GradientHeader(
                title = "Add Property",
                onBack = onBack
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(scroll)
                .padding(horizontal = 16.dp, vertical = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // ---------------- WHITE CARD ----------------
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                shadowElevation = 6.dp,
                tonalElevation = 2.dp,
                color = Color.White
            ) {

                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {

                    CustomTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = "Property Name"
                    )

                    CustomTextField(
                        value = address,
                        onValueChange = { address = it },
                        label = "Address"
                    )

                    CustomTextField(
                        value = ownerName,
                        onValueChange = { ownerName = it },
                        label = "Owner Name"
                    )

                    CustomTextField(
                        value = ownerPhone,
                        onValueChange = { ownerPhone = it },
                        label = "Owner Phone"
                    )

                    CustomTextField(
                        value = ownerEmail,
                        onValueChange = { ownerEmail = it },
                        label = "Owner Email (optional)"
                    )

                    CustomTextField(
                        value = electricityRate,
                        onValueChange = { electricityRate = it },
                        label = "Per Unit Electricity Cost"
                    )

                    // ---------------- SAVE BUTTON ----------------
                    Button(
                        onClick = {
                            if (name.isBlank() || ownerName.isBlank() || ownerPhone.isBlank()) return@Button
                            isSaving = true

                            val entity = PropertyEntity(
                                name = name,
                                address = address,
                                ownerName = ownerName,
                                ownerPhone = ownerPhone,
                                ownerEmail = ownerEmail,
                                perUnitElectricityCost = electricityRate.toDoubleOrNull() ?: 0.0
                            )

                            // Insert into DB
                            viewModel.addProperty(entity) { newId ->
                                isSaving = false
                                onPropertySaved(newId)
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = !isSaving,
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text(
                            text = if (isSaving) "Saving..." else "Save Property",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

