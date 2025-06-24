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
import dev.donmanuel.app.pomodoro.presentation.view.desktop.PomodoroDesktopLayout
import dev.donmanuel.app.pomodoro.presentation.view.mobile.PomodoroMobileLayout
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
    
    // Estado para la pantalla de selección de tipo de enfoque
    var showFocusSelector by remember { mutableStateOf(true) }
    var showCustomDialog by remember { mutableStateOf(false) }
    var focusTitle by remember { mutableStateOf("Focus") }
    
    val settings = remember { PomodoroSettings() }
    
    // Función para aplicar la configuración seleccionada
    fun applyFocusTypeSettings(title: String, focusTime: Int, shortBreakTime: Int, longBreakTime: Int, cycles: Int) {
        focusTitle = title
        Pomodoro.FOCUS.title = "Focus - $title"
        Pomodoro.FOCUS.timer = focusTime
        Pomodoro.BREAK.timer = shortBreakTime
        Pomodoro.LONG_BREAK.timer = longBreakTime
        settings.cyclesBeforeLongBreak.value = cycles
        
        // Actualizar el timer actual si no está en reproducción
        if (!isPlayPomodoro) {
            timerLeft = pomodoro.timer
        }
        
        // Cerrar la pantalla de selección
        showFocusSelector = false
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
        if (showFocusSelector) {
            // Pantalla de selección de tipo de enfoque
            FocusTypeSelector(
                onSelectFocusType = { title, focusTime, shortBreakTime, longBreakTime, cycles ->
                    if (title == "Personalizado") {
                        showCustomDialog = true
                    } else {
                        applyFocusTypeSettings(title, focusTime, shortBreakTime, longBreakTime, cycles)
                    }
                }
            )
            
            // Diálogo para configuración personalizada
            if (showCustomDialog) {
                CustomFocusDialog(
                    onDismiss = { showCustomDialog = false },
                    onConfirm = { title, focusTime, shortBreakTime, longBreakTime, cycles ->
                        applyFocusTypeSettings(title, focusTime, shortBreakTime, longBreakTime, cycles)
                    }
                )
            }
        } else {
            // Pantalla principal del Pomodoro
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
                        // Detener el timer si está en reproducción
                        isPlayPomodoro = false
                        // Mostrar la pantalla de selección de tipo de enfoque
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
                        // Detener el timer si está en reproducción
                        isPlayPomodoro = false
                        // Mostrar la pantalla de selección de tipo de enfoque
                        showFocusSelector = true 
                    }
                )
            }
        }
    }
}