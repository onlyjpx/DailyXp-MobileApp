package com.example.dailyxp.data.repository

import com.example.dailyxp.data.local.HabitDao
import com.example.dailyxp.data.local.HabitEntity
import kotlinx.coroutines.flow.Flow

class HabitRepository (private val habitDao: HabitDao){
    val allHabits: Flow<List<HabitEntity>> = habitDao.getAllHabits()

    suspend fun insert(habit: HabitEntity) {
        habitDao.insertHabit(habit)
    }

    suspend fun delete(habit: HabitEntity) {
        habitDao.deleteHabit(habit)
    }

    suspend fun update(habit: HabitEntity) {
        habitDao.updateHabit(habit)
    }
}