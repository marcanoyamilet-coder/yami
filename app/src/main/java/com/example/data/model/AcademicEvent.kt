package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "academic_events")
data class AcademicEvent(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val message: String,
    val category: String, // e.g., "Académico", "Inscripciones", "Cultura", "Deportes"
    val timestamp: Long = System.currentTimeMillis(),
    val isRead: Boolean = false
)
