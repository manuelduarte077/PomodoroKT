package dev.donmanuel.app.pomodoro.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import dev.donmanuel.app.pomodoro.data.Pomodoro
import dev.donmanuel.app.pomodoro.presentation.ui.theme.GetFontPoppinsMedium
import dev.donmanuel.app.pomodoro.presentation.ui.theme.GetFontPoppinsSemiBold
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import pomodoro.composeapp.generated.resources.Res
import pomodoro.composeapp.generated.resources.ic_minus
import pomodoro.composeapp.generated.resources.ic_plus

@Composable
fun FocusTypeSelector(
    onSelectFocusType: (String, Int, Int, Int, Int) -> Unit
) {
    val backgroundColor = Pomodoro.FOCUS.backgroundColor
    val textColor = Pomodoro.FOCUS.textColor
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Selecciona un tipo de enfoque",
            fontFamily = GetFontPoppinsSemiBold(),
            fontSize = 24.sp,
            color = textColor,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 32.dp)
        )
        
        // Pomodoro estándar
        FocusTypeCard(
            title = "Pomodoro",
            focusTime = 25,
            shortBreakTime = 5,
            longBreakTime = 15,
            cycles = 4,
            backgroundColor = backgroundColor,
            textColor = textColor,
            onClick = { onSelectFocusType("Pomodoro", 25 * 60, 5 * 60, 15 * 60, 4) }
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Estudio
        FocusTypeCard(
            title = "Estudio",
            focusTime = 50,
            shortBreakTime = 10,
            longBreakTime = 30,
            cycles = 3,
            backgroundColor = backgroundColor,
            textColor = textColor,
            onClick = { onSelectFocusType("Estudio", 50 * 60, 10 * 60, 30 * 60, 3) }
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Trabajo
        FocusTypeCard(
            title = "Trabajo",
            focusTime = 45,
            shortBreakTime = 5,
            longBreakTime = 15,
            cycles = 4,
            backgroundColor = backgroundColor,
            textColor = textColor,
            onClick = { onSelectFocusType("Trabajo", 45 * 60, 5 * 60, 15 * 60, 4) }
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Sesión corta
        FocusTypeCard(
            title = "Sesión Corta",
            focusTime = 15,
            shortBreakTime = 3,
            longBreakTime = 10,
            cycles = 5,
            backgroundColor = backgroundColor,
            textColor = textColor,
            onClick = { onSelectFocusType("Sesión Corta", 15 * 60, 3 * 60, 10 * 60, 5) }
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Personalizado
        Button(
            onClick = { 
                // Mostrar diálogo para configuración personalizada
                onSelectFocusType("Personalizado", 25 * 60, 5 * 60, 15 * 60, 4)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = textColor
            ),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(top = 8.dp)
        ) {
            Text(
                text = "Personalizado",
                fontFamily = GetFontPoppinsMedium(),
                fontSize = 16.sp,
                color = backgroundColor,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
    }
}

@Composable
fun FocusTypeCard(
    title: String,
    focusTime: Int,
    shortBreakTime: Int,
    longBreakTime: Int,
    cycles: Int,
    backgroundColor: Color,
    textColor: Color,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        color = backgroundColor,
        border = BorderStroke(1.dp, textColor.copy(alpha = 0.2f))
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = title,
                fontFamily = GetFontPoppinsSemiBold(),
                fontSize = 18.sp,
                color = textColor,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TimeInfoItem(label = "Focus", value = "$focusTime min", textColor = textColor)
                TimeInfoItem(label = "Short Break", value = "$shortBreakTime min", textColor = textColor)
                TimeInfoItem(label = "Long Break", value = "$longBreakTime min", textColor = textColor)
                TimeInfoItem(label = "Ciclos", value = "$cycles", textColor = textColor)
            }
        }
    }
}

@Composable
fun TimeInfoItem(
    label: String,
    value: String,
    textColor: Color
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = label,
            fontFamily = GetFontPoppinsMedium(),
            fontSize = 12.sp,
            color = textColor.copy(alpha = 0.6f)
        )
        
        Text(
            text = value,
            fontFamily = GetFontPoppinsSemiBold(),
            fontSize = 14.sp,
            color = textColor
        )
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun CustomFocusDialog(
    onDismiss: () -> Unit,
    onConfirm: (String, Int, Int, Int, Int) -> Unit
) {
    var focusTime by remember { mutableStateOf(25) }
    var shortBreakTime by remember { mutableStateOf(5) }
    var longBreakTime by remember { mutableStateOf(15) }
    var cycles by remember { mutableStateOf(4) }
    
    val backgroundColor = Pomodoro.FOCUS.backgroundColor
    val textColor = Pomodoro.FOCUS.textColor
    
    Dialog(onDismissRequest = { onDismiss() }) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = backgroundColor
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Configuración Personalizada",
                    fontFamily = GetFontPoppinsSemiBold(),
                    fontSize = 20.sp,
                    color = textColor,
                    modifier = Modifier.padding(bottom = 24.dp)
                )
                
                TimeSettingItem(
                    title = "Tiempo de Focus (min)",
                    value = focusTime,
                    textColor = textColor,
                    onValueChange = { focusTime = it }
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                TimeSettingItem(
                    title = "Tiempo de Short Break (min)",
                    value = shortBreakTime,
                    textColor = textColor,
                    onValueChange = { shortBreakTime = it }
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                TimeSettingItem(
                    title = "Tiempo de Long Break (min)",
                    value = longBreakTime,
                    textColor = textColor,
                    onValueChange = { longBreakTime = it }
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                TimeSettingItem(
                    title = "Ciclos antes de Long Break",
                    value = cycles,
                    textColor = textColor,
                    onValueChange = { cycles = it },
                    minValue = 1,
                    maxValue = 10
                )
                
                Spacer(modifier = Modifier.height(24.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = { onDismiss() },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = textColor.copy(alpha = 0.5f)
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "Cancelar",
                            fontFamily = GetFontPoppinsMedium(),
                            fontSize = 16.sp,
                            color = backgroundColor,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                    
                    Button(
                        onClick = { 
                            onConfirm("Personalizado", focusTime * 60, shortBreakTime * 60, longBreakTime * 60, cycles)
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = textColor
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "Guardar",
                            fontFamily = GetFontPoppinsMedium(),
                            fontSize = 16.sp,
                            color = backgroundColor,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
            }
        }
    }
}

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
