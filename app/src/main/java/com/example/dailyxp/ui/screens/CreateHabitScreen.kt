package com.example.dailyxp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dailyxp.ui.theme.*
import com.example.dailyxp.viewmodel.HabitViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateHabitScreen(
    viewModel: HabitViewModel,
    onBack: () -> Unit = {}
) {
    var habitName by remember { mutableStateOf("") }
    var habitSubtitle by remember { mutableStateOf("") }
    var xpValue by remember { mutableStateOf("10") }
    var showTimePicker by remember { mutableStateOf(false) }
    var selectedHour by remember { mutableStateOf(7) }
    var selectedMinute by remember { mutableStateOf(0) }
    val timePickerState = rememberTimePickerState(
        initialHour = selectedHour,
        initialMinute = selectedMinute,
        is24Hour = true
    )

    val formattedTime = String.format("%02d:%02d", selectedHour, selectedMinute)

    if (showTimePicker) {
        AlertDialog(
            onDismissRequest = { showTimePicker = false },
            containerColor = Surface,
            title = {
                Text("Selecionar horário", color = TextPrimary, fontWeight = FontWeight.Bold)
            },
            text = {
                TimePicker(
                    state = timePickerState,
                    colors = TimePickerDefaults.colors(
                        clockDialColor = Surface2,
                        selectorColor = Teal,
                        containerColor = Surface,
                        periodSelectorBorderColor = Teal,
                        clockDialSelectedContentColor = BgDark,
                        clockDialUnselectedContentColor = TextPrimary,
                        timeSelectorSelectedContainerColor = Teal,
                        timeSelectorUnselectedContainerColor = Surface2,
                        timeSelectorSelectedContentColor = BgDark,
                        timeSelectorUnselectedContentColor = TextPrimary,
                    )
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    selectedHour = timePickerState.hour
                    selectedMinute = timePickerState.minute
                    showTimePicker = false
                }) {
                    Text("Confirmar", color = Teal, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showTimePicker = false }) {
                    Text("Cancelar", color = TextMuted)
                }
            }
        )
    }

    Scaffold(
        containerColor = BgDark,
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Voltar", tint = TextPrimary)
                }
                Text(
                    text = "Novo Hábito",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            Text(text = "Nome do hábito", fontSize = 13.sp, color = TextMuted)
            OutlinedTextField(
                value = habitName,
                onValueChange = { habitName = it },
                placeholder = { Text("Ex: Acordar cedo", color = TextDim) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Teal,
                    unfocusedBorderColor = Surface2,
                    focusedTextColor = TextPrimary,
                    unfocusedTextColor = TextPrimary,
                    cursorColor = Teal
                )
            )

            Text(text = "Horário", fontSize = 13.sp, color = TextMuted)
            OutlinedTextField(
                value = formattedTime,
                onValueChange = {},
                readOnly = true,
                placeholder = { Text("Toque para selecionar", color = TextDim) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Teal,
                    unfocusedBorderColor = Surface2,
                    focusedTextColor = TextPrimary,
                    unfocusedTextColor = TextPrimary,
                    cursorColor = Teal
                ),
                trailingIcon = {
                    TextButton(onClick = { showTimePicker = true }) {
                        Text("Alterar", color = Teal, fontSize = 12.sp)
                    }
                }
            )

            Text(text = "Descrição", fontSize = 13.sp, color = TextMuted)
            OutlinedTextField(
                value = habitSubtitle,
                onValueChange = { habitSubtitle = it },
                placeholder = { Text("Ex: Manhã · 5 min", color = TextDim) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Teal,
                    unfocusedBorderColor = Surface2,
                    focusedTextColor = TextPrimary,
                    unfocusedTextColor = TextPrimary,
                    cursorColor = Teal
                )
            )

            Text(text = "XP do hábito", fontSize = 13.sp, color = TextMuted)
            OutlinedTextField(
                value = xpValue,
                onValueChange = { xpValue = it },
                placeholder = { Text("Ex: 10", color = TextDim) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Teal,
                    unfocusedBorderColor = Surface2,
                    focusedTextColor = TextPrimary,
                    unfocusedTextColor = TextPrimary,
                    cursorColor = Teal
                )
            )

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    if (habitName.isNotBlank()) {
                        val descricao = buildString {
                            append(formattedTime)
                            if (habitSubtitle.isNotBlank()) append(" · $habitSubtitle")
                        }
                        viewModel.insertHabit(
                            titulo = habitName,
                            descricao = descricao,
                            xp = xpValue.toIntOrNull() ?: 10
                        )
                        onBack()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Teal)
            ) {
                Text(
                    text = "Salvar Hábito",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = BgDark
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}