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

@Composable
fun CreateHabitScreen(
    onBack: () -> Unit = {}
) {
    var habitName by remember { mutableStateOf("") }
    var habitTime by remember { mutableStateOf("") }
    var habitSubtitle by remember { mutableStateOf("") }

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
                    Icon(
                        Icons.Filled.ArrowBack,
                        contentDescription = "Voltar",
                        tint = TextPrimary
                    )
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

            // ── Nome do hábito ──
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

            // ── Horário ──
            Text(text = "Horário", fontSize = 13.sp, color = TextMuted)
            OutlinedTextField(
                value = habitTime,
                onValueChange = { habitTime = it },
                placeholder = { Text("Ex: 07:00", color = TextDim) },
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

            // ── Descrição ──
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

            Spacer(modifier = Modifier.weight(1f))

            // ── Botão Salvar ──
            Button(
                onClick = { onBack() },
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