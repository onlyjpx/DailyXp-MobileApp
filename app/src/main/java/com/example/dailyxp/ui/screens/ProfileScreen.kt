package com.example.dailyxp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dailyxp.ui.theme.*
import com.example.dailyxp.viewmodel.HabitViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    viewModel: HabitViewModel,
    onHome: () -> Unit = {},
    onStats: () -> Unit = {},
    onAgenda: () -> Unit = {},
    onAddHabit: () -> Unit = {}
) {
    val habits by viewModel.allHabits.collectAsState()
    val userName by viewModel.userName.collectAsState()

    var nameInput by remember { mutableStateOf(userName) }

    val totalXP = habits.sumOf { it.xp }
    val maxStreak = habits.maxOfOrNull { it.streak } ?: 0
    val totalHabits = habits.size

    Scaffold(
        containerColor = BgDark,
        bottomBar = {
            BottomNavBar(
                onAddHabit = onAddHabit,
                onStats = onStats,
                onHome = onHome,
                onAgenda = onAgenda,
                onProfile = {},
                currentScreen = "perfil"
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            // ── Avatar + nome ────────────────────────────────────────
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(Surface2),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "🧙", fontSize = 36.sp)
                }
                Text(
                    text = userName.ifBlank { "Explorador" },
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                Text(
                    text = "Membro DailyXp",
                    fontSize = 12.sp,
                    color = TextMuted
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // ── CONTA ────────────────────────────────────────────────
            SectionLabel("CONTA")

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
                        text = "👤  NOME",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Medium,
                        color = TextMuted,
                        letterSpacing = 0.8.sp
                    )
                }
                TextField(
                    value = nameInput,
                    onValueChange = {
                        nameInput = it
                        viewModel.updateUserName(it)
                    },
                    placeholder = { Text("Seu nome", color = TextDim, fontSize = 16.sp, fontWeight = FontWeight.SemiBold) },
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

            // ── APARÊNCIA ─────────────────────────────────────────────
            SectionLabel("APARÊNCIA")

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(Surface)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text("🎨", fontSize = 20.sp)
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Tema",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = TextPrimary
                        )
                        Text(
                            text = "Dark Mode",
                            fontSize = 12.sp,
                            color = TextMuted
                        )
                    }
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(Yellow.copy(alpha = 0.15f))
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "Em Breve",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Yellow
                        )
                    }
                }
                HorizontalDivider(color = Surface2, thickness = 1.dp, modifier = Modifier.padding(horizontal = 16.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text("🌐", fontSize = 20.sp)
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Idioma",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = TextPrimary
                        )
                        Text(
                            text = "Português",
                            fontSize = 12.sp,
                            color = TextMuted
                        )
                    }
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(Yellow.copy(alpha = 0.15f))
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "Em Breve",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Yellow
                        )
                    }
                }
            }

            // ── ESTATÍSTICAS ─────────────────────────────────────────
            SectionLabel("ESTATÍSTICAS")

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                ProfileStatChip("⚡", "$totalXP", "XP Total", modifier = Modifier.weight(1f))
                ProfileStatChip("🔥", "$maxStreak", "Sequência", modifier = Modifier.weight(1f))
                ProfileStatChip("📋", "$totalHabits", "Hábitos", modifier = Modifier.weight(1f))
            }

            // ── SOBRE ─────────────────────────────────────────────────
            SectionLabel("SOBRE")

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(Surface)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text("📱", fontSize = 20.sp)
                    Text(
                        text = "Versão do App",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = TextPrimary,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = "1.0.0",
                        fontSize = 13.sp,
                        color = TextMuted
                    )
                }
                HorizontalDivider(color = Surface2, thickness = 1.dp, modifier = Modifier.padding(horizontal = 16.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text("✨", fontSize = 20.sp)
                    Text(
                        text = "Feito com dedicação",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = TextPrimary,
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
private fun SectionLabel(text: String) {
    Text(
        text = text,
        fontSize = 11.sp,
        fontWeight = FontWeight.Medium,
        color = TextMuted,
        letterSpacing = 1.sp
    )
}

@Composable
private fun ProfileStatChip(icon: String, value: String, label: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(14.dp))
            .background(Surface)
            .padding(vertical = 14.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(text = icon, fontSize = 20.sp)
        Text(text = value, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Teal)
        Text(text = label, fontSize = 10.sp, color = TextMuted)
    }
}
