package com.example.dailyxp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.*
import com.example.dailyxp.data.local.HabitEntity
import com.example.dailyxp.ui.screens.AgendaScreen
import com.example.dailyxp.ui.screens.HabitFormScreen
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
                var habitToEdit by remember { mutableStateOf<HabitEntity?>(null) }

                when (currentScreen) {
                    "home" -> HomeScreen(
                        viewModel = viewModel,
                        onAddHabit = { currentScreen = "create" },
                        onStats = { currentScreen = "stats" },
                        onAgenda = { currentScreen = "agenda" },
                        onEditHabit = { habit ->
                            habitToEdit = habit
                            currentScreen = "edit"
                        }
                    )
                    "agenda" -> AgendaScreen(
                        viewModel = viewModel,
                        onAddHabit = { currentScreen = "create" },
                        onHome = { currentScreen = "home" },
                        onStats = { currentScreen = "stats" },
                        onEditHabit = { habit ->
                            habitToEdit = habit
                            currentScreen = "edit"
                        }
                    )
                    "create" -> HabitFormScreen(
                        viewModel = viewModel,
                        onBack = { currentScreen = "home" }
                    )
                    "edit" -> habitToEdit?.let { habit ->
                        HabitFormScreen(
                            habit = habit,
                            viewModel = viewModel,
                            onBack = { currentScreen = "home" }
                        )
                    }
                    "stats" -> StatsScreen(
                        viewModel = viewModel,
                        onBack = { currentScreen = "home" },
                        onAddHabit = { currentScreen = "create" },
                        onHome = { currentScreen = "home" },
                        onAgenda = { currentScreen = "agenda" }
                    )
                }
            }
        }
    }
}