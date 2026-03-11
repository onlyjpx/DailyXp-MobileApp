package com.example.dailyxp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dailyxp.data.local.HabitEntity
import com.example.dailyxp.ui.components.HabitItem
import com.example.dailyxp.ui.theme.*
import com.example.dailyxp.viewmodel.HabitViewModel
import java.util.*

@Composable
fun AgendaScreen(
    viewModel: HabitViewModel,
    onAddHabit: () -> Unit = {},
    onHome: () -> Unit = {},
    onStats: () -> Unit = {}
) {
    val habits by viewModel.allHabits.collectAsState()

    var currentMonth by remember { mutableStateOf(Calendar.getInstance()) }
    var selectedDay by remember { mutableStateOf(Calendar.getInstance().get(Calendar.DAY_OF_MONTH)) }
    var selectedMonth by remember { mutableStateOf(Calendar.getInstance().get(Calendar.MONTH)) }
    var selectedYear by remember { mutableStateOf(Calendar.getInstance().get(Calendar.YEAR)) }

    val monthNames = listOf("Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho",
        "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro")
    val dayLabels = listOf("seg", "ter", "qua", "qui", "sex", "sáb", "dom")

    val year = currentMonth.get(Calendar.YEAR)
    val month = currentMonth.get(Calendar.MONTH)

    val firstDayOfMonth = Calendar.getInstance().apply {
        set(year, month, 1)
    }
    val firstDayOfWeek = (firstDayOfMonth.get(Calendar.DAY_OF_WEEK) + 5) % 7
    val daysInMonth = currentMonth.getActualMaximum(Calendar.DAY_OF_MONTH)

    Scaffold(
        containerColor = BgDark,
        bottomBar = {
            BottomNavBar(
                onAddHabit = onAddHabit,
                onStats = onStats,
                onHome = onHome,
                onAgenda = {}
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Row(  // Header do mês
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = {
                        currentMonth = (currentMonth.clone() as Calendar).apply {
                            add(Calendar.MONTH, -1)
                        }
                    }) {
                        Icon(Icons.Filled.KeyboardArrowLeft, contentDescription = "Mês anterior", tint = TextMuted)
                    }

                    Text(
                        text = "${monthNames[month]} ",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    Text(
                        text = "$year",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Teal
                    )

                    IconButton(onClick = {
                        currentMonth = (currentMonth.clone() as Calendar).apply {
                            add(Calendar.MONTH, 1)
                        }
                    }) {
                        Icon(Icons.Filled.KeyboardArrowRight, contentDescription = "Próximo mês", tint = TextMuted)
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))
                Row(modifier = Modifier.fillMaxWidth()) {
                    dayLabels.forEach { label ->
                        Text( // Labels dos dias
                            text = label,
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Center,
                            fontSize = 11.sp,
                            color = TextDim,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))


                val totalCells = firstDayOfWeek + daysInMonth
                val rows = (totalCells + 6) / 7
                // Grid do calendário
                for (row in 0 until rows) {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        for (col in 0 until 7) {
                            val cellIndex = row * 7 + col
                            val day = cellIndex - firstDayOfWeek + 1

                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .aspectRatio(1f)
                                    .padding(2.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                if (day in 1..daysInMonth) {
                                    val isToday = day == Calendar.getInstance().get(Calendar.DAY_OF_MONTH) &&
                                            month == Calendar.getInstance().get(Calendar.MONTH) &&
                                            year == Calendar.getInstance().get(Calendar.YEAR)
                                    val isSelected = day == selectedDay &&
                                            month == selectedMonth &&
                                            year == selectedYear

                                    Box(
                                        modifier = Modifier
                                            .size(36.dp)
                                            .clip(RoundedCornerShape(10.dp))
                                            .background(
                                                when {
                                                    isToday -> Teal
                                                    isSelected -> TealDim
                                                    else -> androidx.compose.ui.graphics.Color.Transparent
                                                }
                                            )
                                            .clickable {
                                                selectedDay = day
                                                selectedMonth = month
                                                selectedYear = year
                                            },
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = "$day",
                                            fontSize = 13.sp,
                                            fontWeight = if (isToday || isSelected) FontWeight.Bold else FontWeight.Normal,
                                            color = when {
                                                isToday -> BgDark
                                                isSelected -> Teal
                                                else -> TextMuted
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Hábitos do dia selecionado
                Text(
                    text = "Hábitos — $selectedDay de ${monthNames[selectedMonth]}",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    color = TextMuted,
                    letterSpacing = 0.5.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                if (habits.isEmpty()) {
                    Text(
                        text = "Nenhum hábito cadastrado.",
                        color = TextMuted,
                        fontSize = 14.sp
                    )
                }
            }

            items(habits.size, key = { habits[it].id }) { index ->
                val habit = habits[index]
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = habit.descricao?.substringBefore(" ·") ?: "",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = TextDim,
                        modifier = Modifier.width(40.dp)
                    )
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .clip(androidx.compose.foundation.shape.CircleShape)
                            .background(Teal)
                    )
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = habit.titulo,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = TextPrimary
                        )
                        Text(
                            text = habit.descricao ?: "",
                            fontSize = 11.sp,
                            color = TextMuted
                        )
                    }
                    Box(
                        modifier = Modifier
                            .clip(androidx.compose.foundation.shape.RoundedCornerShape(8.dp))
                            .background(TealDim)
                            .padding(horizontal = 8.dp, vertical = 3.dp)
                    ) {
                        Text(
                            text = "+${habit.xp} xp",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Teal
                        )
                    }
                }
                HorizontalDivider(color = Surface2, thickness = 1.dp)
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }
}