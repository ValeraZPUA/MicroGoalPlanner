package com.microgoalplanner.domain.model

import java.time.LocalDate

data class Goal(
    val id: Long = 0,
    val name: String,
    val targetAmount: Long,
    val currency: String,
    val deadline: LocalDate,
    val priority: Int = 0,
    val completed: Boolean = false
)

data class Stage(
    val id: Long = 0,
    val goalId: Long,
    val name: String,
    val amount: Long,
    val deadline: LocalDate,
    val description: String = "",
    val completed: Boolean = false
)
