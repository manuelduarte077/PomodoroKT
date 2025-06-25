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
import dev.donmanuel.app.pomodoro.presentation.components.CustomFocusDialog
import dev.donmanuel.app.pomodoro.presentation.components.FocusTypeSelector
import dev.donmanuel.app.pomodoro.presentation.components.NotificationAlert
import dev.donmanuel.app.pomodoro.presentation.view.desktop.PomodoroDesktopLayout
import dev.donmanuel.app.pomodoro.presentation.view.mobile.PomodoroMobileLayout
import dev.donmanuel.app.pomodoro.utils.NotificationManager
import dev.donmanuel.app.pomodoro.utils.platform
import kotlinx.coroutines.delay

@Composable
fun PomodoroApp() {
    var pomodoro by remember { mutableStateOf(Pomodoro.FOCUS) }
    var isPlayPomodoro by remember { mutableStateOf(false) }
    var isShowDialog by remember { mutableStateOf(false) }
    var isShowSettingsDialog by remember { mutableStateOf(false) }
    var timerLeft by remember { mutableStateOf(pomodoro.timer) }
    var speedTime by remember { mutableStateOf(Speed.NORMAL) }
    var completedPomodoros by remember { mutableStateOf(0) }
    
    var showFocusSelector by remember { mutableStateOf(true) }
    var showCustomDialog by remember { mutableStateOf(false) }

    val settings = remember { PomodoroSettings() }

    val notificationManager = remember { NotificationManager() }
    
    fun applyFocusTypeSettings(title: String, focusTime: Int, shortBreakTime: Int, longBreakTime: Int, cycles: Int) {
        Pomodoro.FOCUS.title = "Focus - $title"
        Pomodoro.FOCUS.timer = focusTime
        Pomodoro.BREAK.timer = shortBreakTime
        Pomodoro.LONG_BREAK.timer = longBreakTime
        settings.cyclesBeforeLongBreak.value = cycles
        
        if (!isPlayPomodoro) {
            timerLeft = pomodoro.timer
        }
        
        // Actualizar el título en el gestor de notificaciones
        notificationManager.currentTitle = title
        
        showFocusSelector = false
    }

    LaunchedEffect(key1 = isPlayPomodoro) {
        if (isPlayPomodoro) {
            notificationManager.resetNotifications()
        }
        
        while (isPlayPomodoro && timerLeft > 0) {
            delay(speedTime.speed)
            timerLeft--
            
            // Verificar si es momento de mostrar una notificación
            notificationManager.checkTimeForNotifications(timerLeft, isPlayPomodoro)

            if (timerLeft <= 0) {
                when (pomodoro) {
                    Pomodoro.FOCUS -> {
                        completedPomodoros++
                        // Check if it's time for a long break
                        if (completedPomodoros >= settings.cyclesBeforeLongBreak.value) {
                            pomodoro = Pomodoro.LONG_BREAK
                            completedPomodoros = 0
                            notificationManager.finishedMessage = "¡Tiempo de Focus completado! Es hora de tomar un descanso largo."
                        } else {
                            pomodoro = Pomodoro.BREAK
                            notificationManager.finishedMessage = "¡Tiempo de Focus completado! Es hora de tomar un descanso corto."
                        }
                        timerLeft = pomodoro.timer
                        isPlayPomodoro = false
                    }
                    Pomodoro.BREAK, Pomodoro.LONG_BREAK -> {
                        pomodoro = Pomodoro.FOCUS
                        notificationManager.finishedMessage = "¡Tiempo de descanso completado! Es hora de volver a concentrarse."
                        timerLeft = pomodoro.timer
                        isPlayPomodoro = false
                    }
                }
            }
        }
    }

    val platform = platform()

    MaterialTheme {
        NotificationAlert(
            showNotification = notificationManager.showFiveMinNotification,
            title = "Alerta de tiempo - ${pomodoro.title}",
            message = notificationManager.fiveMinMessage,
            backgroundColor = pomodoro.backgroundColor,
            textColor = pomodoro.textColor,
            onDismiss = {}
        )
        
        NotificationAlert(
            showNotification = notificationManager.showThreeMinNotification,
            title = "Alerta de tiempo - ${pomodoro.title}",
            message = notificationManager.threeMinMessage,
            backgroundColor = pomodoro.backgroundColor,
            textColor = pomodoro.textColor,
            onDismiss = {}
        )
        
        NotificationAlert(
            showNotification = notificationManager.showFinishedNotification,
            title = "¡Tiempo completado! - ${pomodoro.title}",
            message = notificationManager.finishedMessage,
            backgroundColor = pomodoro.backgroundColor,
            textColor = pomodoro.textColor,
            onDismiss = {}
        )
        
        if (showFocusSelector) {
            FocusTypeSelector(
                onSelectFocusType = { title, focusTime, shortBreakTime, longBreakTime, cycles ->
                    if (title == "Personalizado") {
                        showCustomDialog = true
                    } else {
                        applyFocusTypeSettings(title, focusTime, shortBreakTime, longBreakTime, cycles)
                    }
                }
            )
            
            if (showCustomDialog) {
                CustomFocusDialog(
                    onDismiss = { showCustomDialog = false },
                    onConfirm = { title, focusTime, shortBreakTime, longBreakTime, cycles ->
                        applyFocusTypeSettings(title, focusTime, shortBreakTime, longBreakTime, cycles)
                    }
                )
            }
        } else {
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
                    onSettingsToggle = { isShowSettingsDialog = it },
                    onBackToFocusSelector = { 
                        isPlayPomodoro = false
                        notificationManager.resetNotifications()
                        showFocusSelector = true
                    }
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
                    onSettingsToggle = { isShowSettingsDialog = it },
                    onBackToFocusSelector = { 
                        isPlayPomodoro = false
                        notificationManager.resetNotifications()
                        showFocusSelector = true
                    }
                )
            }
        }
    }
}