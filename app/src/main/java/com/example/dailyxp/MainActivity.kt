package com.example.dailyxp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.dailyxp.ui.screens.HomeScreen
import com.example.dailyxp.ui.theme.DailyXpTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DailyXpTheme {
                HomeScreen()
            }
        }
    }
}