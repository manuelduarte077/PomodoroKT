package dev.donmanuel.app.pomodoro.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.donmanuel.app.pomodoro.data.Pomodoro
import dev.donmanuel.app.pomodoro.data.Speed
import org.jetbrains.compose.resources.painterResource
import pomodoro.composeapp.generated.resources.Res
import pomodoro.composeapp.generated.resources.ic_fast_foward
import pomodoro.composeapp.generated.resources.ic_menu
import pomodoro.composeapp.generated.resources.ic_pause
import pomodoro.composeapp.generated.resources.ic_play
import dev.donmanuel.app.pomodoro.presentation.ui.theme.GetFontPoppinsBold
import dev.donmanuel.app.pomodoro.presentation.ui.theme.GetFontPoppinsMedium
import dev.donmanuel.app.pomodoro.presentation.ui.theme.GetFontPoppinsSemiBold

@Composable
fun PomodoroContent(
    pomodoro: Pomodoro,
    isPlayPomodoro: Boolean,
    timerLeft: Int,
    speedTime: Speed,
    completedPomodoros: Int = 0,
    totalPomodoros: Int = 4,
    onPlayPause: (Boolean) -> Unit,
    onSpeedChange: (Speed) -> Unit,
    onDialogToggle: (Boolean) -> Unit
) {
    Surface(
        color = pomodoro.buttonColorSecond,
        shape = RoundedCornerShape(100.dp),
        border = BorderStroke(width = 1.dp, color = pomodoro.textColor),
    ) {
        Row(
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier.size(22.dp),
                painter = painterResource(pomodoro.icon),
                contentDescription = "Icon Pomodoro"
            )

            Text(
                modifier = Modifier.padding(start = 6.dp),
                text = pomodoro.title,
                fontFamily = GetFontPoppinsSemiBold(),
                fontSize = 14.sp,
                color = pomodoro.textColor
            )
        }
    }

    Text(
        text = String.format("%02d\n%02d", timerLeft / 60, timerLeft % 60),
        fontFamily = GetFontPoppinsBold(),
        fontSize = 168.sp,
        textAlign = TextAlign.Center,
        lineHeight = 148.sp,
        color = pomodoro.textColor
    )
    
    // Display completed pomodoros
    if (pomodoro == Pomodoro.FOCUS) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            for (i in 1..totalPomodoros) {
                val color = if (i <= completedPomodoros) {
                    pomodoro.buttonColorPrimary
                } else {
                    pomodoro.buttonColorSecond
                }
                Surface(
                    modifier = Modifier.size(12.dp),
                    shape = CircleShape,
                    color = color,
                    border = BorderStroke(1.dp, pomodoro.textColor.copy(alpha = 0.2f))
                ) {}
                if (i < totalPomodoros) {
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }
        }
    }

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            modifier = Modifier.size(60.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = pomodoro.buttonColorSecond,
            ),
            contentPadding = PaddingValues(0.dp),
            onClick = { onDialogToggle(!isPlayPomodoro) }
        ) {
            Image(
                modifier = Modifier.size(18.dp),
                painter = painterResource(Res.drawable.ic_menu),
                contentDescription = ""
            )
        }

        Button(
            modifier = Modifier.size(width = 120.dp, height = 80.dp)
                .padding(horizontal = 14.dp),
            shape = RoundedCornerShape(18.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = pomodoro.buttonColorPrimary
            ),
            onClick = { onPlayPause(!isPlayPomodoro) }
        ) {
            Image(
                painter = painterResource(
                    if (!isPlayPomodoro)
                        Res.drawable.ic_play
                    else
                        Res.drawable.ic_pause
                ),
                contentDescription = ""
            )
        }

        Button(
            modifier = Modifier.size(60.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = pomodoro.buttonColorSecond
            ),
            contentPadding = PaddingValues(0.dp),
            onClick = {
                onSpeedChange(if (speedTime == Speed.NORMAL) Speed.FAST else Speed.NORMAL)
            }
        ) {
            Image(
                modifier = Modifier.size(18.dp),
                painter = painterResource(Res.drawable.ic_fast_foward),
                contentDescription = ""
            )
        }
    }

    if (speedTime == Speed.FAST) {
        Text(
            modifier = Modifier.padding(top = 16.dp),
            text = "Speed is Fast",
            fontFamily = GetFontPoppinsMedium(),
            color = pomodoro.textColor.copy(alpha = 0.5f),
            fontSize = 12.sp
        )
    }
}