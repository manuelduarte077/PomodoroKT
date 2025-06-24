package dev.donmanuel.app.pomodoro.utils

interface Platform {
    val isDesktop: Boolean
}

fun platform(): Platform = object : Platform {
    override val isDesktop: Boolean = true
}
