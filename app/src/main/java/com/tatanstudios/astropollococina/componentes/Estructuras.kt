package com.tatanstudios.astropollococina.componentes

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tatanstudios.astropollococina.R
@Composable
fun BloqueTextFieldLogin(text: String, onTextChanged: (String) -> Unit, maxLength: Int) {
    // Color común para el texto del placeholder y la línea
    val commonColor = Color.Gray

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White) // Fondo blanco
            .padding(6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        // Ícono al inicio con color gris claro
        Icon(
            imageVector = Icons.Filled.Person,  // Aquí puedes poner el ícono que prefieras
            contentDescription = null,
            modifier = Modifier
                .padding(end = 8.dp) // Espacio entre el ícono y el TextField
                .size(24.dp), // Tamaño del ícono
            tint = commonColor // Usamos el color común para el ícono
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .drawBehind {
                    val strokeWidth = 2.dp.toPx()
                    val y = size.height - strokeWidth / 2
                    drawLine(
                        color = commonColor, // Usamos el mismo color para la línea
                        start = Offset(0f, y),
                        end = Offset(size.width, y),
                        strokeWidth = strokeWidth
                    )
                }
        ) {
            TextField(
                value = text,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                onValueChange = { newText ->
                    if (newText.length <= maxLength) {
                        onTextChanged(newText)
                    }
                },
                textStyle = TextStyle(
                    fontSize = 17.sp, // Tamaño del texto
                    fontWeight = FontWeight.Normal // Negrita
                ),
                placeholder = { Text(text = stringResource(id = R.string.usuario), color = commonColor) }, // Color del placeholder
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White, // Fondo blanco cuando está enfocado
                    unfocusedContainerColor = Color.White, // Fondo blanco cuando no está enfocado
                    disabledContainerColor = Color.White, // Fondo blanco cuando está deshabilitado
                    errorContainerColor = Color.White, // Fondo blanco si hay error
                    focusedIndicatorColor = commonColor, // Color de la línea cuando está enfocado
                    unfocusedIndicatorColor = commonColor  // Color de la línea cuando no está enfocado
                ),
            )
        }
    }
}

@Composable
fun BloqueTextFieldPassword(
    text: String,
    onTextChanged: (String) -> Unit,
    isPasswordVisible: Boolean,
    onPasswordVisibilityChanged: (Boolean) -> Unit,
    maxLength: Int
) {
    // Color común para el texto del placeholder y la línea
    val commonColor = Color.Gray

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White) // Fondo blanco
            .padding(6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        // Ícono al inicio con color gris claro
        Icon(
            imageVector = Icons.Filled.Visibility,  // Puedes cambiarlo por otro ícono si lo prefieres
            contentDescription = null,
            modifier = Modifier
                .padding(end = 8.dp) // Espacio entre el ícono y el TextField
                .size(24.dp), // Tamaño del ícono
            tint = commonColor // Usamos el color común para el ícono
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .drawBehind {
                    val strokeWidth = 2.dp.toPx()
                    val y = size.height - strokeWidth / 2
                    drawLine(
                        color = commonColor, // Usamos el mismo color para la línea
                        start = Offset(0f, y),
                        end = Offset(size.width, y),
                        strokeWidth = strokeWidth
                    )
                }
        ) {
            TextField(
                value = text,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                onValueChange = { newText ->
                    if (newText.length <= maxLength) {  // Limitar la longitud de la contraseña si lo deseas
                        onTextChanged(newText)
                    }
                },
                textStyle = TextStyle(
                    fontSize = 17.sp, // Tamaño del texto
                    fontWeight = FontWeight.Normal // Negrita
                ),
                placeholder = { Text(text = stringResource(id = R.string.contrasena), color = commonColor) }, // Color del placeholder
                singleLine = true,
                visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { onPasswordVisibilityChanged(!isPasswordVisible) }) {
                        Icon(
                            imageVector = if (isPasswordVisible) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                            contentDescription = null,
                            tint = commonColor
                        )
                    }
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White, // Fondo blanco cuando está enfocado
                    unfocusedContainerColor = Color.White, // Fondo blanco cuando no está enfocado
                    disabledContainerColor = Color.White, // Fondo blanco cuando está deshabilitado
                    errorContainerColor = Color.White, // Fondo blanco si hay error
                    focusedIndicatorColor = commonColor, // Color de la línea cuando está enfocado
                    unfocusedIndicatorColor = commonColor  // Color de la línea cuando no está enfocado
                ),
            )
        }
    }
}