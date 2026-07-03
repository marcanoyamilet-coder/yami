package com.example.ui.screens

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.mock.UnimarData
import com.example.ui.viewmodel.UnimarViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EnrollmentScreen(
    viewModel: UnimarViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val enrollments by viewModel.enrollments.collectAsStateWithLifecycle()
    
    var careerExpanded by remember { mutableStateOf(false) }

    val currentSelectedCareerName = UnimarData.careers.find { it.id == viewModel.selectedCareerId }?.name ?: viewModel.selectedCareerId

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF, 0xF9, 0xF5)) // Warm soft paper background
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 32.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        
        // Header Card
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
                        text = "Curso Introductorio 2026",
                        color = Color(0xFF, 0xD7, 0x00), // Gold
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Formulario de Pre-Inscripción",
                        color = Color.White,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Black
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Completa tus datos personales para solicitar plaza. Al presionar enviar, tus datos se guardarán localmente y se abrirá tu gestor de correos listo para enviar la información.",
                        color = Color.White.copy(alpha = 0.85f),
                        fontSize = 13.sp,
                        lineHeight = 18.sp
                    )
                }
            }
        }



        // Main Enrollment Form Input fields
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "Datos del Estudiante",
                        fontWeight = FontWeight.Black,
                        fontSize = 16.sp,
                        color = Color(0x0F, 0x2C, 0x59)
                    )

                    // 1. Full name
                    OutlinedTextField(
                        value = viewModel.fullName,
                        onValueChange = { viewModel.updateFullName(it) },
                        label = { Text("Nombre Completo *") },
                        leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                        singleLine = true,
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("full_name_input")
                    )

                    // 2. ID Card (Cédula de Identidad)
                    OutlinedTextField(
                        value = viewModel.idCard,
                        onValueChange = { viewModel.updateIdCard(it) },
                        label = { Text("Cédula de Identidad *") },
                        placeholder = { Text("V-12345678 o E-12345678") },
                        leadingIcon = { Icon(Icons.Default.Badge, contentDescription = null) },
                        singleLine = true,
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("id_card_input"),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )

                    // 3. Email Address
                    OutlinedTextField(
                        value = viewModel.email,
                        onValueChange = { viewModel.updateEmail(it) },
                        label = { Text("Tu Correo Electrónico *") },
                        leadingIcon = { Icon(Icons.Default.AlternateEmail, contentDescription = null) },
                        singleLine = true,
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("student_email_input"),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                    )

                    // 4. Phone Number
                    OutlinedTextField(
                        value = viewModel.phone,
                        onValueChange = { viewModel.updatePhone(it) },
                        label = { Text("Teléfono de Contacto *") },
                        leadingIcon = { Icon(Icons.Default.Phone, contentDescription = null) },
                        singleLine = true,
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("student_phone_input"),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
                    )

                    // 5. Dropdown Selection for Careers of Interest
                    ExposedDropdownMenuBox(
                        expanded = careerExpanded,
                        onExpandedChange = { careerExpanded = !careerExpanded },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        OutlinedTextField(
                            value = currentSelectedCareerName,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Carrera de Interés *") },
                            leadingIcon = { Icon(Icons.Default.School, contentDescription = null) },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = careerExpanded) },
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor()
                                .testTag("career_dropdown_anchor")
                        )

                        ExposedDropdownMenu(
                            expanded = careerExpanded,
                            onDismissRequest = { careerExpanded = false }
                        ) {
                            UnimarData.careers.forEach { career ->
                                DropdownMenuItem(
                                    text = { Text(career.name) },
                                    onClick = {
                                        viewModel.updateSelectedCareer(career.id)
                                        careerExpanded = false
                                    }
                                )
                            }
                        }
                    }

                    // 6. School of Origin (Liceo de Procedencia)
                    OutlinedTextField(
                        value = viewModel.schoolOfOrigin,
                        onValueChange = { viewModel.updateSchoolOfOrigin(it) },
                        label = { Text("Liceo de Procedencia") },
                        leadingIcon = { Icon(Icons.Default.HomeWork, contentDescription = null) },
                        singleLine = true,
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("school_origin_input")
                    )

                    // 7. Comments
                    OutlinedTextField(
                        value = viewModel.comments,
                        onValueChange = { viewModel.updateComments(it) },
                        label = { Text("Comentarios / Preguntas") },
                        leadingIcon = { Icon(Icons.Default.Comment, contentDescription = null) },
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                            .testTag("comments_input"),
                        maxLines = 3
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Submit Button
                    Button(
                        onClick = {
                            if (viewModel.fullName.isBlank() || viewModel.idCard.isBlank() || viewModel.email.isBlank() || viewModel.phone.isBlank()) {
                                Toast.makeText(context, "Por favor complete todos los campos obligatorios (*)", Toast.LENGTH_LONG).show()
                            } else {
                                viewModel.submitEnrollment(context) {
                                    Toast.makeText(context, "¡Inscripción registrada con éxito!", Toast.LENGTH_SHORT).show()
                                }
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF, 0x6F, 0x00)),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .testTag("submit_enrollment_button"),
                        enabled = !viewModel.isSubmitting
                    ) {
                        if (viewModel.isSubmitting) {
                            CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                        } else {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Icon(Icons.Default.Send, contentDescription = null)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("REGISTRAR Y ENVIAR", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                            }
                        }
                    }
                }
            }
        }

        // Database History Log (Online/Offline Applications logs)
        if (enrollments.isNotEmpty()) {
            item {
                Text(
                    text = "Historial Local de Pre-Inscripciones (${enrollments.size})",
                    fontWeight = FontWeight.Black,
                    fontSize = 16.sp,
                    color = Color(0x0F, 0x2C, 0x59),
                    modifier = Modifier.padding(top = 10.dp)
                )
            }

            items(enrollments) { enrollment ->
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(8.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = enrollment.fullName,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                color = Color(0x0F, 0x2C, 0x59)
                            )
                            val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
                            Text(
                                text = sdf.format(Date(enrollment.timestamp)),
                                fontSize = 11.sp,
                                color = Color.Gray
                            )
                        }
                        
                        Spacer(modifier = Modifier.height(4.dp))
                        
                        Text(
                            text = "CI: ${enrollment.idCard}  •  Tlf: ${enrollment.phone}",
                            fontSize = 13.sp,
                            color = Color.DarkGray
                        )
                        Text(
                            text = "Carrera: ${enrollment.career}",
                            fontSize = 13.sp,
                            color = Color(0xFF, 0x6F, 0x00),
                            fontWeight = FontWeight.Bold
                        )
                        if (enrollment.schoolOfOrigin.isNotBlank()) {
                            Text(
                                text = "Liceo: ${enrollment.schoolOfOrigin}",
                                fontSize = 12.sp,
                                color = Color.Gray
                            )
                        }
                    }
                }
            }
        }
    }

    // Interactive Success Dialog
    if (viewModel.showSuccessDialog) {
        AlertDialog(
            onDismissRequest = { viewModel.dismissSuccessDialog() },
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Éxito",
                        tint = Color(0xFF, 0x6F, 0x00),
                        modifier = Modifier.size(32.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("¡Registro Exitoso!")
                }
            },
            text = {
                Text(
                    text = "Tu solicitud ha sido guardada en la base de datos local del móvil y se ha abierto el cliente de correo. Si aún no enviaste el correo electrónico, recuerda completar el envío en tu aplicación gestora para que llegue al correo receptor: ${viewModel.targetEmail}."
                )
            },
            confirmButton = {
                Button(
                    onClick = { viewModel.dismissSuccessDialog() },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0x0F, 0x2C, 0x59))
                ) {
                    Text("ENTENDIDO")
                }
            }
        )
    }
}
