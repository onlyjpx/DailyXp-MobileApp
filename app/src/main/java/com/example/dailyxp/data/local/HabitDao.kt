package com.example.dailyxp.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

/* DAO da tabela de hábitos
    Cria os métodos para acessar os dados do banco de dados.
    Os métodos são os seguintes:
        - getAllHabits(): Flow<List<HabitEntity>> -> Retorna todos os hábitos.
        - insertHabit(habit: HabitEntity) -> Adiciona um novo hábito.
        - deleteHabit(habit: HabitEntity) -> Deleta um hábito.
        - updateHabit(habit: HabitEntity) -> Atualiza um hábito.
*/
@Dao
interface HabitDao {

    @Query("SELECT * from habits ORDER by id DESC")
    fun getAllHabits(): Flow<List<HabitEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHabit(habit: HabitEntity)

    @Delete
    suspend fun deleteHabit(habit: HabitEntity)

    @Update
    suspend fun updateHabit(habit: HabitEntity)
}
