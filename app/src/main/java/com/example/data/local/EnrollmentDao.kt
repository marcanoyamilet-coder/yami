package com.example.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.model.Enrollment
import kotlinx.coroutines.flow.Flow

@Dao
interface EnrollmentDao {
    @Query("SELECT * FROM enrollments ORDER BY timestamp DESC")
    fun getAllEnrollments(): Flow<List<Enrollment>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEnrollment(enrollment: Enrollment): Long

    @Delete
    suspend fun deleteEnrollment(enrollment: Enrollment)
}
