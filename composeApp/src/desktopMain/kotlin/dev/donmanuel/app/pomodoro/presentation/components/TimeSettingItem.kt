package dev.donmanuel.app.pomodoro.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.donmanuel.app.pomodoro.presentation.ui.theme.GetFontPoppinsMedium
import dev.donmanuel.app.pomodoro.presentation.ui.theme.GetFontPoppinsSemiBold
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import pomodoro.composeapp.generated.resources.Res
import pomodoro.composeapp.generated.resources.ic_minus
import pomodoro.composeapp.generated.resources.ic_plus

@OptIn(ExperimentalResourceApi::class)
@Composable
fun TimeSettingItem(
    title: String,
    value: Int,
    textColor: Color,
    onValueChange: (Int) -> Unit,
    minValue: Int = 1,
    maxValue: Int = 60
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            fontFamily = GetFontPoppinsMedium(),
            fontSize = 14.sp,
            color = textColor
        )

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = {
                    if (value > minValue) {
                        onValueChange(value - 1)
                    }
                }
            ) {
                Icon(
                    painter = painterResource(Res.drawable.ic_minus),
                    contentDescription = "Menos",
                    tint = textColor
                )
            }

            Text(
                text = "$value",
                fontFamily = GetFontPoppinsSemiBold(),
                fontSize = 16.sp,
                color = textColor,
                modifier = Modifier.padding(horizontal = 8.dp)
            )

            IconButton(
                onClick = {
                    if (value < maxValue) {
                        onValueChange(value + 1)
                    }
                }
            ) {
                Icon(
                    painter = painterResource(Res.drawable.ic_plus),
                    contentDescription = "Más",
                    tint = textColor
                )
            }
        }
    }
}
