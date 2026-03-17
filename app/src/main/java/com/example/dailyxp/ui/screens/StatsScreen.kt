package com.example.dailyxp.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dailyxp.ui.theme.*
import com.example.dailyxp.viewmodel.HabitViewModel

data class XpMilestone(
    val xp: Int,
    val icon: String,
    val label: String
)

val milestones = listOf(
    XpMilestone(0,    "🌱", "Iniciante"),
    XpMilestone(100,  "🌿", "Aprendiz"),
    XpMilestone(300,  "🌳", "Dedicado"),
    XpMilestone(600,  "⚔️", "Guerreiro"),
    XpMilestone(1000, "🏆", "Campeão"),
    XpMilestone(2000, "💎", "Lendário"),
    XpMilestone(5000, "⭐", "Mestre"),
)

@Composable
fun StatsScreen(
    viewModel: HabitViewModel,
    onBack: () -> Unit = {},
    onAddHabit: () -> Unit = {},
    onHome: () -> Unit = {},
    onAgenda: () -> Unit = {},
    onProfile: () -> Unit = {}
) {
    val habits by viewModel.allHabits.collectAsState()
    val totalXP = habits.sumOf { it.xp }
    val maxStreak = habits.maxOfOrNull { it.streak } ?: 0
    val totalHabits = habits.size
    val doneHabits = habits.count { viewModel.isCompletedToday(it.ultimaVezCompletado) }

    // Animação de entrada
    var animationPlayed by remember { mutableStateOf(false) }
    val animatedXP by animateIntAsState(
        targetValue = if (animationPlayed) totalXP else 0,
        animationSpec = tween(1200, easing = EaseOut),
        label = "xpAnim"
    )
    LaunchedEffect(Unit) { animationPlayed = true }

    // Milestone atual
    val currentMilestone = milestones.lastOrNull { totalXP >= it.xp } ?: milestones.first()
    val nextMilestone = milestones.firstOrNull { totalXP < it.xp }

    Scaffold(
        containerColor = BgDark,
        bottomBar = {
            BottomNavBar(
                onAddHabit = onAddHabit,
                onStats = {},
                onHome = onHome,
                onAgenda = onAgenda,
                onProfile = onProfile,
                currentScreen = "stats"
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Estatísticas",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )

                Spacer(modifier = Modifier.height(16.dp))

                // ── Cards de resumo compactos ──
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    MiniStatCard(icon = "⚡", value = "$animatedXP", label = "XP", modifier = Modifier.weight(1f))
                    MiniStatCard(icon = "🔥", value = "$maxStreak", label = "Streak", modifier = Modifier.weight(1f))
                    MiniStatCard(icon = "✅", value = "$doneHabits", label = "Hoje", modifier = Modifier.weight(1f))
                    MiniStatCard(icon = "📋", value = "$totalHabits", label = "Total", modifier = Modifier.weight(1f))
                }

                Spacer(modifier = Modifier.height(24.dp))

                // ── Nível atual ──
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(Surface),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = currentMilestone.icon, fontSize = 48.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = currentMilestone.label,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Teal
                        )
                        if (nextMilestone != null) {
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Faltam ${nextMilestone.xp - totalXP} XP para ${nextMilestone.label}",
                                fontSize = 12.sp,
                                color = TextMuted
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            val progress = (totalXP - currentMilestone.xp).toFloat() /
                                    (nextMilestone.xp - currentMilestone.xp).toFloat()
                            val animatedProgress by animateFloatAsState(
                                targetValue = if (animationPlayed) progress else 0f,
                                animationSpec = tween(1200, easing = EaseOut),
                                label = "progressAnim"
                            )
                            LinearProgressIndicator(
                                progress = { animatedProgress },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(6.dp)
                                    .clip(RoundedCornerShape(6.dp)),
                                color = Teal,
                                trackColor = Surface2
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(text = "${currentMilestone.xp} XP", fontSize = 10.sp, color = TextDim)
                                Text(text = "${nextMilestone.xp} XP", fontSize = 10.sp, color = TextDim)
                            }
                        } else {
                            Text(text = "Nível máximo atingido! 🎉", fontSize = 12.sp, color = Teal)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "TRILHA DE PROGRESSO",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium,
                    color = TextMuted,
                    letterSpacing = 1.sp
                )

                Spacer(modifier = Modifier.height(16.dp))
            }

            // ── Trilha de marcos ──
            items(milestones.size) { index ->
                val milestone = milestones[index]
                val isReached = totalXP >= milestone.xp
                val isCurrent = milestone == currentMilestone

                val scale by animateFloatAsState(
                    targetValue = if (animationPlayed && isCurrent) 1.1f else 1f,
                    animationSpec = spring(stiffness = Spring.StiffnessLow),
                    label = "scaleAnim"
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Ícone do marco
                    Box(
                        modifier = Modifier
                            .size(52.dp)
                            .scale(scale)
                            .clip(CircleShape)
                            .background(if (isReached) Teal else Surface2),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = milestone.icon,
                            fontSize = if (isCurrent) 24.sp else 20.sp
                        )
                    }

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = milestone.label,
                            fontSize = 15.sp,
                            fontWeight = if (isCurrent) FontWeight.Bold else FontWeight.Normal,
                            color = if (isReached) TextPrimary else TextDim
                        )
                        Text(
                            text = "${milestone.xp} XP",
                            fontSize = 11.sp,
                            color = if (isReached) Teal else TextDim
                        )
                    }

                    if (isCurrent) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(TealDim)
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(text = "Atual", fontSize = 11.sp, color = Teal, fontWeight = FontWeight.Bold)
                        }
                    } else if (isReached) {
                        Text(text = "✓", fontSize = 16.sp, color = Teal, fontWeight = FontWeight.Bold)
                    }
                }

                // Linha conectora
                if (index < milestones.size - 1) {
                    Box(
                        modifier = Modifier
                            .padding(start = 25.dp)
                            .width(2.dp)
                            .height(24.dp)
                            .background(if (isReached) Teal else Surface2)
                    )
                }
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }
}

@Composable
fun MiniStatCard(icon: String, value: String, label: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(Surface)
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Text(text = icon, fontSize = 16.sp)
        Text(text = value, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Teal)
        Text(text = label, fontSize = 9.sp, color = TextMuted, textAlign = TextAlign.Center)
    }
}