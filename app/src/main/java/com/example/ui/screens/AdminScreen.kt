package com.example.ui.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.model.Enrollment
import com.example.ui.viewmodel.UnimarViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminScreen(
    viewModel: UnimarViewModel,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val enrollments by viewModel.enrollments.collectAsStateWithLifecycle()

    var tempUser by remember { mutableStateOf(viewModel.instagramUser) }
    var tempSub by remember { mutableStateOf(viewModel.instagramSub) }
    var tempSlogan1 by remember { mutableStateOf(viewModel.sloganLine1) }
    var tempSlogan2 by remember { mutableStateOf(viewModel.sloganLine2) }
    var tempButtonText by remember { mutableStateOf(viewModel.buttonText) }
    var tempWebLink by remember { mutableStateOf(viewModel.webLink) }
    var tempEmail by remember { mutableStateOf(viewModel.targetEmail) }
    var tempAdminPassword by remember { mutableStateOf(viewModel.adminPassword) }
    var tempWebhookUrl by remember { mutableStateOf(viewModel.webhookUrl) }
    var tempSendAutomatically by remember { mutableStateOf(viewModel.sendAutomatically) }

    // State for notification simulation
    var notifTitle by remember { mutableStateOf("¡Atención Estudiantes!") }
    var notifMessage by remember { mutableStateOf("El nuevo calendario de inscripciones presenciales ya está disponible.") }
    var notifCategory by remember { mutableStateOf("Inscripciones") }

    val textFieldColors = OutlinedTextFieldDefaults.colors(
        focusedTextColor = Color.Black,
        unfocusedTextColor = Color.Black,
        focusedContainerColor = Color.White,
        unfocusedContainerColor = Color.White,
        focusedLabelColor = Color(0x0F, 0x2C, 0x59),
        unfocusedLabelColor = Color.Gray,
        focusedBorderColor = Color(0x0F, 0x2C, 0x59),
        unfocusedBorderColor = Color.LightGray
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Panel de Administración",
                        fontWeight = FontWeight.Black,
                        fontSize = 18.sp,
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Volver",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0x0F, 0x2C, 0x59)
                )
            )
        },
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF, 0xF9, 0xF5))
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(top = 16.dp, bottom = 40.dp)
        ) {
            // Summary Card
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color(0x0F, 0x2C, 0x59)),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "Consola de Control UNIMAR",
                            color = Color(0xFF, 0xD7, 0x00),
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Bienvenido, Administrador",
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Black
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Desde este panel puedes controlar el modo de diseño de la portada, personalizar todos los textos, slogans, modificar el correo receptor de datos y ver la lista de postulantes inscritos en tiempo real.",
                            color = Color.White.copy(alpha = 0.85f),
                            fontSize = 13.sp,
                            lineHeight = 18.sp
                        )
                    }
                }
            }

            // SECTION 1: DESIGN MODE SELECTOR
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "Modo Editor Dinámico",
                                    fontWeight = FontWeight.Black,
                                    fontSize = 15.sp,
                                    color = Color(0x0F, 0x2C, 0x59)
                                )
                                Text(
                                    text = "Si se activa, el fondo se adapta para mostrar textos personalizados. Si está apagado, se muestra la imagen exacta del mockup.",
                                    fontSize = 12.sp,
                                    color = Color.Gray,
                                    lineHeight = 16.sp
                                )
                            }
                            Switch(
                                checked = viewModel.useDynamicLayout,
                                onCheckedChange = { viewModel.updateUseDynamicLayout(it) },
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = Color.White,
                                    checkedTrackColor = Color(0xFF, 0x6F, 0x00),
                                    uncheckedThumbColor = Color.LightGray,
                                    uncheckedTrackColor = Color.LightGray.copy(alpha = 0.5f)
                                ),
                                modifier = Modifier.testTag("layout_mode_switch")
                            )
                        }
                    }
                }
            }

            // SECTION 2: PORTADA CUSTOMIZATION FIELDS
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "Personalización de Contenidos",
                            fontWeight = FontWeight.Black,
                            fontSize = 15.sp,
                            color = Color(0x0F, 0x2C, 0x59)
                        )

                        OutlinedTextField(
                            value = tempUser,
                            onValueChange = { tempUser = it },
                            label = { Text("Usuario Instagram") },
                            singleLine = true,
                            colors = textFieldColors,
                            modifier = Modifier.fillMaxWidth()
                        )

                        OutlinedTextField(
                            value = tempSub,
                            onValueChange = { tempSub = it },
                            label = { Text("Etiqueta de Publicación") },
                            singleLine = true,
                            colors = textFieldColors,
                            modifier = Modifier.fillMaxWidth()
                        )

                        OutlinedTextField(
                            value = tempSlogan1,
                            onValueChange = { tempSlogan1 = it },
                            label = { Text("Eslogan Línea 1 (Serif)") },
                            singleLine = true,
                            colors = textFieldColors,
                            modifier = Modifier.fillMaxWidth()
                        )

                        OutlinedTextField(
                            value = tempSlogan2,
                            onValueChange = { tempSlogan2 = it },
                            label = { Text("Eslogan Línea 2 (Negrita)") },
                            singleLine = true,
                            colors = textFieldColors,
                            modifier = Modifier.fillMaxWidth()
                        )

                        OutlinedTextField(
                            value = tempButtonText,
                            onValueChange = { tempButtonText = it },
                            label = { Text("Texto del Botón Naranja") },
                            singleLine = true,
                            colors = textFieldColors,
                            modifier = Modifier.fillMaxWidth()
                        )

                        OutlinedTextField(
                            value = tempWebLink,
                            onValueChange = { tempWebLink = it },
                            label = { Text("Enlace Web Footer") },
                            singleLine = true,
                            colors = textFieldColors,
                            modifier = Modifier.fillMaxWidth()
                        )

                        Button(
                            onClick = {
                                viewModel.updateInstagramUser(tempUser)
                                viewModel.updateInstagramSub(tempSub)
                                viewModel.updateSloganLine1(tempSlogan1)
                                viewModel.updateSloganLine2(tempSlogan2)
                                viewModel.updateButtonText(tempButtonText)
                                viewModel.updateWebLink(tempWebLink)
                                Toast.makeText(context, "¡Cambios de portada guardados!", Toast.LENGTH_SHORT).show()
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0x0F, 0x2C, 0x59)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("GUARDAR PERSONALIZACIÓN DE PORTADA", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            // SECTION 3: EMAIL CONFIGURATION
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "Correo Receptor de Preinscripciones",
                            fontWeight = FontWeight.Black,
                            fontSize = 15.sp,
                            color = Color(0x0F, 0x2C, 0x59)
                        )

                        Text(
                            text = "Las solicitudes que realicen los postulantes a través de la app se enviarán automáticamente en formato formal a este correo.",
                            fontSize = 12.sp,
                            color = Color.Gray,
                            lineHeight = 16.sp
                        )

                        OutlinedTextField(
                            value = tempEmail,
                            onValueChange = { tempEmail = it },
                            label = { Text("Correo del Administrador / Control de Estudios") },
                            singleLine = true,
                            colors = textFieldColors,
                            modifier = Modifier.fillMaxWidth()
                        )

                        Button(
                            onClick = {
                                if (tempEmail.contains("@") && tempEmail.contains(".")) {
                                    viewModel.updateTargetEmail(tempEmail)
                                    Toast.makeText(context, "¡Correo receptor actualizado con éxito!", Toast.LENGTH_SHORT).show()
                                } else {
                                    Toast.makeText(context, "Por favor, introduce un correo válido", Toast.LENGTH_SHORT).show()
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF, 0x6F, 0x00)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("ACTUALIZAR CORREO RECEPTOR", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            // SECTION 3.4: AUTOMATIC BACKGROUND SEND (OPTION A)
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "Opción A: Envío Directo y Automático",
                            fontWeight = FontWeight.Black,
                            fontSize = 15.sp,
                            color = Color(0x0F, 0x2C, 0x59)
                        )

                        Text(
                            text = "Si activas esta opción, los estudiantes enviarán sus formularios directamente en segundo plano sin necesidad de que se les abra su aplicación de Gmail. El servidor de Google recibirá los datos, los guardará en tu Hoja de Cálculo y te enviará un correo.",
                            fontSize = 12.sp,
                            color = Color.Gray,
                            lineHeight = 16.sp
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Activar Envío Directo en Background",
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                color = Color.DarkGray
                            )
                            Switch(
                                checked = tempSendAutomatically,
                                onCheckedChange = { tempSendAutomatically = it }
                            )
                        }

                        OutlinedTextField(
                            value = tempWebhookUrl,
                            onValueChange = { tempWebhookUrl = it },
                            label = { Text("URL de Google Apps Script (Web App)") },
                            placeholder = { Text("https://script.google.com/macros/s/.../exec") },
                            singleLine = true,
                            colors = textFieldColors,
                            modifier = Modifier.fillMaxWidth()
                        )

                        Button(
                            onClick = {
                                viewModel.updateSendAutomatically(tempSendAutomatically)
                                viewModel.updateWebhookUrl(tempWebhookUrl.trim())
                                Toast.makeText(context, "¡Configuración de Envío Automático guardada!", Toast.LENGTH_SHORT).show()
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0x0F, 0x2C, 0x59)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("GUARDAR CONFIGURACIÓN AUTOMÁTICA", fontWeight = FontWeight.Bold)
                        }

                        Spacer(modifier = Modifier.height(4.dp))

                        Divider(color = Color.LightGray.copy(alpha = 0.5f))

                        Text(
                            text = "📋 Código de Google Apps Script para usar en tu Hoja de Cálculo:",
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            color = Color(0x0F, 0x2C, 0x59)
                        )

                        Card(
                            colors = CardDefaults.cardColors(containerColor = Color(0xF5, 0xF5, 0xF5)),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "function doPost(e) {\n" +
                                        "  try {\n" +
                                        "    var data = JSON.parse(e.postData.contents);\n" +
                                        "    var sheet = SpreadsheetApp.getActiveSpreadsheet().getActiveSheet();\n" +
                                        "    sheet.appendRow([\n" +
                                        "      new Date(),\n" +
                                        "      data.fullName,\n" +
                                        "      data.idCard,\n" +
                                        "      data.email,\n" +
                                        "      data.phone,\n" +
                                        "      data.career,\n" +
                                        "      data.schoolOfOrigin,\n" +
                                        "      data.comments\n" +
                                        "    ]);\n" +
                                        "    var subject = \"Pre-Inscripción UNIMAR - \" + data.fullName;\n" +
                                        "    var body = \"Se registró un nuevo estudiante:\\n\\n\" +\n" +
                                        "               \"Nombre: \" + data.fullName + \"\\n\" +\n" +
                                        "               \"Cédula: \" + data.idCard + \"\\n\" +\n" +
                                        "               \"Correo: \" + data.email + \"\\n\" +\n" +
                                        "               \"Teléfono: \" + data.phone + \"\\n\" +\n" +
                                        "               \"Carrera: \" + data.career + \"\\n\" +\n" +
                                        "               \"Procedencia: \" + data.schoolOfOrigin + \"\\n\" +\n" +
                                        "               \"Comentarios: \" + data.comments;\n" +
                                        "    MailApp.sendEmail(data.targetEmail, subject, body);\n" +
                                        "    return ContentService.createTextOutput(JSON.stringify({status:'success'}))\n" +
                                        "                         .setMimeType(ContentService.MimeType.JSON);\n" +
                                        "  } catch(error) {\n" +
                                        "    return ContentService.createTextOutput(JSON.stringify({status:'error', message: error.toString()}))\n" +
                                        "                         .setMimeType(ContentService.MimeType.JSON);\n" +
                                        "  }\n" +
                                        "}",
                                fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace,
                                fontSize = 9.sp,
                                color = Color.DarkGray,
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                    }
                }
            }

            // SECTION 3.5: SECURITY & ADMIN PASSWORD
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "Seguridad de Acceso al Panel",
                            fontWeight = FontWeight.Black,
                            fontSize = 15.sp,
                            color = Color(0x0F, 0x2C, 0x59)
                        )

                        Text(
                            text = "Establece una clave de acceso numérica o de texto para evitar que los estudiantes puedan ingresar al Panel de Administración.",
                            fontSize = 12.sp,
                            color = Color.Gray,
                            lineHeight = 16.sp
                        )

                        OutlinedTextField(
                            value = tempAdminPassword,
                            onValueChange = { tempAdminPassword = it },
                            label = { Text("Contraseña de Administrador") },
                            singleLine = true,
                            colors = textFieldColors,
                            modifier = Modifier.fillMaxWidth()
                        )

                        Button(
                            onClick = {
                                if (tempAdminPassword.isNotBlank()) {
                                    viewModel.updateAdminPassword(tempAdminPassword)
                                    Toast.makeText(context, "¡Contraseña de administrador actualizada con éxito!", Toast.LENGTH_SHORT).show()
                                } else {
                                    Toast.makeText(context, "La contraseña no puede estar vacía", Toast.LENGTH_SHORT).show()
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0x0F, 0x2C, 0x59)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("GUARDAR CONTRASEÑA DE ACCESO", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            // SECTION 4: SIMULATE PUSH NOTIFICATION
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "Disparar Aviso Académico (Push)",
                            fontWeight = FontWeight.Black,
                            fontSize = 15.sp,
                            color = Color(0x0F, 0x2C, 0x59)
                        )

                        OutlinedTextField(
                            value = notifTitle,
                            onValueChange = { notifTitle = it },
                            label = { Text("Título de la Notificación") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )

                        OutlinedTextField(
                            value = notifMessage,
                            onValueChange = { notifMessage = it },
                            label = { Text("Contenido de la Notificación") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(80.dp),
                            maxLines = 3
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            listOf("Académico", "Inscripciones", "Eventos").forEach { category ->
                                val isSelected = notifCategory == category
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(if (isSelected) Color(0xFF, 0x6F, 0x00) else Color.LightGray.copy(alpha = 0.3f))
                                        .clickable { notifCategory = category }
                                        .padding(vertical = 8.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = category,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (isSelected) Color.White else Color.DarkGray
                                    )
                                }
                            }
                        }

                        Button(
                            onClick = {
                                if (notifTitle.isNotBlank() && notifMessage.isNotBlank()) {
                                    viewModel.simulateNotification(context, notifTitle, notifMessage, notifCategory)
                                    Toast.makeText(context, "¡Notificación local enviada a los alumnos!", Toast.LENGTH_SHORT).show()
                                } else {
                                    Toast.makeText(context, "Título y mensaje no pueden estar vacíos", Toast.LENGTH_SHORT).show()
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0x0F, 0x2C, 0x59)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("DISPARAR AVISO PUSH", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            // SECTION 5: REGISTERED STUDENTS (ENROLLMENTS)
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Alumnos Preinscritos (${enrollments.size})",
                        fontWeight = FontWeight.Black,
                        fontSize = 16.sp,
                        color = Color(0x0F, 0x2C, 0x59)
                    )
                }
            }

            if (enrollments.isEmpty()) {
                item {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = Icons.Default.PeopleOutline,
                                contentDescription = null,
                                tint = Color.LightGray,
                                modifier = Modifier.size(48.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Ningún alumno se ha preinscrito aún",
                                color = Color.Gray,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            } else {
                items(enrollments) { student ->
                    StudentAdminCard(
                        student = student,
                        onDelete = { viewModel.deleteEnrollment(student) }
                    )
                }
            }
        }
    }
}

@Composable
fun StudentAdminCard(
    student: Enrollment,
    onDelete: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = student.fullName,
                        fontWeight = FontWeight.Black,
                        fontSize = 16.sp,
                        color = Color(0x0F, 0x2C, 0x59)
                    )
                    Text(
                        text = "C.I. ${student.idCard}",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }

                IconButton(onClick = onDelete) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Eliminar",
                        tint = Color.Red
                    )
                }
            }

            Divider(color = Color.LightGray.copy(alpha = 0.5f))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Carrera de interés",
                        fontWeight = FontWeight.Bold,
                        fontSize = 11.sp,
                        color = Color(0xFF, 0x6F, 0x00)
                    )
                    Text(
                        text = student.career,
                        fontSize = 13.sp,
                        color = Color.DarkGray
                    )
                }

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Liceo de Procedencia",
                        fontWeight = FontWeight.Bold,
                        fontSize = 11.sp,
                        color = Color(0xFF, 0x6F, 0x00)
                    )
                    Text(
                        text = student.schoolOfOrigin.ifBlank { "No especificado" },
                        fontSize = 13.sp,
                        color = Color.DarkGray
                    )
                }
            }

            Spacer(modifier = Modifier.height(2.dp))

            Column {
                Text(
                    text = "Contacto",
                    fontWeight = FontWeight.Bold,
                    fontSize = 11.sp,
                    color = Color(0x0F, 0x2C, 0x59)
                )
                Text(
                    text = "Email: ${student.email}",
                    fontSize = 12.sp,
                    color = Color.DarkGray
                )
                Text(
                    text = "Teléfono: ${student.phone}",
                    fontSize = 12.sp,
                    color = Color.DarkGray
                )
            }

            if (student.comments.isNotBlank()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFF, 0xF9, 0xF5))
                        .padding(8.dp)
                ) {
                    Column {
                        Text(
                            text = "Comentarios:",
                            fontWeight = FontWeight.Bold,
                            fontSize = 10.sp,
                            color = Color.Gray
                        )
                        Text(
                            text = student.comments,
                            fontSize = 12.sp,
                            color = Color.DarkGray,
                            lineHeight = 16.sp
                        )
                    }
                }
            }
        }
    }
}
