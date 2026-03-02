package com.microgoalplanner.notifications

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import java.util.concurrent.TimeUnit

class ReminderScheduler(private val context: Context) {
    fun scheduleDeadlineReminder(stageId: Long) {
        val request = OneTimeWorkRequestBuilder<ReminderWorker>()
            .setInputData(workDataOf("type" to "stage", "stageId" to stageId))
            .setInitialDelay(24, TimeUnit.HOURS)
            .build()
        WorkManager.getInstance(context).enqueue(request)
    }

    fun scheduleWeeklyGoalReminder() {
        val request = PeriodicWorkRequestBuilder<ReminderWorker>(7, TimeUnit.DAYS)
            .setInputData(workDataOf("type" to "weekly"))
            .build()
        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "weekly_goal_reminder",
            ExistingPeriodicWorkPolicy.UPDATE,
            request
        )
    }
}
