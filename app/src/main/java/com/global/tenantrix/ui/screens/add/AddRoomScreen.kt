package com.global.tenantrix.ui.screens.add

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.global.tenantrix.data.entity.RoomEntity
import com.global.tenantrix.viewmodel.RoomViewModel
import com.global.tenantrix.ui.components.GradientHeader
import com.global.tenantrix.ui.components.CustomTextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddRoomScreen(
    propertyId: Long,
    viewModel: RoomViewModel = hiltViewModel(),
    onRoomSaved: (Long) -> Unit,
    onBack: () -> Unit
) {

    var roomNumber by remember { mutableStateOf("") }
    var rentAmount by remember { mutableStateOf("") }
    var meterNumber by remember { mutableStateOf("") }
    var isOccupied by remember { mutableStateOf(false) }
    var isSaving by remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            GradientHeader(
                title = "Add Room",
                onBack = onBack
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // ---------------- CARD ----------------
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                shadowElevation = 6.dp,
                color = Color.White
            ) {

                Column(
                    modifier = Modifier.padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {

                    // Room Number
                    CustomTextField(
                        value = roomNumber,
                        onValueChange = { roomNumber = it },
                        label = "Room Number"
                    )

                    // Rent Amount
                    CustomTextField(
                        value = rentAmount,
                        onValueChange = { rentAmount = it },
                        label = "Rent Amount",
                        keyboardType = KeyboardType.Number
                    )

                    // Meter Number
                    CustomTextField(
                        value = meterNumber,
                        onValueChange = { meterNumber = it },
                        label = "Meter Number (Optional)"
                    )

                    // Occupied Switch
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Is Occupied?", fontSize = 16.sp)
                        Switch(
                            checked = isOccupied,
                            onCheckedChange = { isOccupied = it }
                        )
                    }

                    // Save Button
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        enabled = !isSaving,
                        shape = RoundedCornerShape(10.dp),
                        onClick = {
                            if (roomNumber.isBlank() || rentAmount.isBlank()) return@Button

                            isSaving = true

                            val entity = RoomEntity(
                                propertyId = propertyId,
                                roomNumber = roomNumber,
                                rentAmount = rentAmount.toDoubleOrNull() ?: 0.0,
                                meterNumber = meterNumber.ifBlank { null },
                                isOccupied = isOccupied
                            )

                            // Save & return new ID
                            viewModel.addRoom(entity) { roomId ->
                                isSaving = false
                                onRoomSaved(roomId)
                            }
                        }
                    ) {
                        Text(
                            text = if (isSaving) "Saving..." else "Save Room",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

/*******************************************************
REUSABLE COMPONENTS (Same as AddPropertyScreen)
 *******************************************************/

@Composable
fun GradientHeader(
    title: String,
    onBack: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF6A11CB), Color(0xFF2575FC))
                )
            )
            .padding(vertical = 22.dp, horizontal = 16.dp)
    ) {
        Text(
            text = title,
            color = Color.White,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterStart)
        )
    }
}