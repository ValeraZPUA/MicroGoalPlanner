package com.microgoalplanner.data.repository

import com.microgoalplanner.data.local.GoalDao
import com.microgoalplanner.data.local.GoalEntity
import com.microgoalplanner.data.local.SettingsDao
import com.microgoalplanner.data.local.SettingsEntity
import com.microgoalplanner.data.local.StageDao
import com.microgoalplanner.data.local.StageEntity
import com.microgoalplanner.domain.model.AnalyticsSummary
import com.microgoalplanner.domain.model.Goal
import com.microgoalplanner.domain.model.GoalStatus
import com.microgoalplanner.domain.model.GoalWithProgress
import com.microgoalplanner.domain.model.Stage
import com.microgoalplanner.domain.model.StageStatus
import java.time.LocalDate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class PlannerRepository(
    private val goalDao: GoalDao,
    private val stageDao: StageDao,
    private val settingsDao: SettingsDao
) {
    fun observeGoalsWithProgress(): Flow<List<GoalWithProgress>> =
        combine(goalDao.observeGoals(), stageDao.observeStages()) { goals, stages ->
            goals.map { goal ->
                val goalStages = stages.filter { it.goalId == goal.id }
                val saved = goalStages.filter { it.status == StageStatus.COMPLETED.name }.sumOf { it.amount }
                val progress = if (goal.targetAmount == 0L) 0 else ((saved * 100) / goal.targetAmount).toInt().coerceIn(0, 100)
                GoalWithProgress(
                    goal = goal.toDomain(),
                    savedAmount = saved,
                    progressPercent = progress,
                    remainingAmount = (goal.targetAmount - saved).coerceAtLeast(0)
                )
            }
        }

    fun observeStages(): Flow<List<Stage>> = stageDao.observeStages().map { list -> list.map { it.toDomain() } }

    fun observeStagesByGoal(goalId: Long): Flow<List<Stage>> =
        stageDao.observeStagesByGoal(goalId).map { list -> list.map { it.toDomain() } }

    suspend fun createGoal(goal: Goal): Long = goalDao.insert(goal.toEntity())
    suspend fun updateGoal(goal: Goal) = goalDao.update(goal.toEntity())
    suspend fun deleteGoal(goalId: Long) = goalDao.deleteById(goalId)

    suspend fun createStage(stage: Stage): Long = stageDao.insert(stage.toEntity())
    suspend fun updateStage(stage: Stage) = stageDao.update(stage.toEntity())
    suspend fun getStage(stageId: Long): Stage? = stageDao.getById(stageId)?.toDomain()

    fun observeSettings(): Flow<SettingsEntity?> = settingsDao.observeSettings()
    suspend fun getSettings(): SettingsEntity? = settingsDao.observeSettings().first()
    suspend fun upsertSettings(settings: SettingsEntity) = settingsDao.upsert(settings)

    suspend fun clearAllData() {
        settingsDao.clearStages()
        settingsDao.clearGoals()
    }

    fun observeAnalytics(): Flow<AnalyticsSummary> =
        combine(goalDao.observeGoals(), stageDao.observeStages()) { goals, stages ->
            val completedGoals = goals.count { it.status == GoalStatus.COMPLETED.name }
            val completedStages = stages.count { it.status == StageStatus.COMPLETED.name }
            val projection = if (stages.isEmpty()) 0 else (completedStages * 100 / stages.size)
            AnalyticsSummary(
                totalGoals = goals.size,
                completedGoals = completedGoals,
                totalStages = stages.size,
                completedStages = completedStages,
                projectedCompletionRatePercent = projection
            )
        }
}

private fun GoalEntity.toDomain() = Goal(
    id = id,
    name = name,
    targetAmount = targetAmount,
    currency = currency,
    deadline = LocalDate.ofEpochDay(deadlineEpochDay),
    priority = priority,
    status = GoalStatus.valueOf(status)
)

private fun StageEntity.toDomain() = Stage(
    id = id,
    goalId = goalId,
    name = name,
    amount = amount,
    deadline = LocalDate.ofEpochDay(deadlineEpochDay),
    description = description,
    priority = priority,
    status = StageStatus.valueOf(status),
    note = note
)

private fun Goal.toEntity() = GoalEntity(
    id = id,
    name = name,
    targetAmount = targetAmount,
    currency = currency,
    deadlineEpochDay = deadline.toEpochDay(),
    priority = priority,
    status = status.name
)

private fun Stage.toEntity() = StageEntity(
    id = id,
    goalId = goalId,
    name = name,
    amount = amount,
    deadlineEpochDay = deadline.toEpochDay(),
    description = description,
    priority = priority,
    status = status.name,
    note = note
)
