package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.model.AcademicEvent
import kotlinx.coroutines.flow.Flow

@Dao
interface AcademicEventDao {
    @Query("SELECT * FROM academic_events ORDER BY timestamp DESC")
    fun getAllEvents(): Flow<List<AcademicEvent>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvent(event: AcademicEvent): Long

    @Query("UPDATE academic_events SET isRead = 1 WHERE id = :id")
    suspend fun markAsRead(id: Int)

    @Query("DELETE FROM academic_events")
    suspend fun deleteAllEvents()
}
