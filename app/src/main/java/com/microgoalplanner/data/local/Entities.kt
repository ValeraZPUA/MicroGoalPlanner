package com.microgoalplanner.data.local

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "goals")
data class GoalEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val targetAmount: Long,
    val currency: String,
    val deadlineEpochDay: Long,
    val priority: Int,
    val status: String
)

@Entity(
    tableName = "stages",
    foreignKeys = [
        ForeignKey(
            entity = GoalEntity::class,
            parentColumns = ["id"],
            childColumns = ["goalId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("goalId")]
)
data class StageEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val goalId: Long,
    val name: String,
    val amount: Long,
    val deadlineEpochDay: Long,
    val description: String,
    val priority: Int,
    val status: String,
    val note: String
)

@Entity(tableName = "settings")
data class SettingsEntity(
    @PrimaryKey val id: Int = 1,
    val currency: String,
    val notificationsEnabled: Boolean,
    val weeklyReminderEnabled: Boolean,
    val onboardingCompleted: Boolean,
    val privacyMode: Boolean
)
