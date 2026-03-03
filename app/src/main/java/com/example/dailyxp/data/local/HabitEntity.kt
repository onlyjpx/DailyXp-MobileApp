package com.example.dailyxp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

/* Tabela de Hábitos
    Os atributos são o id do hábito, o título, oque aquele hábito é (descrição),
    quanto de xp aquele hábito gera, streak é a quantidade de dias seguidos que o hábito foi realizado,
    e a ultima vez que o hábito foi realizado.
 */
@Entity(tableName = "habits")
data class HabitEntity
    (@PrimaryKey(autoGenerate = true)
        val id: Int = 0,
        val titulo: String,
        val descricao: String? = null,
        val xp: Int = 0,
        val streak: Int = 0,
        val ultimaVezCompletado: Long? = null
    ) {
}