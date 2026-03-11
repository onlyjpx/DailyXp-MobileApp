package com.example.dailyxp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.dailyxp.data.local.AppDatabase
import com.example.dailyxp.data.local.HabitEntity
import com.example.dailyxp.data.repository.HabitRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HabitViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: HabitRepository = HabitRepository(
        AppDatabase.getDatabase(application).habitDao()
    )

    val allHabits: StateFlow<List<HabitEntity>> = repository.allHabits.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun insertHabit(titulo: String, descricao: String, xp: Int) {
        viewModelScope.launch {
            repository.insert(
                HabitEntity(
                    titulo = titulo,
                    descricao = descricao,
                    xp = xp
                )
            )
        }
    }

    fun deleteHabit(habit: HabitEntity) {
        viewModelScope.launch {
            repository.delete(habit)
        }
    }

    fun completeHabit(habit: HabitEntity) {
        viewModelScope.launch {
            android.util.Log.d("HabitViewModel", "complete: id=${habit.id} isDone=${isCompletedToday(habit.ultimaVezCompletado)}")
            repository.update(
                habit.copy(
                    streak = habit.streak + 1,
                    ultimaVezCompletado = System.currentTimeMillis()
                )
            )
        }
    }

    fun uncompleteHabit(habit: HabitEntity) {
        viewModelScope.launch {
            val updated = habit.copy(
                streak = if (habit.streak > 0) habit.streak - 1 else 0,
                ultimaVezCompletado = null
            )
            repository.update(updated)
            android.util.Log.d("HabitViewModel", "uncomplete: id=${habit.id} ultimaVez=${updated.ultimaVezCompletado}")
        }
    }

    fun isCompletedToday(ultimaVezCompletado: Long?): Boolean {
        if (ultimaVezCompletado == null) return false
        val hoje = java.util.Calendar.getInstance().apply {
            set(java.util.Calendar.HOUR_OF_DAY, 0)
            set(java.util.Calendar.MINUTE, 0)
            set(java.util.Calendar.SECOND, 0)
            set(java.util.Calendar.MILLISECOND, 0)
        }.timeInMillis
        return ultimaVezCompletado >= hoje
    }
}