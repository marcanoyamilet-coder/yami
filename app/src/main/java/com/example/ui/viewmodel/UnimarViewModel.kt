package com.example.ui.viewmodel

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.app.NotificationCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.MainActivity
import com.example.R
import com.example.data.mock.UnimarData
import com.example.data.model.AcademicEvent
import com.example.data.model.Enrollment
import com.example.data.repository.UnimarRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull

class UnimarViewModel(
    private val repository: UnimarRepository,
    context: Context
) : ViewModel() {

    private val sharedPrefs = context.applicationContext.getSharedPreferences("unimar_settings", Context.MODE_PRIVATE)

    // Admin Customization States
    var useDynamicLayout by mutableStateOf(sharedPrefs.getBoolean("use_dynamic_layout", false))
        private set
    var sloganLine1 by mutableStateOf(sharedPrefs.getString("slogan_line_1", "Tu futuro") ?: "Tu futuro")
        private set
    var sloganLine2 by mutableStateOf(sharedPrefs.getString("slogan_line_2", "COMIENZA AQUÍ") ?: "COMIENZA AQUÍ")
        private set
    var buttonText by mutableStateOf(sharedPrefs.getString("button_text", "¡INSCRÍBETE AHORA!") ?: "¡INSCRÍBETE AHORA!")
        private set
    var instagramUser by mutableStateOf(sharedPrefs.getString("instagram_user", "unimaroficial") ?: "unimaroficial")
        private set
    var instagramSub by mutableStateOf(sharedPrefs.getString("instagram_sub", "Publicidad") ?: "Publicidad")
        private set
    var webLink by mutableStateOf(sharedPrefs.getString("web_link", "unimar.edu.ve") ?: "unimar.edu.ve")
        private set

    // Target Email where the survey data will be sent
    var targetEmail by mutableStateOf(sharedPrefs.getString("target_email", "yami2hiv@gmail.com") ?: "yami2hiv@gmail.com")
        private set

    // Webhook URL for direct background submissions (e.g. Google Apps Script)
    var webhookUrl by mutableStateOf(sharedPrefs.getString("webhook_url", "https://script.google.com/macros/s/AKfycbwwWcvj2SQ9YrTIjl2A3FxsdBjB90_uwHbuCHJ0EhkCegU_3kFf0axn1k-aJ3VuS4jP/exec") ?: "https://script.google.com/macros/s/AKfycbwwWcvj2SQ9YrTIjl2A3FxsdBjB90_uwHbuCHJ0EhkCegU_3kFf0axn1k-aJ3VuS4jP/exec")
        private set

    // Toggle whether to send automatically in background or launch the mail client
    var sendAutomatically by mutableStateOf(sharedPrefs.getBoolean("send_automatically", true))
        private set

    // Admin password to prevent unauthorized access
    var adminPassword by mutableStateOf(sharedPrefs.getString("admin_password", "2128Pj$$") ?: "2128Pj$$")
        private set

    // Form states
    var fullName by mutableStateOf("")
        private set
    var idCard by mutableStateOf("")
        private set
    var email by mutableStateOf("")
        private set
    var phone by mutableStateOf("")
        private set
    var selectedCareerId by mutableStateOf("sistemas")
        private set
    var schoolOfOrigin by mutableStateOf("")
        private set
    var comments by mutableStateOf("")
        private set

    // Status states
    var isSubmitting by mutableStateOf(false)
        private set
    var showSuccessDialog by mutableStateOf(false)
        private set

    // Observable states from local database
    val enrollments: StateFlow<List<Enrollment>> = repository.allEnrollments
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val academicEvents: StateFlow<List<AcademicEvent>> = repository.allEvents
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    init {
        // Pre-populate with default academic events if the database is empty
        viewModelScope.launch {
            repository.allEvents.collect { list ->
                if (list.isEmpty()) {
                    val initialEvents = listOf(
                        AcademicEvent(
                            title = "¡Inscripciones Abiertas!",
                            message = "Ya se encuentra abierto el proceso de preinscripción para el curso introductorio 2026-II.",
                            category = "Inscripciones"
                        ),
                        AcademicEvent(
                            title = "Feria de Orientación Vocacional",
                            message = "Acompáñanos este viernes en el Aula Magna para conocer los planes de estudio y hablar con los decanos.",
                            category = "Académico"
                        ),
                        AcademicEvent(
                            title = "Inicio del Semestre 2026-I",
                            message = "Te damos la bienvenida a las aulas virtuales y presenciales de tu alma mater el Caribe.",
                            category = "Académico"
                        )
                    )
                    initialEvents.forEach { repository.insertEvent(it) }
                }
            }
        }
    }

    // Customization Mutators (Admin Panel)
    fun updateUseDynamicLayout(value: Boolean) {
        useDynamicLayout = value
        sharedPrefs.edit().putBoolean("use_dynamic_layout", value).apply()
    }

    fun updateSloganLine1(value: String) {
        sloganLine1 = value
        sharedPrefs.edit().putString("slogan_line_1", value).apply()
    }

    fun updateSloganLine2(value: String) {
        sloganLine2 = value
        sharedPrefs.edit().putString("slogan_line_2", value).apply()
    }

    fun updateButtonText(value: String) {
        buttonText = value
        sharedPrefs.edit().putString("button_text", value).apply()
    }

    fun updateInstagramUser(value: String) {
        instagramUser = value
        sharedPrefs.edit().putString("instagram_user", value).apply()
    }

    fun updateInstagramSub(value: String) {
        instagramSub = value
        sharedPrefs.edit().putString("instagram_sub", value).apply()
    }

    fun updateWebLink(value: String) {
        webLink = value
        sharedPrefs.edit().putString("web_link", value).apply()
    }

    fun updateTargetEmail(value: String) {
        targetEmail = value
        sharedPrefs.edit().putString("target_email", value).apply()
    }

    fun updateAdminPassword(value: String) {
        adminPassword = value
        sharedPrefs.edit().putString("admin_password", value).apply()
    }

    fun updateWebhookUrl(value: String) {
        webhookUrl = value
        sharedPrefs.edit().putString("webhook_url", value).apply()
    }

    fun updateSendAutomatically(value: Boolean) {
        sendAutomatically = value
        sharedPrefs.edit().putBoolean("send_automatically", value).apply()
    }

    // Enrollment Management
    fun deleteEnrollment(enrollment: Enrollment) {
        viewModelScope.launch {
            repository.deleteEnrollment(enrollment)
        }
    }

    // Form mutators

    fun updateFullName(value: String) {
        fullName = value
    }

    fun updateIdCard(value: String) {
        idCard = value
    }

    fun updateEmail(value: String) {
        email = value
    }

    fun updatePhone(value: String) {
        phone = value
    }

    fun updateSelectedCareer(value: String) {
        selectedCareerId = value
    }

    fun updateSchoolOfOrigin(value: String) {
        schoolOfOrigin = value
    }

    fun updateComments(value: String) {
        comments = value
    }

    fun clearForm() {
        fullName = ""
        idCard = ""
        email = ""
        phone = ""
        selectedCareerId = "sistemas"
        schoolOfOrigin = ""
        comments = ""
    }

    // Submit form and trigger email intent or background direct send
    fun submitEnrollment(context: Context, onComplete: () -> Unit) {
        if (fullName.isBlank() || idCard.isBlank() || email.isBlank() || phone.isBlank()) {
            return
        }

        isSubmitting = true
        viewModelScope.launch {
            val careerName = UnimarData.careers.find { it.id == selectedCareerId }?.name ?: selectedCareerId
            
            // Create Enrollment Entity
            val enrollment = Enrollment(
                fullName = fullName,
                idCard = idCard,
                email = email,
                phone = phone,
                career = careerName,
                schoolOfOrigin = schoolOfOrigin,
                comments = comments,
                sentStatus = true
            )

            // Save to Local SQLite Room Database
            repository.insertEnrollment(enrollment)

            // Prepare Email Content
            val emailSubject = "Pre-Inscripción UNIMAR - Curso Introductorio ($fullName)"
            val emailBody = """
                Estimada Coordinación de Admisión UNIMAR,
                
                Se ha registrado una solicitud de inscripción para el Curso Introductorio a través de la aplicación móvil. A continuación se detallan los datos recabados:
                
                --- DATOS DEL POSTULANTE ---
                Nombre y Apellido: $fullName
                Cédula de Identidad: $idCard
                Correo Electrónico: $email
                Teléfono: $phone
                Carrera de Interés: $careerName
                Liceo de Procedencia: ${schoolOfOrigin.ifBlank { "No especificado" }}
                
                --- COMENTARIOS ADICIONALES ---
                ${comments.ifBlank { "Ninguno" }}
                
                ----------------------------
                Enviado desde el dispositivo móvil del estudiante a través de la aplicación oficial UNIMAR.
            """.trimIndent()

            // Option A: Direct background HTTP request if automatic sending is enabled and webhook is configured
            if (sendAutomatically && webhookUrl.isNotBlank()) {
                var success = false
                var errorMsg = ""
                try {
                    withContext(Dispatchers.IO) {
                        val json = """
                            {
                                "fullName": ${escapeJson(fullName)},
                                "idCard": ${escapeJson(idCard)},
                                "email": ${escapeJson(email)},
                                "phone": ${escapeJson(phone)},
                                "career": ${escapeJson(careerName)},
                                "schoolOfOrigin": ${escapeJson(schoolOfOrigin)},
                                "comments": ${escapeJson(comments)},
                                "targetEmail": ${escapeJson(targetEmail)}
                            }
                        """.trimIndent()

                        val client = OkHttpClient()
                        val mediaType = "application/json; charset=utf-8".toMediaTypeOrNull()
                        val body = RequestBody.create(mediaType, json)
                        val request = Request.Builder()
                            .url(webhookUrl)
                            .post(body)
                            .build()

                        client.newCall(request).execute().use { response ->
                            if (response.isSuccessful) {
                                success = true
                            } else {
                                errorMsg = "Código: ${response.code}"
                            }
                        }
                    }
                } catch (e: Exception) {
                    errorMsg = e.localizedMessage ?: "Error de conexión"
                }

                if (success) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "¡Pre-Inscripción enviada directamente con éxito!", Toast.LENGTH_LONG).show()
                        isSubmitting = false
                        showSuccessDialog = true
                        clearForm()
                        onComplete()
                    }
                    return@launch
                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "Envío automático falló ($errorMsg). Abriendo correo como respaldo...", Toast.LENGTH_LONG).show()
                    }
                }
            }

            // Fallback (Option B): Open Mail Client via ACTION_SEND
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:${Uri.encode(targetEmail)}") // Encodes recipient address directly in the URI for guaranteed pre-fill
                putExtra(Intent.EXTRA_SUBJECT, emailSubject)
                putExtra(Intent.EXTRA_TEXT, emailBody)
            }

            try {
                val chooser = Intent.createChooser(intent, "Enviar formulario por correo...").apply {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
                context.startActivity(chooser)
            } catch (e: Exception) {
                // Fallback in case no mail clients are installed
                val shareIntent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_EMAIL, arrayOf(targetEmail))
                    putExtra(Intent.EXTRA_SUBJECT, emailSubject)
                    putExtra(Intent.EXTRA_TEXT, emailBody)
                }
                val shareChooser = Intent.createChooser(shareIntent, "Compartir datos por...").apply {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
                context.startActivity(shareChooser)
            }

            isSubmitting = false
            showSuccessDialog = true
            clearForm()
            onComplete()
        }
    }

    private fun escapeJson(value: String): String {
        return "\"" + value.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "\\r") + "\""
    }

    // Dismiss Dialog
    fun dismissSuccessDialog() {
        showSuccessDialog = false
    }

    // Trigger local push notification (Simulating a real push message sent by the server)
    fun simulateNotification(context: Context, title: String, message: String, category: String) {
        viewModelScope.launch {
            // Save to database
            val event = AcademicEvent(
                title = title,
                message = message,
                category = category
            )
            repository.insertEvent(event)

            // Trigger system push notification
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val channelId = "academic_channel"

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(
                    channelId,
                    "Eventos Académicos UNIMAR",
                    NotificationManager.IMPORTANCE_HIGH
                ).apply {
                    description = "Canal para notificaciones de eventos académicos y preinscripciones."
                }
                notificationManager.createNotificationChannel(channel)
            }

            val intent = Intent(context, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            }
            val pendingIntent = PendingIntent.getActivity(
                context, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            // Dynamic Icon selection
            val iconRes = android.R.drawable.ic_dialog_info

            val builder = NotificationCompat.Builder(context, channelId)
                .setSmallIcon(iconRes)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)

            notificationManager.notify(System.currentTimeMillis().toInt(), builder.build())
        }
    }

    fun clearNotifications() {
        viewModelScope.launch {
            repository.clearAllEvents()
        }
    }

    // Factory Class for ViewModel
    class Factory(
        private val repository: UnimarRepository,
        private val context: Context
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(UnimarViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return UnimarViewModel(repository, context) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
