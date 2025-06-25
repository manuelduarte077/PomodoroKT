package dev.donmanuel.app.pomodoro.presentation.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.Color
import dev.donmanuel.app.pomodoro.presentation.ui.theme.GetFontPoppinsMedium
import dev.donmanuel.app.pomodoro.presentation.ui.theme.GetFontPoppinsSemiBold

/**
 * Componente para mostrar una notificación como un AlertDialog
 */
@Composable
fun NotificationAlert(
    showNotification: MutableState<Boolean>,
    title: String,
    message: String,
    backgroundColor: Color,
    textColor: Color,
    onDismiss: () -> Unit
) {
    if (showNotification.value) {
        AlertDialog(
            onDismissRequest = {
                showNotification.value = false
                onDismiss()
            },
            title = {
                Text(
                    text = title,
                    fontFamily = GetFontPoppinsSemiBold(),
                    color = textColor
                )
            },
            text = {
                Text(
                    text = message,
                    fontFamily = GetFontPoppinsMedium(),
                    color = textColor
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        showNotification.value = false
                        onDismiss()
                    }
                ) {
                    Text(
                        text = "Aceptar",
                        fontFamily = GetFontPoppinsMedium(),
                        color = backgroundColor
                    )
                }
            },
            containerColor = backgroundColor,
            titleContentColor = textColor,
            textContentColor = textColor
        )
    }
}