package com.microgoalplanner

import android.app.Application
import com.microgoalplanner.data.local.AppDatabase
import com.microgoalplanner.data.repository.PlannerRepository

class App : Application() {
    lateinit var repository: PlannerRepository
        private set

    override fun onCreate() {
        super.onCreate()
        val db = AppDatabase.getInstance(this)
        repository = PlannerRepository(db.goalDao(), db.stageDao(), db.settingsDao())
    }
}
