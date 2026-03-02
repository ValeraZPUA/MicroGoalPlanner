package com.microgoalplanner.domain.model

import java.time.LocalDate

enum class GoalStatus { ACTIVE, COMPLETED }
enum class StageStatus { TODO, IN_PROGRESS, COMPLETED }

data class Goal(
    val id: Long = 0,
    val name: String,
    val targetAmount: Long,
    val currency: String,
    val deadline: LocalDate,
    val priority: Int = 1,
    val status: GoalStatus = GoalStatus.ACTIVE
)

data class Stage(
    val id: Long = 0,
    val goalId: Long,
    val name: String,
    val amount: Long,
    val deadline: LocalDate,
    val description: String = "",
    val priority: Int = 1,
    val status: StageStatus = StageStatus.TODO,
    val note: String = ""
)

data class GoalWithProgress(
    val goal: Goal,
    val savedAmount: Long,
    val progressPercent: Int,
    val remainingAmount: Long
)

data class AnalyticsSummary(
    val totalGoals: Int,
    val completedGoals: Int,
    val totalStages: Int,
    val completedStages: Int,
    val projectedCompletionRatePercent: Int
)
