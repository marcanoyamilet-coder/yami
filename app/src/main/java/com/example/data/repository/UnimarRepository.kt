package com.example.data.repository

import com.example.data.local.AcademicEventDao
import com.example.data.local.EnrollmentDao
import com.example.data.model.AcademicEvent
import com.example.data.model.Enrollment
import kotlinx.coroutines.flow.Flow

class UnimarRepository(
    private val enrollmentDao: EnrollmentDao,
    private val academicEventDao: AcademicEventDao
) {
    val allEnrollments: Flow<List<Enrollment>> = enrollmentDao.getAllEnrollments()
    val allEvents: Flow<List<AcademicEvent>> = academicEventDao.getAllEvents()

    suspend fun insertEnrollment(enrollment: Enrollment): Long {
        return enrollmentDao.insertEnrollment(enrollment)
    }

    suspend fun deleteEnrollment(enrollment: Enrollment) {
        enrollmentDao.deleteEnrollment(enrollment)
    }

    suspend fun insertEvent(event: AcademicEvent): Long {
        return academicEventDao.insertEvent(event)
    }

    suspend fun markEventAsRead(id: Int) {
        academicEventDao.markAsRead(id)
    }

    suspend fun clearAllEvents() {
        academicEventDao.deleteAllEvents()
    }
}
