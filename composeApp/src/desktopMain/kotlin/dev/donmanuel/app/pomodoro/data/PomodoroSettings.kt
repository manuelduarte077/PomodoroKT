package dev.donmanuel.app.pomodoro.data

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class PomodoroSettings(
    val focusTime: MutableState<Int> = mutableStateOf(1500), // 25 minutes
    val shortBreakTime: MutableState<Int> = mutableStateOf(300), // 5 minutes
    val longBreakTime: MutableState<Int> = mutableStateOf(900), // 15 minutes
    val cyclesBeforeLongBreak: MutableState<Int> = mutableStateOf(4) // Long break after 4 pomodoros
)
