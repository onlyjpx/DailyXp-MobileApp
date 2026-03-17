package com.example.dailyxp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dailyxp.ui.components.HabitItem
import com.example.dailyxp.ui.theme.*
import com.example.dailyxp.viewmodel.HabitViewModel
import com.example.dailyxp.data.local.HabitEntity

@Composable
fun HomeScreen(
    viewModel: HabitViewModel,
    onAddHabit: () -> Unit = {},
    onStats: () -> Unit = {},
    onAgenda: () -> Unit = {},
    onEditHabit: (HabitEntity) -> Unit = {}
) {
    val habits by viewModel.allHabits.collectAsState()

    val totalXP = habits.sumOf { it.xp }
    val maxStreak = habits.maxOfOrNull { it.streak } ?: 0

    Scaffold(
        containerColor = BgDark,
        bottomBar = { BottomNavBar(onAddHabit = onAddHabit, onStats = onStats, onHome = {}, onAgenda = onAgenda, currentScreen = "home") }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(16.dp))

                // ── Header ──
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(text = "Bom dia,", fontSize = 13.sp, color = TextMuted)
                        Text(
                            text = "Maria Eduarda",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                    }
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(RoundedCornerShape(50))
                            .background(Surface2),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "🧙", fontSize = 20.sp)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // ── Chips XP e Streak ──
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    StatChip(icon = "⚡", value = "$totalXP XP", label = "Experiência", modifier = Modifier.weight(1f))
                    StatChip(icon = "🔥", value = "$maxStreak dias", label = "Sequência", modifier = Modifier.weight(1f))
                }

                Spacer(modifier = Modifier.height(24.dp))

                // ── Barra de progresso ──
                val done = habits.count { viewModel.isCompletedToday(it.ultimaVezCompletado) }
                val total = habits.size
                val pct = if (total > 0) done.toFloat() / total else 0f

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "$done de $total concluídos", fontSize = 11.sp, color = TextMuted)
                    Text(text = "${(pct * 100).toInt()}%", fontSize = 11.sp, color = Teal, fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(6.dp))

                LinearProgressIndicator(
                    progress = { pct },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(4.dp)
                        .clip(RoundedCornerShape(4.dp)),
                    color = Teal,
                    trackColor = Surface2
                )

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "HOJE",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium,
                    color = TextMuted,
                    letterSpacing = 1.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                if (habits.isEmpty()) {
                    Text(
                        text = "Nenhum hábito ainda. Clique em + para adicionar!",
                        color = TextMuted,
                        fontSize = 14.sp
                    )
                }
            }

// oi joaoo, já conectei o editar! ->>
            items(habits, key = { it.id }) { habit ->
                val isDone = viewModel.isCompletedToday(habit.ultimaVezCompletado)
                HabitItem(
                    time = habit.descricao?.substringBefore(" ·") ?: "",
                    name = habit.titulo,
                    subtitle = habit.descricao ?: "",
                    xp = habit.xp,
                    isDone = isDone,
                    onComplete = {
                        if (isDone) {
                            viewModel.uncompleteHabit(habit)
                        } else {
                            viewModel.completeHabit(habit)
                        }
                    },
                    onEdit = { onEditHabit(habit) },
                    onDelete = { viewModel.deleteHabit(habit) }
                )
                HorizontalDivider(color = Surface2, thickness = 1.dp)
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }
}

@Composable
fun BottomNavBar(
    onAddHabit: () -> Unit = {},
    onStats: () -> Unit = {},
    onHome: () -> Unit = {},
    onAgenda: () -> Unit,
    currentScreen: String = "home"
) {
    NavigationBar(
        containerColor = Surface,
        tonalElevation = 0.dp
    ) {
        NavigationBarItem(
            selected = currentScreen == "home",
            onClick = onHome,
            icon = { Icon(Icons.Filled.Home, contentDescription = "Início") },
            label = { Text("Início") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Teal,
                selectedTextColor = Teal,
                indicatorColor = Surface,
                unselectedIconColor = TextMuted,
                unselectedTextColor = TextMuted
            )
        )
        NavigationBarItem(
            selected = currentScreen == "agenda",
            onClick = onAgenda,
            icon = { Icon(Icons.Filled.DateRange, contentDescription = "Agenda") },
            label = { Text("Agenda") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Teal,
                selectedTextColor = Teal,
                indicatorColor = Surface,
                unselectedIconColor = TextMuted,
                unselectedTextColor = TextMuted
            )
        )
        NavigationBarItem(
            selected = false,
            onClick = onAddHabit,
            icon = {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(Teal),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Filled.Add, contentDescription = "Adicionar", tint = BgDark, modifier = Modifier.size(28.dp))
                }
            },
            label = { Text("") },
            colors = NavigationBarItemDefaults.colors(indicatorColor = Surface)
        )
        NavigationBarItem(
            selected = currentScreen == "stats",
            onClick = onStats,
            icon = { Icon(Icons.Filled.Star, contentDescription = "Stats") },
            label = { Text("Stats") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Teal,
                selectedTextColor = Teal,
                indicatorColor = Surface,
                unselectedIconColor = TextMuted,
                unselectedTextColor = TextMuted
            )
        )
        NavigationBarItem(
            selected = currentScreen == "perfil",
            onClick = {},
            icon = { Icon(Icons.Filled.Person, contentDescription = "Perfil") },
            label = { Text("Perfil") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Teal,
                selectedTextColor = Teal,
                indicatorColor = Surface,
                unselectedIconColor = TextMuted,
                unselectedTextColor = TextMuted
            )
        )
    }
}

@Composable
fun StatChip(icon: String, value: String, label: String, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .background(Surface)
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(text = icon, fontSize = 18.sp)
        Column {
            Text(text = value, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Teal)
            Text(text = label, fontSize = 10.sp, color = TextMuted)
        }
    }
}