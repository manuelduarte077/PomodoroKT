package dev.donmanuel.app.pomodoro.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import dev.donmanuel.app.pomodoro.data.PomodoroSettings
import dev.donmanuel.app.pomodoro.presentation.ui.theme.GetFontPoppinsMedium
import dev.donmanuel.app.pomodoro.presentation.ui.theme.GetFontPoppinsSemiBold
import org.jetbrains.compose.resources.painterResource
import pomodoro.composeapp.generated.resources.Res
import pomodoro.composeapp.generated.resources.ic_close

@Composable
fun SettingsDialog(
    settings: PomodoroSettings,
    textColor: Color,
    backgroundColor: Color,
    onCloseDialog: () -> Unit,
    onSaveSettings: (PomodoroSettings) -> Unit
) {
    Dialog(onDismissRequest = { onCloseDialog() }) {
        Surface(
            modifier = Modifier
                .width(400.dp)
                .wrapContentHeight(),
            shape = RoundedCornerShape(16.dp),
            color = backgroundColor,
            border = BorderStroke(width = 1.dp, color = textColor.copy(alpha = 0.1f))
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Header with close button
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Settings",
                        fontFamily = GetFontPoppinsSemiBold(),
                        fontSize = 18.sp,
                        color = textColor
                    )

                    Image(
                        modifier = Modifier
                            .size(24.dp)
                            .clickable {
                                onCloseDialog()
                            },
                        painter = painterResource(Res.drawable.ic_close),
                        colorFilter = ColorFilter.tint(color = textColor.copy(alpha = 0.5f)),
                        contentDescription = "Close"
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Focus time setting
                TimeSettingItem(
                    title = "Focus Time (minutes)",
                    value = settings.focusTime.value / 60,
                    textColor = textColor,
                    onValueChange = { newValue ->
                        settings.focusTime.value = newValue * 60
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Short break time setting
                TimeSettingItem(
                    title = "Short Break (minutes)",
                    value = settings.shortBreakTime.value / 60,
                    textColor = textColor,
                    onValueChange = { newValue ->
                        settings.shortBreakTime.value = newValue * 60
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Long break time setting
                TimeSettingItem(
                    title = "Long Break (minutes)",
                    value = settings.longBreakTime.value / 60,
                    textColor = textColor,
                    onValueChange = { newValue ->
                        settings.longBreakTime.value = newValue * 60
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Cycles before long break setting
                TimeSettingItem(
                    title = "Cycles Before Long Break",
                    value = settings.cyclesBeforeLongBreak.value,
                    textColor = textColor,
                    onValueChange = { newValue ->
                        settings.cyclesBeforeLongBreak.value = newValue
                    },
                    minValue = 1,
                    maxValue = 10
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Save button
                Button(
                    onClick = {
                        onSaveSettings(settings)
                        onCloseDialog()
                    },
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = textColor
                    )
                ) {
                    Text(
                        text = "Save Settings",
                        fontFamily = GetFontPoppinsMedium(),
                        color = backgroundColor
                    )
                }
            }
        }
    }
}
