package dev.donmanuel.app.pomodoro

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import dev.donmanuel.app.pomodoro.data.Pomodoro
import dev.donmanuel.app.pomodoro.data.PomodoroSettings
import dev.donmanuel.app.pomodoro.data.Speed
import dev.donmanuel.app.pomodoro.presentation.view.desktop.PomodoroDesktopLayout
import dev.donmanuel.app.pomodoro.presentation.view.mobile.PomodoroMobileLayout
import kotlinx.coroutines.delay
import dev.donmanuel.app.pomodoro.utils.platform

@Composable
fun PomodoroApp() {
    var pomodoro by remember { mutableStateOf(Pomodoro.FOCUS) }
    var isPlayPomodoro by remember { mutableStateOf(false) }
    var isShowDialog by remember { mutableStateOf(false) }
    var isShowSettingsDialog by remember { mutableStateOf(false) }
    var timerLeft by remember { mutableStateOf(pomodoro.timer) }
    var speedTime by remember { mutableStateOf(Speed.NORMAL) }
    var completedPomodoros by remember { mutableStateOf(0) }
    
    val settings = remember { PomodoroSettings() }
    
    // Update timer values from settings
    LaunchedEffect(settings) {
        Pomodoro.FOCUS.timer = settings.focusTime.value
        Pomodoro.BREAK.timer = settings.shortBreakTime.value
        Pomodoro.LONG_BREAK.timer = settings.longBreakTime.value
        
        // Update current timer if needed
        if (pomodoro == Pomodoro.FOCUS && !isPlayPomodoro) {
            timerLeft = settings.focusTime.value
        } else if (pomodoro == Pomodoro.BREAK && !isPlayPomodoro) {
            timerLeft = settings.shortBreakTime.value
        } else if (pomodoro == Pomodoro.LONG_BREAK && !isPlayPomodoro) {
            timerLeft = settings.longBreakTime.value
        }
    }

    LaunchedEffect(key1 = isPlayPomodoro) {
        while (isPlayPomodoro && timerLeft > 0) {
            delay(speedTime.speed)
            timerLeft--

            if (timerLeft <= 0) {
                when (pomodoro) {
                    Pomodoro.FOCUS -> {
                        completedPomodoros++
                        // Check if it's time for a long break
                        if (completedPomodoros >= settings.cyclesBeforeLongBreak.value) {
                            pomodoro = Pomodoro.LONG_BREAK
                            completedPomodoros = 0
                        } else {
                            pomodoro = Pomodoro.BREAK
                        }
                        timerLeft = pomodoro.timer
                        isPlayPomodoro = false
                    }
                    Pomodoro.BREAK, Pomodoro.LONG_BREAK -> {
                        pomodoro = Pomodoro.FOCUS
                        timerLeft = pomodoro.timer
                        isPlayPomodoro = false
                    }
                }
            }
        }
    }

    val platform = platform()

    MaterialTheme {
        if (platform.isDesktop) {
            PomodoroDesktopLayout(
                pomodoro = pomodoro,
                isPlayPomodoro = isPlayPomodoro,
                timerLeft = timerLeft,
                speedTime = speedTime,
                isShowDialog = isShowDialog,
                isShowSettingsDialog = isShowSettingsDialog,
                settings = settings,
                completedPomodoros = completedPomodoros,
                onPlayPause = { isPlayPomodoro = it },
                onSpeedChange = { speedTime = it },
                onDialogToggle = { isShowDialog = it },
                onSettingsToggle = { isShowSettingsDialog = it }
            )
        } else {
            PomodoroMobileLayout(
                pomodoro = pomodoro,
                isPlayPomodoro = isPlayPomodoro,
                timerLeft = timerLeft,
                speedTime = speedTime,
                isShowDialog = isShowDialog,
                isShowSettingsDialog = isShowSettingsDialog,
                settings = settings,
                completedPomodoros = completedPomodoros,
                onPlayPause = { isPlayPomodoro = it },
                onSpeedChange = { speedTime = it },
                onDialogToggle = { isShowDialog = it },
                onSettingsToggle = { isShowSettingsDialog = it }
            )
        }
    }
}