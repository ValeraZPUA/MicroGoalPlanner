package com.microgoalplanner.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface GoalDao {
    @Query("SELECT * FROM goals ORDER BY deadlineEpochDay ASC")
    fun observeGoals(): Flow<List<GoalEntity>>

    @Query("SELECT * FROM goals WHERE id = :id")
    suspend fun getGoalById(id: Long): GoalEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(goal: GoalEntity): Long

    @Update
    suspend fun update(goal: GoalEntity)

    @Query("DELETE FROM goals WHERE id = :id")
    suspend fun deleteById(id: Long)
}

@Dao
interface StageDao {
    @Query("SELECT * FROM stages ORDER BY deadlineEpochDay ASC")
    fun observeStages(): Flow<List<StageEntity>>

    @Query("SELECT * FROM stages WHERE goalId = :goalId ORDER BY deadlineEpochDay ASC")
    fun observeStagesByGoal(goalId: Long): Flow<List<StageEntity>>

    @Query("SELECT * FROM stages WHERE id = :id")
    suspend fun getById(id: Long): StageEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(stage: StageEntity): Long

    @Update
    suspend fun update(stage: StageEntity)

    @Query("DELETE FROM stages WHERE id = :id")
    suspend fun deleteById(id: Long)
}

@Dao
interface SettingsDao {
    @Query("SELECT * FROM settings WHERE id = 1")
    fun observeSettings(): Flow<SettingsEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(settings: SettingsEntity)

    @Query("DELETE FROM goals")
    suspend fun clearGoals()

    @Query("DELETE FROM stages")
    suspend fun clearStages()
}
