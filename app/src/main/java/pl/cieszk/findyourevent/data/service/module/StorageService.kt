package pl.cieszk.findyourevent.data.service.module

import kotlinx.coroutines.flow.Flow
import pl.cieszk.findyourevent.data.model.Event

interface StorageService {
    val events: Flow<List<Event>>
    suspend fun createEvent(event: Event)
    suspend fun readEvent(eventId: String) : Event?
    suspend fun updateEvent(event: Event)
    suspend fun deleteEvent(eventId: String)
}