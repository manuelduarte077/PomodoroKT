package dev.donmanuel.app.pomodoro.presentation.view.desktop

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.donmanuel.app.pomodoro.data.Pomodoro
import dev.donmanuel.app.pomodoro.data.PomodoroSettings
import dev.donmanuel.app.pomodoro.data.Speed
import dev.donmanuel.app.pomodoro.presentation.components.PomodoroContent
import dev.donmanuel.app.pomodoro.presentation.components.SettingsDialog
import dev.donmanuel.app.pomodoro.presentation.ui.theme.GetFontPoppinsMedium
import dev.donmanuel.app.pomodoro.utils.CustomDialog
import org.jetbrains.compose.resources.painterResource
import pomodoro.composeapp.generated.resources.Res
import pomodoro.composeapp.generated.resources.ic_settings

@Composable
fun PomodoroDesktopLayout(
    modifier: Modifier = Modifier,
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
    onSettingsToggle: (Boolean) -> Unit,
    onBackToFocusSelector: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = pomodoro.backgroundColor)
    ) {
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

        Row(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp)
        ) {
            Text(
                text = "Cambiar tipo",
                fontFamily = GetFontPoppinsMedium(),
                fontSize = 14.sp,
                color = pomodoro.textColor.copy(alpha = 0.7f),
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .clickable { onBackToFocusSelector() }
                    .padding(8.dp)
            )
        }

        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
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
                onSaveSettings = {
                    onSettingsToggle(true)
                }
            )
        }
    }
}