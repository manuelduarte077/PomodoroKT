package dev.donmanuel.app.pomodoro

import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import org.jetbrains.compose.resources.painterResource
import pomodoro.composeapp.generated.resources.Res
import pomodoro.composeapp.generated.resources.ic_launcher

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Pomodoro",
        resizable = true,
        state = rememberWindowState(width = 800.dp, height = 600.dp),
        icon = painterResource(Res.drawable.ic_launcher),
    ) {
        PomodoroApp()
    }
}