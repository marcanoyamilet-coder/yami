package com.example.ui.screens

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.viewmodel.UnimarViewModel

val FreestyleScriptFont = FontFamily(
    Font(R.font.freestyle_script)
)

@Composable
fun MainStoryScreen(
    onNavigateToEnrollment: () -> Unit,
    onNavigateToAdmin: () -> Unit,
    viewModel: UnimarViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var isLiked by remember { mutableStateOf(false) }
    var messageText by remember { mutableStateOf("") }

    var showAdminPasswordDialog by remember { mutableStateOf(false) }
    var adminPasswordInput by remember { mutableStateOf("") }
    var isPasswordError by remember { mutableStateOf(false) }

    // Resolve texts based on Admin toggles (Classic Mockup vs. Custom Admin)
    val instagramUser = if (viewModel.useDynamicLayout) viewModel.instagramUser else "unimaroficial"
    val instagramSub = if (viewModel.useDynamicLayout) viewModel.instagramSub else "Publicidad"
    val slogan1 = if (viewModel.useDynamicLayout) viewModel.sloganLine1 else "Tu futuro"
    val slogan2 = if (viewModel.useDynamicLayout) viewModel.sloganLine2 else "COMIENZA AQUÍ"
    val buttonText = if (viewModel.useDynamicLayout) viewModel.buttonText else "¡INSCRÍBETE AHORA!"
    val webLink = if (viewModel.useDynamicLayout) viewModel.webLink else "unimar.edu.ve"

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Fullbleed Colonial Campus Background Image
        Image(
            painter = painterResource(id = R.drawable.unimar_custom_background),
            contentDescription = "Fondo UNIMAR",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )


        // Main layout containing the top (bright) and bottom (dark blue wave) sections
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            
            // TOP SECTION: Header, Branding & Slogans
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .statusBarsPadding()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.Top
            ) {
                
                // 1. Instagram Story Header Section (Light theme style)
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Segmented progress bar at the top (Instagram Story Style)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp),
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(3.dp)
                                .clip(RoundedCornerShape(2.dp))
                                .background(Color(0x0F, 0x2C, 0x59)) // Royal Navy active segment
                        )
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(3.dp)
                                .clip(RoundedCornerShape(2.dp))
                                .background(Color(0x0F, 0x2C, 0x59).copy(alpha = 0.15f)) // inactive segment
                        )
                    }

                    // Profile Header: unimaroficial + Publicidad
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // UNIMAR Rounded Logo Profile Pic with Navy outline
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape)
                                    .border(1.5.dp, Color(0x0F, 0x2C, 0x59), CircleShape)
                                    .background(Color.White),
                                contentAlignment = Alignment.Center
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.unimar_logo_clean),
                                    contentDescription = "Logo UNIMAR",
                                    modifier = Modifier.size(32.dp)
                                )
                            }

                            Spacer(modifier = Modifier.width(10.dp))

                            Column {
                                Text(
                                    text = instagramUser,
                                    color = Color(0x0F, 0x2C, 0x59),
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = instagramSub,
                                    color = Color(0x0F, 0x2C, 0x59).copy(alpha = 0.7f),
                                    fontSize = 11.sp
                                )
                            }
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            // Secret Backdoor: Clicking options opens Admin Panel after password check
                            Icon(
                                imageVector = Icons.Default.MoreVert,
                                contentDescription = "Opciones",
                                tint = Color(0x0F, 0x2C, 0x59),
                                modifier = Modifier.clickable {
                                    adminPasswordInput = ""
                                    isPasswordError = false
                                    showAdminPasswordDialog = true
                                }
                            )
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Cerrar",
                                tint = Color(0x0F, 0x2C, 0x59),
                                modifier = Modifier.clickable {
                                    Toast.makeText(context, "¡Inscríbete para formar parte de la Universidad de Margarita!", Toast.LENGTH_SHORT).show()
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // 2. Center Branding & Slogans
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    
                    // Official UNIMAR Logo (without surrounding circle container)
                    Image(
                        painter = painterResource(id = R.drawable.unimar_logo_clean),
                        contentDescription = "Logo UNIMAR",
                        modifier = Modifier
                            .size(110.dp)
                            .padding(bottom = 8.dp),
                        contentScale = ContentScale.Fit
                    )

                    // Brand names
                    Text(
                        text = "UNIMAR",
                        color = Color(0x0F, 0x2C, 0x59),
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 2.sp
                    )
                    
                    Text(
                        text = "Universidad de Margarita",
                        color = Color(0x0F, 0x2C, 0x59),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.5.sp
                    )

                    Text(
                        text = "Alma Mater del Caribe",
                        color = Color(0xFF, 0x6F, 0x00), // Vibrant orange color accent
                        fontSize = 13.sp,
                        fontStyle = FontStyle.Italic,
                        fontWeight = FontWeight.SemiBold,
                        letterSpacing = 1.sp,
                        modifier = Modifier.padding(top = 2.dp)
                    )

                    // Wavy orange accent line drawn dynamically
                    Box(
                        modifier = Modifier
                            .padding(vertical = 12.dp)
                            .width(60.dp)
                            .height(8.dp)
                            .drawBehind {
                                val path = androidx.compose.ui.graphics.Path().apply {
                                    moveTo(0f, size.height / 2)
                                    cubicTo(
                                        size.width / 4, 0f,
                                        size.width / 4, size.height,
                                        size.width / 2, size.height / 2
                                    )
                                    cubicTo(
                                        3 * size.width / 4, 0f,
                                        3 * size.width / 4, size.height,
                                        size.width, size.height / 2
                                    )
                                }
                                drawPath(
                                    path = path,
                                    color = Color(0xFF, 0x6F, 0x00),
                                    style = androidx.compose.ui.graphics.drawscope.Stroke(width = 3.dp.toPx())
                                )
                            }
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    // Cursive "Tu futuro" Slogan Line
                    Text(
                        text = slogan1,
                        color = Color(0x0F, 0x2C, 0x59),
                        fontSize = 32.sp,
                        fontFamily = FontFamily.Serif,
                        fontStyle = FontStyle.Italic,
                        textAlign = TextAlign.Center
                    )

                    // Bold Uppercase Slogan Line: "COMIENZA AQUÍ" with AQUÍ below COMIENZA
                    Text(
                        text = slogan2.replace("COMIENZA ", "COMIENZA\n")
                                      .replace("comienza ", "comienza\n")
                                      .replace("Comienza ", "Comienza\n"),
                        color = Color(0x0F, 0x2C, 0x59),
                        fontSize = 38.sp,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 1.sp,
                        textAlign = TextAlign.Center,
                        lineHeight = 42.sp,
                        modifier = Modifier.padding(top = 4.dp)
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // Three Pillars (Formación, Comunidad, Compromiso)
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White.copy(alpha = 0.85f)
                        ),
                        border = BorderStroke(1.dp, Color(0x0F, 0x2C, 0x59).copy(alpha = 0.12f)),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 4.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 12.dp, horizontal = 6.dp),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Pillar 1: Formación Integral
                            Column(
                                modifier = Modifier.weight(1f),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Icon(
                                    imageVector = Icons.Default.MenuBook,
                                    contentDescription = "Formación",
                                    tint = Color(0x0F, 0x2C, 0x59),
                                    modifier = Modifier.size(24.dp)
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "FORMACIÓN\nINTEGRAL",
                                    color = Color(0x0F, 0x2C, 0x59),
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center,
                                    lineHeight = 11.sp
                                )
                            }

                            // Thin orange vertical divider
                            Box(
                                modifier = Modifier
                                    .width(1.dp)
                                    .height(35.dp)
                                    .background(Color(0xFF, 0x6F, 0x00).copy(alpha = 0.5f))
                            )

                            // Pillar 2: Comunidad Activa
                            Column(
                                modifier = Modifier.weight(1f),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Groups,
                                    contentDescription = "Comunidad",
                                    tint = Color(0x0F, 0x2C, 0x59),
                                    modifier = Modifier.size(24.dp)
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "COMUNIDAD\nACTIVA",
                                    color = Color(0x0F, 0x2C, 0x59),
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center,
                                    lineHeight = 11.sp
                                )
                            }

                            // Thin orange vertical divider
                            Box(
                                modifier = Modifier
                                    .width(1.dp)
                                    .height(35.dp)
                                    .background(Color(0xFF, 0x6F, 0x00).copy(alpha = 0.5f))
                            )

                            // Pillar 3: Compromiso Social
                            Column(
                                modifier = Modifier.weight(1f),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Anchor,
                                    contentDescription = "Compromiso",
                                    tint = Color(0x0F, 0x2C, 0x59),
                                    modifier = Modifier.size(24.dp)
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "COMPROMISO\nSOCIAL",
                                    color = Color(0x0F, 0x2C, 0x59),
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center,
                                    lineHeight = 11.sp
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.weight(1f))
            }

            // BOTTOM WAVE CONTAINER: Organic fluid wave with buttons and links (High-fidelity mockup design)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(110.dp)
                    .drawBehind {
                        val path = androidx.compose.ui.graphics.Path().apply {
                            moveTo(0f, 15.dp.toPx())
                            // Create a highly visible organic wave with a deeper valley and clear crest
                            cubicTo(
                                size.width * 0.32f, 60.dp.toPx(),   // Deeper, beautiful low valley
                                size.width * 0.7f, -12.dp.toPx(),  // Elegant rising crest
                                size.width, 12.dp.toPx()           // Clean finish on the right edge
                            )
                            lineTo(size.width, size.height)
                            lineTo(0f, size.height)
                            close()
                        }
                        drawPath(
                            path = path,
                            color = Color(0x0F, 0x2C, 0x59) // UNIMAR Navy
                        )
                    }
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .navigationBarsPadding()
                        .padding(horizontal = 16.dp)
                        .padding(top = 14.dp, bottom = 4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    
                    // Capsule-Shaped Primary Orange Action Button
                    Button(
                        onClick = onNavigateToEnrollment,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF, 0x6F, 0x00) // Wavy Orange
                        ),
                        shape = RoundedCornerShape(24.dp),
                        contentPadding = PaddingValues(horizontal = 24.dp, vertical = 12.dp),
                        modifier = Modifier
                            .fillMaxWidth(0.85f)
                            .testTag("inscribete_button")
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = buttonText,
                                color = Color.White,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Black,
                                letterSpacing = 1.sp
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Icon(
                                imageVector = Icons.Default.ArrowForward,
                                contentDescription = "Inscríbete ahora",
                                tint = Color.White,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }

                    // Website link with Globe Icon
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.clickable {
                            Toast.makeText(context, "Abriendo $webLink...", Toast.LENGTH_SHORT).show()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Language,
                            contentDescription = "Enlace Web",
                            tint = Color.White.copy(alpha = 0.85f),
                            modifier = Modifier.size(15.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = webLink,
                            color = Color.White.copy(alpha = 0.95f),
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.5.sp
                        )
                    }

                    // Simulated Instagram interaction bar (Input text box + Heart + Share icon)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        // "Enviar mensaje" Input Field Simulated Container
                        OutlinedTextField(
                            value = messageText,
                            onValueChange = { messageText = it },
                            placeholder = {
                                Text(
                                    text = "Enviar mensaje",
                                    color = Color.White.copy(alpha = 0.65f),
                                    fontSize = 13.sp
                                )
                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color.White.copy(alpha = 0.8f),
                                unfocusedBorderColor = Color.White.copy(alpha = 0.5f),
                                focusedContainerColor = Color.White.copy(alpha = 0.12f),
                                unfocusedContainerColor = Color.White.copy(alpha = 0.08f),
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White
                            ),
                            shape = RoundedCornerShape(25.dp),
                            modifier = Modifier
                                .weight(1f)
                                .height(46.dp)
                                .testTag("enviar_mensaje_input"),
                            singleLine = true,
                            textStyle = LocalTextStyle.current.copy(fontSize = 13.sp),
                            trailingIcon = {
                                if (messageText.isNotBlank()) {
                                    IconButton(onClick = {
                                        Toast.makeText(context, "Mensaje enviado a UNIMAR: $messageText", Toast.LENGTH_SHORT).show()
                                        messageText = ""
                                    }) {
                                        Icon(
                                            imageVector = Icons.Default.Send,
                                            contentDescription = "Enviar",
                                            tint = Color.White,
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }
                                }
                            }
                        )

                        // Heart/Like Interaction
                        IconButton(
                            onClick = {
                                isLiked = !isLiked
                                if (isLiked) {
                                    Toast.makeText(context, "¡Te gusta UNIMAR!", Toast.LENGTH_SHORT).show()
                                }
                            },
                            modifier = Modifier.size(44.dp)
                        ) {
                            Icon(
                                imageVector = if (isLiked) Icons.Default.Favorite else Icons.Outlined.FavoriteBorder,
                                contentDescription = "Me gusta",
                                tint = if (isLiked) Color.Red else Color.White,
                                modifier = Modifier.size(24.dp)
                            )
                        }

                        // Share / Direct Message Interaction
                        IconButton(
                            onClick = {
                                Toast.makeText(context, "¡Comparte la oportunidad de estudiar en UNIMAR!", Toast.LENGTH_SHORT).show()
                            },
                            modifier = Modifier.size(44.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Send,
                                contentDescription = "Compartir",
                                tint = Color.White,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                }
            }
        }

        if (showAdminPasswordDialog) {
            AlertDialog(
                onDismissRequest = { showAdminPasswordDialog = false },
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = "Acceso Protegido",
                            tint = Color(0x0F, 0x2C, 0x59)
                        )
                        Text(
                            text = "Acceso de Administrador",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = Color(0x0F, 0x2C, 0x59)
                        )
                    }
                },
                text = {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Por favor, introduce la contraseña de seguridad para acceder al Panel de Administración.",
                            fontSize = 14.sp,
                            color = Color.DarkGray
                        )
                        OutlinedTextField(
                            value = adminPasswordInput,
                            onValueChange = {
                                adminPasswordInput = it
                                isPasswordError = false
                            },
                            label = { Text("Contraseña") },
                            placeholder = { Text("Introduce la contraseña") },
                            singleLine = true,
                            isError = isPasswordError,
                            visualTransformation = androidx.compose.ui.text.input.PasswordVisualTransformation(),
                            keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(
                                keyboardType = androidx.compose.ui.text.input.KeyboardType.Password
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )
                        if (isPasswordError) {
                            Text(
                                text = "Contraseña incorrecta. Inténtalo de nuevo.",
                                color = Color.Red,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            if (adminPasswordInput == viewModel.adminPassword) {
                                showAdminPasswordDialog = false
                                onNavigateToAdmin()
                            } else {
                                isPasswordError = true
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0x0F, 0x2C, 0x59)
                        )
                    ) {
                        Text("Ingresar")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = { showAdminPasswordDialog = false }
                    ) {
                        Text("Cancelar", color = Color.Gray)
                    }
                },
                shape = RoundedCornerShape(16.dp),
                containerColor = Color.White
            )
        }
    }
}
