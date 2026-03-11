package com.example.dailyxp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
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
fun StatsScreen(
    onBack: () -> Unit = {},
    onAddHabit: () -> Unit = {},
    onHome: () -> Unit = {}
) {
    Scaffold(
        containerColor = BgDark,
        bottomBar = {
            BottomNavBar(
                onAddHabit = onAddHabit,
                onStats = {},
                onHome = onBack
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Estatísticas",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )

                Spacer(modifier = Modifier.height(20.dp))

                // ── Cards de resumo ──
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    StatCard(
                        icon = "🔥",
                        value = "12",
                        label = "Dias seguidos",
                        modifier = Modifier.weight(1f)
                    )
                    StatCard(
                        icon = "✅",
                        value = "47",
                        label = "Hábitos feitos",
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    StatCard(
                        icon = "⚡",
                        value = "7424",
                        label = "XP total",
                        modifier = Modifier.weight(1f)
                    )
                    StatCard(
                        icon = "📅",
                        value = "30",
                        label = "Dias ativos",
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // ── Progresso semanal ──
                Text(
                    text = "ESTA SEMANA",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium,
                    color = TextMuted,
                    letterSpacing = 1.sp
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Barras dos dias da semana
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    val days = listOf(
                        "Seg" to 1f,
                        "Ter" to 0.8f,
                        "Qua" to 1f,
                        "Qui" to 0.6f,
                        "Sex" to 0.4f,
                        "Sáb" to 0f,
                        "Dom" to 0f
                    )
                    days.forEach { (day, pct) ->
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .width(32.dp)
                                    .height(80.dp),
                                contentAlignment = Alignment.BottomCenter
                            ) {
                                Box(
                                    modifier = Modifier
                                        .width(32.dp)
                                        .height((80 * pct).dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(if (pct > 0f) Teal else Surface2)
                                )
                            }
                            Text(
                                text = day,
                                fontSize = 10.sp,
                                color = TextMuted
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // ── Hábitos mais concluídos ──
                Text(
                    text = "MAIS CONCLUÍDOS",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium,
                    color = TextMuted,
                    letterSpacing = 1.sp
                )

                Spacer(modifier = Modifier.height(12.dp))
            }

            val topHabits = listOf(
                Triple("Acordar e arrumar a cama", "28x", 0.93f),
                Triple("Fazer o almoço", "24x", 0.80f),
                Triple("Molhar as plantas", "20x", 0.67f),
                Triple("Passear com o cachorro", "15x", 0.50f),
            )

            items(topHabits.size) { index ->
                val (name, count, pct) = topHabits[index]
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = name, fontSize = 13.sp, color = TextPrimary)
                        Text(text = count, fontSize = 13.sp, color = Teal, fontWeight = FontWeight.Bold)
                    }
                    LinearProgressIndicator(
                        progress = { pct },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(4.dp)
                            .clip(RoundedCornerShape(4.dp)),
                        color = Teal,
                        trackColor = Surface2
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@Composable
fun StatCard(
    icon: String,
    value: String,
    label: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(Surface)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(text = icon, fontSize = 24.sp)
        Text(
            text = value,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Teal
        )
        Text(
            text = label,
            fontSize = 11.sp,
            color = TextMuted
        )
    }
}