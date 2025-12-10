package com.global.tenantrix.ui.screens.add

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.global.tenantrix.data.entity.TenantEntity
import com.global.tenantrix.ui.components.CustomTextField
import com.global.tenantrix.ui.components.GradientHeader
import com.global.tenantrix.viewmodel.TenantViewModel
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTenantScreen(
    roomId: Long,
    viewModel: TenantViewModel = hiltViewModel(),
    onTenantSaved: (Long) -> Unit,
    onBack: () -> Unit
) {
    // FORM FIELDS
    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var idProof by remember { mutableStateOf("") }
    var depositAmount by remember { mutableStateOf("") }

    // Join date = current date
    val joinDate = remember { System.currentTimeMillis() }

    var isSaving by remember { mutableStateOf(false) }

    val scroll = rememberScrollState()

    Scaffold(
        topBar = {
            GradientHeader(
                title = "Add Tenant",
                onBack = onBack
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(scroll)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                shadowElevation = 6.dp,
                tonalElevation = 2.dp,
                color = Color.White
            ) {

                Column(
                    modifier = Modifier
                        .padding(18.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {

                    // ------------------ INPUT FIELDS ------------------
                    CustomTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = "Tenant Name"
                    )

                    CustomTextField(
                        value = phone,
                        onValueChange = { phone = it },
                        label = "Phone Number",
                        keyboardType = KeyboardType.Phone
                    )

                    CustomTextField(
                        value = idProof,
                        onValueChange = { idProof = it },
                        label = "ID Proof (Optional)"
                    )

                    CustomTextField(
                        value = depositAmount,
                        onValueChange = { depositAmount = it },
                        label = "Deposit Amount",
                        keyboardType = KeyboardType.Number
                    )

                    // ------------------ SAVE BUTTON ------------------
                    Button(
                        onClick = {
                            if (name.isBlank() || phone.isBlank()) return@Button

                            isSaving = true

                            val tenant = TenantEntity(
                                name = name,
                                phone = phone,
                                idProof = idProof.ifBlank { null },
                                joinDate = joinDate,
                                leaveDate = null,
                                depositAmount = depositAmount.toDoubleOrNull(),
                                roomId = roomId
                            )

                            viewModel.addTenant(tenant) { tenantId ->
                                isSaving = false
                                onTenantSaved(tenantId)
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = !isSaving,
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text(
                            text = if (isSaving) "Saving..." else "Save Tenant",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}
