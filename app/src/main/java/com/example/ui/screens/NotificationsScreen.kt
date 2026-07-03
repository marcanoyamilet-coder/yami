package com.example.ui.screens

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.model.AcademicEvent
import com.example.ui.viewmodel.UnimarViewModel
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun NotificationsScreen(
    viewModel: UnimarViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val notifications by viewModel.academicEvents.collectAsStateWithLifecycle()

    var customTitle by remember { mutableStateOf("¡Conferencia de Inteligencia Artificial!") }
    var customMessage by remember { mutableStateOf("Únete este jueves en el auditorio a las 10:00 AM para la charla inaugural.") }
    var customCategory by remember { mutableStateOf("Académico") }

    // Request POST_NOTIFICATIONS permission for Android 13+
    var hasNotificationPermission by remember {
        mutableStateOf(
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            } else {
                true
            }
        )
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        hasNotificationPermission = isGranted
        if (isGranted) {
            Toast.makeText(context, "Permiso de notificaciones concedido", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Las notificaciones locales se mostrarán sólo dentro de la app", Toast.LENGTH_LONG).show()
        }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF, 0xF9, 0xF5))
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 32.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        
        // Header Info Card
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
                        text = "Notificaciones Académicas",
                        color = Color(0xFF, 0xD7, 0x00),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Sistema de Notificaciones Push",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Black
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Mantente al tanto de inscripciones, eventos culturales, deportivos y suspensiones de clases. Puedes simular el envío de una nueva notificación abajo.",
                        color = Color.White.copy(alpha = 0.85f),
                        fontSize = 13.sp,
                        lineHeight = 18.sp
                    )
                }
            }
        }

        // Test push notification simulation box
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
                        text = "Simulador de Notificaciones Push",
                        fontWeight = FontWeight.Black,
                        fontSize = 15.sp,
                        color = Color(0x0F, 0x2C, 0x59)
                    )

                    OutlinedTextField(
                        value = customTitle,
                        onValueChange = { customTitle = it },
                        label = { Text("Título del Evento") },
                        singleLine = true,
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("notification_title_input")
                    )

                    OutlinedTextField(
                        value = customMessage,
                        onValueChange = { customMessage = it },
                        label = { Text("Mensaje o Descripción") },
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(80.dp)
                            .testTag("notification_msg_input"),
                        maxLines = 2
                    )

                    // Category selection Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        listOf("Académico", "Inscripciones", "Eventos").forEach { category ->
                            val isSelected = customCategory == category
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(if (isSelected) Color(0xFF, 0x6F, 0x00) else Color.LightGray.copy(alpha = 0.3f))
                                    .clickable { customCategory = category }
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

                    Spacer(modifier = Modifier.height(4.dp))

                    // Trigger Notification Button
                    Button(
                        onClick = {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && !hasNotificationPermission) {
                                permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                            } else {
                                if (customTitle.isBlank() || customMessage.isBlank()) {
                                    Toast.makeText(context, "Escribe un título y mensaje para la notificación de prueba", Toast.LENGTH_SHORT).show()
                                } else {
                                    viewModel.simulateNotification(context, customTitle, customMessage, customCategory)
                                    Toast.makeText(context, "¡Notificación enviada!", Toast.LENGTH_SHORT).show()
                                }
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF, 0x6F, 0x00)),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .testTag("trigger_notification_button")
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(Icons.Default.NotificationsActive, contentDescription = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("DISPARAR NOTIFICACIÓN LOCAL", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        }
                    }
                }
            }
        }

        // Title of notifications list
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Bandeja de Entrada de Avisos (${notifications.size})",
                    fontWeight = FontWeight.Black,
                    fontSize = 16.sp,
                    color = Color(0x0F, 0x2C, 0x59),
                    modifier = Modifier.padding(top = 8.dp)
                )

                if (notifications.isNotEmpty()) {
                    Text(
                        text = "Limpiar Todo",
                        color = Color.Red,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .clickable { viewModel.clearNotifications() }
                            .padding(8.dp)
                    )
                }
            }
        }

        // List of Stored Notification Events (Inbox style)
        if (notifications.isEmpty()) {
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
                            imageVector = Icons.Default.Inbox,
                            contentDescription = null,
                            tint = Color.LightGray,
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "No tienes avisos guardados",
                            color = Color.Gray,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        } else {
            items(notifications) { event ->
                NotificationEventCard(event = event)
            }
        }
    }
}

@Composable
fun NotificationEventCard(event: AcademicEvent) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.Top
        ) {
            // Category Icon Badge
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(
                        when (event.category) {
                            "Inscripciones" -> Color(0xFF, 0x6F, 0x00).copy(alpha = 0.15f)
                            "Académico" -> Color(0x0F, 0x2C, 0x59).copy(alpha = 0.1f)
                            else -> Color(0xFF, 0xD7, 0x00).copy(alpha = 0.2f)
                        }
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = when (event.category) {
                        "Inscripciones" -> Icons.Default.Assignment
                        "Académico" -> Icons.Default.School
                        else -> Icons.Default.Campaign
                    },
                    contentDescription = null,
                    tint = when (event.category) {
                        "Inscripciones" -> Color(0xFF, 0x6F, 0x00)
                        "Académico" -> Color(0x0F, 0x2C, 0x59)
                        else -> Color(0xFF, 0x8F, 0x00)
                    },
                    modifier = Modifier.size(22.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Text info
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = event.category.uppercase(),
                        color = when (event.category) {
                            "Inscripciones" -> Color(0xFF, 0x6F, 0x00)
                            "Académico" -> Color(0x0F, 0x2C, 0x59)
                            else -> Color(0xFF, 0x8F, 0x00)
                        },
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                    
                    val sdf = SimpleDateFormat("dd/MM HH:mm", Locale.getDefault())
                    Text(
                        text = sdf.format(Date(event.timestamp)),
                        color = Color.Gray,
                        fontSize = 11.sp
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = event.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = Color(0x0F, 0x2C, 0x59)
                )

                Text(
                    text = event.message,
                    fontSize = 13.sp,
                    color = Color.DarkGray,
                    lineHeight = 17.sp,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}
