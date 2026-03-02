package com.microgoalplanner.validation

import java.time.LocalDate

object ValidationRules {
    private val textRegex = Regex("^[a-zA-Zа-яА-Я0-9 ]{2,64}$")

    fun validateText(value: String): Boolean = textRegex.matches(value.trim())

    fun validateNumber(value: Long): Boolean = value > 0 && value <= 999_999_999

    fun validateDate(value: LocalDate): Boolean = !value.isBefore(LocalDate.now())

    fun validateSelection(value: String?): Boolean = !value.isNullOrBlank()

    fun validateImageFormat(fileName: String): Boolean {
        val lowered = fileName.lowercase()
        return lowered.endsWith(".jpg") || lowered.endsWith(".jpeg") || lowered.endsWith(".png")
    }

    fun validateImageSize(bytes: Long): Boolean = bytes in 1..2_000_000
}
