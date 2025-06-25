package dev.donmanuel.app.pomodoro.data

/**
 * Representa un preset de configuración para diferentes tipos de enfoque
 */
data class FocusPreset(
    val name: String,
    val focusTime: Int,
    val shortBreakTime: Int,
    val longBreakTime: Int,
    val cyclesBeforeLongBreak: Int = 4
)