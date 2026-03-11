package com.example.dailyxp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import com.example.dailyxp.ui.screens.CreateHabitScreen
import com.example.dailyxp.ui.screens.HomeScreen
import com.example.dailyxp.ui.screens.StatsScreen
import com.example.dailyxp.ui.theme.DailyXpTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DailyXpTheme {
                var currentScreen by remember { mutableStateOf("home") }

                when (currentScreen) {
                    "home" -> HomeScreen(
                        onAddHabit = { currentScreen = "create" },
                        onStats = { currentScreen = "stats" }
                    )
                    "create" -> CreateHabitScreen(
                        onBack = { currentScreen = "home" }
                    )
                    "stats" -> StatsScreen(
                        onBack = { currentScreen = "home" }
                    )
                }
            }
        }
    }
}