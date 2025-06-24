package dev.donmanuel.app.pomodoro.data

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

/**
 * Representa un preset de configuración para diferentes tipos de enfoque
 */
data class FocusPreset(
    val name: String,
    val focusTime: Int, // en segundos
    val shortBreakTime: Int, // en segundos
    val longBreakTime: Int, // en segundos
    val cyclesBeforeLongBreak: Int = 4
)

/**
 * Presets predefinidos para diferentes tipos de actividades
 */
object FocusPresets {
    val POMODORO = FocusPreset(
        name = "Pomodoro",
        focusTime = 1500, // 25 minutos
        shortBreakTime = 300, // 5 minutos
        longBreakTime = 900, // 15 minutos
        cyclesBeforeLongBreak = 4
    )
    
    val STUDY = FocusPreset(
        name = "Estudio",
        focusTime = 3000, // 50 minutos
        shortBreakTime = 600, // 10 minutos
        longBreakTime = 1800, // 30 minutos
        cyclesBeforeLongBreak = 3
    )
    
    val WORK = FocusPreset(
        name = "Trabajo",
        focusTime = 2700, // 45 minutos
        shortBreakTime = 300, // 5 minutos
        longBreakTime = 900, // 15 minutos
        cyclesBeforeLongBreak = 4
    )
    
    val SHORT = FocusPreset(
        name = "Corto",
        focusTime = 900, // 15 minutos
        shortBreakTime = 180, // 3 minutos
        longBreakTime = 600, // 10 minutos
        cyclesBeforeLongBreak = 5
    )
    
    val CUSTOM = FocusPreset(
        name = "Personalizado",
        focusTime = 1500, // 25 minutos (valor inicial)
        shortBreakTime = 300, // 5 minutos (valor inicial)
        longBreakTime = 900, // 15 minutos (valor inicial)
        cyclesBeforeLongBreak = 4
    )
    
    val ALL_PRESETS = listOf(POMODORO, STUDY, WORK, SHORT, CUSTOM)
}

/**
 * Clase para representar presets de enfoque con tiempos personalizados
 */
class CustomFocusPreset(
    val name: String,
    val focusTimeState: MutableState<Int>,
    val shortBreakTimeState: MutableState<Int>,
    val longBreakTimeState: MutableState<Int>,
    val cyclesBeforeLongBreakState: MutableState<Int>
) {
    // Creamos una función para obtener el preset actual con los valores actualizados
    fun getPreset(): FocusPreset {
        return FocusPreset(
            name = name,
            focusTime = focusTimeState.value,
            shortBreakTime = shortBreakTimeState.value,
            longBreakTime = longBreakTimeState.value,
            cyclesBeforeLongBreak = cyclesBeforeLongBreakState.value
        )
    }

    fun updateFocusTime(time: Int) {
        focusTimeState.value = time
    }

    fun updateShortBreakTime(time: Int) {
        shortBreakTimeState.value = time
    }

    fun updateLongBreakTime(time: Int) {
        longBreakTimeState.value = time
    }

    fun updateCyclesBeforeLongBreak(cycles: Int) {
        cyclesBeforeLongBreakState.value = cycles
    }
}
