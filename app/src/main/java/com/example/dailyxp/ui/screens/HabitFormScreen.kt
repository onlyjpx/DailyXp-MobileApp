package com.example.dailyxp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dailyxp.data.local.HabitEntity
import com.example.dailyxp.ui.theme.*
import com.example.dailyxp.viewmodel.HabitViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HabitFormScreen(
    habit: HabitEntity? = null,
    viewModel: HabitViewModel,
    onBack: () -> Unit = {}
) {
    val isEditing = habit != null

    val existingTime = habit?.descricao?.substringBefore(" ·") ?: "07:00"
    val timeParts = existingTime.split(":")
    val initHour = timeParts.getOrNull(0)?.toIntOrNull() ?: 7
    val initMinute = timeParts.getOrNull(1)?.toIntOrNull() ?: 0
    val existingSubtitle = if (habit?.descricao?.contains(" · ") == true)
        habit.descricao.substringAfter(" · ")
    else ""

    var habitName by remember { mutableStateOf(habit?.titulo ?: "") }
    var habitSubtitle by remember { mutableStateOf(existingSubtitle) }
    var xpValue by remember { mutableStateOf(habit?.xp?.toString() ?: "10") }
    var showTimePicker by remember { mutableStateOf(false) }
    var selectedHour by remember { mutableStateOf(initHour) }
    var selectedMinute by remember { mutableStateOf(initMinute) }
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
            title = { Text("Selecionar horário", color = TextPrimary, fontWeight = FontWeight.Bold) },
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
                }) { Text("Confirmar", color = Teal, fontWeight = FontWeight.Bold) }
            },
            dismissButton = {
                TextButton(onClick = { showTimePicker = false }) {
                    Text("Cancelar", color = TextMuted)
                }
            }
        )
    }

    Scaffold(containerColor = BgDark) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            // ── Header ──────────────────────────────────────────────
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                IconButton(
                    onClick = onBack,
                    modifier = Modifier
                        .size(38.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Surface2)
                ) {
                    Icon(
                        Icons.Filled.ArrowBack,
                        contentDescription = "Voltar",
                        tint = TextPrimary,
                        modifier = Modifier.size(18.dp)
                    )
                }
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = if (isEditing) "Editar Hábito" else "Novo Hábito",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    Text(
                        text = if (isEditing) "Ajuste os detalhes do hábito" else "Configure seu novo hábito",
                        fontSize = 12.sp,
                        color = TextMuted
                    )
                }
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(TealDim),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (isEditing) Icons.Filled.Edit else Icons.Filled.Add,
                        contentDescription = null,
                        tint = Teal,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            // ── Campos ──────────────────────────────────────────────
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                // Nome
                InputCard(label = "✏️  NOME DO HÁBITO", trailingContent = null) {
                    TextField(
                        value = habitName,
                        onValueChange = { habitName = it },
                        placeholder = {
                            Text(
                                "Ex: Acordar cedo",
                                color = TextDim,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        textStyle = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = TextPrimary
                        ),
                        singleLine = true,
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            cursorColor = Teal
                        )
                    )
                }

                // Horário
                InputCard(
                    label = "⏰  HORÁRIO",
                    trailingContent = {
                        TextButton(onClick = { showTimePicker = true }) {
                            Text("Alterar", color = Teal, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                ) {
                    Text(
                        text = formattedTime,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary,
                        modifier = Modifier.padding(start = 16.dp, bottom = 12.dp, top = 4.dp)
                    )
                }

                // Descrição
                InputCard(label = "📝  DESCRIÇÃO", trailingContent = null) {
                    TextField(
                        value = habitSubtitle,
                        onValueChange = { habitSubtitle = it },
                        placeholder = { Text("Ex: Manhã · 5 min", color = TextDim, fontSize = 15.sp) },
                        modifier = Modifier.fillMaxWidth(),
                        textStyle = TextStyle(fontSize = 15.sp, color = TextPrimary),
                        singleLine = true,
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            cursorColor = Teal
                        )
                    )
                }

                // XP com preview ao vivo
                InputCard(
                    label = "⚡  XP DO HÁBITO",
                    trailingContent = {
                        val xpPreview = xpValue.toIntOrNull() ?: 10
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(TealDim)
                                .padding(horizontal = 10.dp, vertical = 4.dp)
                        ) {
                            Text("+$xpPreview xp", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Teal)
                        }
                    }
                ) {
                    TextField(
                        value = xpValue,
                        onValueChange = { xpValue = it },
                        placeholder = {
                            Text("10", color = TextDim, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        textStyle = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = TextPrimary
                        ),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            cursorColor = Teal
                        )
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))
            }

            // ── Botão salvar ─────────────────────────────────────────
            Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 20.dp)) {
                Button(
                    onClick = {
                        if (habitName.isNotBlank()) {
                            val descricao = buildString {
                                append(formattedTime)
                                if (habitSubtitle.isNotBlank()) append(" · $habitSubtitle")
                            }
                            if (isEditing) {
                                viewModel.updateHabit(
                                    habit = habit!!,
                                    titulo = habitName,
                                    descricao = descricao,
                                    xp = xpValue.toIntOrNull() ?: 10
                                )
                            } else {
                                viewModel.insertHabit(
                                    titulo = habitName,
                                    descricao = descricao,
                                    xp = xpValue.toIntOrNull() ?: 10
                                )
                            }
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
                        text = if (isEditing) "Salvar Alterações" else "Salvar Hábito",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = BgDark
                    )
                }
            }
        }
    }
}

@Composable
private fun InputCard(
    label: String,
    trailingContent: (@Composable () -> Unit)?,
    content: @Composable () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Surface)
            .padding(top = 14.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = label,
                fontSize = 10.sp,
                fontWeight = FontWeight.Medium,
                color = TextMuted,
                letterSpacing = 0.8.sp
            )
            trailingContent?.invoke()
        }
        content()
    }
}
