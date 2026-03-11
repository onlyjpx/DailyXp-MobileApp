package com.example.dailyxp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.*
import com.example.dailyxp.ui.screens.CreateHabitScreen
import com.example.dailyxp.ui.screens.HomeScreen
import com.example.dailyxp.ui.screens.StatsScreen
import com.example.dailyxp.ui.theme.DailyXpTheme
import com.example.dailyxp.viewmodel.HabitViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DailyXpTheme {
                val viewModel: HabitViewModel = viewModel()
                var currentScreen by remember { mutableStateOf("home") }

                when (currentScreen) {
                    "home" -> HomeScreen(
                        viewModel = viewModel,
                        onAddHabit = { currentScreen = "create" },
                        onStats = { currentScreen = "stats" }
                    )
                    "create" -> CreateHabitScreen(
                        viewModel = viewModel,
                        onBack = { currentScreen = "home" }
                    )
                    "stats" -> StatsScreen(
                        onBack = { currentScreen = "home" },
                        onAddHabit = { currentScreen = "create" },
                        onHome = { currentScreen = "home" }
                    )
                }
            }
        }
    }
}