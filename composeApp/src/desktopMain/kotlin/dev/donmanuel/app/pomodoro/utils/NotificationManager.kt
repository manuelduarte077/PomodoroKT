package dev.donmanuel.app.pomodoro.utils

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import dev.donmanuel.app.pomodoro.presentation.ui.theme.GetFontPoppinsMedium
import dev.donmanuel.app.pomodoro.presentation.ui.theme.GetFontPoppinsSemiBold

/**
 * Clase para manejar las notificaciones del temporizador Pomodoro
 */
class NotificationManager {
    val showFiveMinNotification = mutableStateOf(false)
    val showThreeMinNotification = mutableStateOf(false)
    val showFinishedNotification = mutableStateOf(false)

    var fiveMinMessage = "¡Quedan 5 minutos para terminar!"
    var threeMinMessage = "¡Quedan 3 minutos para terminar!"
    var finishedMessage = "¡Tiempo completado!"

    var currentTitle = "Focus"

    fun checkTimeForNotifications(timeLeftInSeconds: Int, isPomodoroActive: Boolean) {
        if (!isPomodoroActive) return

        when (timeLeftInSeconds) {
            300 -> showFiveMinNotification.value = true
            180 -> showThreeMinNotification.value = true
            0 -> showFinishedNotification.value = true
        }
    }

    // Función para resetear todas las notificaciones
    fun resetNotifications() {
        showFiveMinNotification.value = false
        showThreeMinNotification.value = false
        showFinishedNotification.value = false
    }
}
