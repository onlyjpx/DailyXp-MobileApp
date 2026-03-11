package com.example.dailyxp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dailyxp.ui.theme.*

@Composable
fun HabitItem(
    time: String,
    name: String,
    subtitle: String,
    xp: Int,
    isDone: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Horário
        Text(
            text = time,
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold,
            color = TextDim,
            modifier = Modifier.width(40.dp)
        )

        // Dot da timeline
        Box(
            modifier = Modifier
                .size(10.dp)
                .clip(CircleShape)
                .background(if (isDone) Teal else TextDim)
        )

        // Nome e subtítulo
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = name,
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                color = if (isDone) TextMuted else TextPrimary,
                textDecoration = if (isDone) TextDecoration.LineThrough else TextDecoration.None
            )
            Text(
                text = subtitle,
                fontSize = 11.sp,
                color = TextMuted
            )
        }

        // Badge XP
        if (isDone) {
            Box(
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
    }
}