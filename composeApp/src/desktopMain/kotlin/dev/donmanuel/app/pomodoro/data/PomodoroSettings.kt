package dev.donmanuel.app.pomodoro.data

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

/**
 * Representa la configuración de un pomodoro
 */
data class PomodoroSettings(
    val focusTime: MutableState<Int> = mutableStateOf(1500),
    val shortBreakTime: MutableState<Int> = mutableStateOf(300),
    val longBreakTime: MutableState<Int> = mutableStateOf(900),
    val cyclesBeforeLongBreak: MutableState<Int> = mutableStateOf(4)
)
