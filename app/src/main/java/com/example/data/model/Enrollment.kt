package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "enrollments")
data class Enrollment(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val fullName: String,
    val idCard: String,
    val email: String,
    val phone: String,
    val career: String,
    val schoolOfOrigin: String,
    val comments: String,
    val timestamp: Long = System.currentTimeMillis(),
    val sentStatus: Boolean = false
)
