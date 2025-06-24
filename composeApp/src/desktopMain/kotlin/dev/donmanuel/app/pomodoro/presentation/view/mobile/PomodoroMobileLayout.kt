package dev.donmanuel.app.pomodoro.presentation.view.mobile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import dev.donmanuel.app.pomodoro.data.Pomodoro
import dev.donmanuel.app.pomodoro.data.PomodoroSettings
import dev.donmanuel.app.pomodoro.data.Speed
import dev.donmanuel.app.pomodoro.presentation.components.PomodoroContent
import dev.donmanuel.app.pomodoro.presentation.components.SettingsDialog
import dev.donmanuel.app.pomodoro.utils.CustomDialog
import org.jetbrains.compose.resources.painterResource
import pomodoro.composeapp.generated.resources.Res
import pomodoro.composeapp.generated.resources.ic_settings

@Composable
fun PomodoroMobileLayout(
    pomodoro: Pomodoro,
    isPlayPomodoro: Boolean,
    timerLeft: Int,
    speedTime: Speed,
    isShowDialog: Boolean,
    isShowSettingsDialog: Boolean,
    settings: PomodoroSettings,
    completedPomodoros: Int,
    onPlayPause: (Boolean) -> Unit,
    onSpeedChange: (Speed) -> Unit,
    onDialogToggle: (Boolean) -> Unit,
    onSettingsToggle: (Boolean) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = pomodoro.backgroundColor),
    ) {
        // Settings button in the top-right corner
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Image(
                modifier = Modifier
                    .size(24.dp)
                    .clickable { onSettingsToggle(true) },
                painter = painterResource(Res.drawable.ic_settings),
                colorFilter = ColorFilter.tint(pomodoro.textColor.copy(alpha = 0.7f)),
                contentDescription = "Settings"
            )
        }
        
        Column(
            modifier = Modifier
                .width(289.dp)
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            PomodoroContent(
                pomodoro = pomodoro,
                isPlayPomodoro = isPlayPomodoro,
                timerLeft = timerLeft,
                speedTime = speedTime,
                completedPomodoros = completedPomodoros,
                totalPomodoros = settings.cyclesBeforeLongBreak.value,
                onPlayPause = onPlayPause,
                onSpeedChange = onSpeedChange,
                onDialogToggle = onDialogToggle
            )
        }

        if (isShowDialog) {
            CustomDialog(
                textColor = pomodoro.textColor,
                backgroundColor = pomodoro.backgroundColor,
                onCloseDialog = { onDialogToggle(false) }
            )
        }
        
        if (isShowSettingsDialog) {
            SettingsDialog(
                settings = settings,
                textColor = pomodoro.textColor,
                backgroundColor = pomodoro.backgroundColor,
                onCloseDialog = { onSettingsToggle(false) },
                onSaveSettings = { /* Settings are automatically saved via MutableState */ }
            )
        }
    }
}