package com.tatanstudios.astropollococina.componentes

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.tatanstudios.astropollococina.R
import com.tatanstudios.astropollococina.extras.ItemsMenuLateral
import com.tatanstudios.astropollococina.ui.theme.ColorAzul
import es.dmoral.toasty.Toasty

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



@Composable
fun CustomModal1Boton(showDialog: Boolean, message: String, onDismiss: () -> Unit) {
    if (showDialog) {
        Dialog(onDismissRequest = {}) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth() // Ajusta el ancho al 80% de la pantalla
                    .background(Color.White, shape = RoundedCornerShape(16.dp))
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(4.dp), // Agrega padding alrededor del contenido
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = message,
                        fontSize = 18.sp,
                        color = colorResource(R.color.colorNegro),
                        modifier = Modifier.padding(bottom = 16.dp) // Espacio entre el texto y el botón
                    )
                    Button(
                        onClick = onDismiss,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(R.color.colorAzul),
                            contentColor = colorResource(R.color.colorBlanco),
                        ),
                    ) {
                        Text(text = stringResource(id = R.string.aceptar))
                    }
                }
            }
        }
    }
}



@Composable
fun LoadingModal(isLoading: Boolean, titulo:String = "Cargando...") {
    if (isLoading) {
        Dialog(onDismissRequest = { /* Evitar que se cierre el modal */ }) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(160.dp)
                    .background(Color.White, shape = RoundedCornerShape(16.dp))
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator(color = colorResource(R.color.colorAzul))
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = titulo,
                        fontSize = 18.sp,
                        color = colorResource(R.color.colorNegro)
                    )
                }
            }
        }
    }
}

enum class ToastType {
    SUCCESS,
    ERROR,
    INFO,
    WARNING
}

fun CustomToasty(context: Context, message: String, type: ToastType) {
    when (type) {
        ToastType.SUCCESS -> Toasty.success(context, message, Toasty.LENGTH_SHORT, true).show()
        ToastType.ERROR -> Toasty.error(context, message, Toasty.LENGTH_SHORT, true).show()
        ToastType.INFO -> Toasty.info(context, message, Toasty.LENGTH_SHORT, true).show()
        ToastType.WARNING -> Toasty.warning(context, message, Toasty.LENGTH_SHORT, true).show()
    }
}



val MontserratFont = FontFamily(
    Font(R.font.montserratregular, FontWeight.Normal),
    Font(R.font.montserratmedium, FontWeight.Bold)
)

@Composable
fun DrawerHeader() {
    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(
        Color.Red,
        darkIcons = false
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(75.dp)
            .background(Color.Red)
            .statusBarsPadding()
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.logonegrocirculo),
                contentDescription = stringResource(R.string.logotipo),
                modifier = Modifier
                    .size(54.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.titleLarge.copy(
                    color = Color.White,
                    fontFamily = MontserratFont,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                ),
                modifier = Modifier.padding(start = 4.dp)
            )
        }
    }
}


@Composable
fun DrawerBody(
    items: List<ItemsMenuLateral>,
    onItemClick: (ItemsMenuLateral) -> Unit
) {
    Column {
        items.forEach { item ->
            NavigationDrawerItem(
                icon = { Icon(item.icon, contentDescription = null) },
                label = {
                    Text(
                        stringResource(id = item.idString),
                        fontSize = 16.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Medium
                    )
                },
                selected = false,
                onClick = { onItemClick(item) },
                modifier = Modifier.padding(horizontal = 12.dp)
            )
        }
    }
}



@Composable
fun CustomModalCerrarSesion(
    showDialog: Boolean,
    message: String,
    onDismiss: () -> Unit,
    onAccept: () -> Unit
) {
    if (showDialog) {
        Dialog(onDismissRequest = { }) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Color.White,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(16.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = message,
                        fontSize = 18.sp,
                        color = colorResource(R.color.colorNegro),
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Button(
                            onClick = onDismiss,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colorResource(R.color.colorGrisv1),
                                contentColor = colorResource(R.color.colorBlanco),
                            ),
                        ) {
                            Text(stringResource(id = R.string.no), color = Color.White)
                        }

                        Button(
                            onClick = onAccept,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colorResource(R.color.colorAzul),
                                contentColor = colorResource(R.color.colorBlanco)
                            ),
                        ) {
                            Text(stringResource(id = R.string.si), color = Color.White)
                        }
                    }
                }
            }
        }
    }
}





