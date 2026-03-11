package com.example.dailyxp.ui.components

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dailyxp.ui.theme.*
import kotlinx.coroutines.delay

@Composable
fun HabitItem(
    time: String,
    name: String,
    subtitle: String,
    xp: Int,
    isDone: Boolean,
    onComplete: () -> Unit = {}
) {
    var showXpFloat by remember { mutableStateOf(false) }
    val offsetY by animateFloatAsState(
        targetValue = if (showXpFloat) -40f else 0f,
        animationSpec = tween(600, easing = EaseOut),
        label = "xpFloat"
    )
    val alpha by animateFloatAsState(
        targetValue = if (showXpFloat) 0f else 1f,
        animationSpec = tween(600),
        label = "xpAlpha"
    )

    LaunchedEffect(showXpFloat) {
        if (showXpFloat) {
            delay(700)
            showXpFloat = false
        }
    }

    Box {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            Text( // Horário
                text = time,
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                color = TextDim,
                modifier = Modifier.width(40.dp)
            )

            Box( // Dot da timeline
                modifier = Modifier
                    .size(10.dp)
                    .clip(CircleShape)
                    .background(if (isDone) Teal else TextDim)
            )

            Column(modifier = Modifier.weight(1f)) {
                Text( // Nome e subtítulo
                    text = name,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = if (isDone) TextMuted else TextPrimary,
                    textDecoration = if (isDone) TextDecoration.LineThrough else TextDecoration.None
                )
                Text(text = subtitle, fontSize = 11.sp, color = TextMuted)
            }

            if (isDone) {
                Box(// Badge XP
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(TealDim)
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = "+$xp xp",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Teal
                    )
                }
            }

            IconButton(
                onClick = {
                    if (!isDone) showXpFloat = true
                    onComplete()
                },
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(if (isDone) Teal else Surface2)
            ) {
                Icon(
                    imageVector = Icons.Filled.Check,
                    contentDescription = "Concluir",
                    tint = if (isDone) BgDark else TextDim,
                    modifier = Modifier.size(16.dp)
                )
            }
        }

        // XP flutuando
        if (showXpFloat) {
            Text(
                text = "+$xp xp",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = Teal,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 40.dp)
                    .offset(y = offsetY.dp)
                    .alpha(alpha)
            )
        }
    }
}