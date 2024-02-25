package pl.cieszk.findyourevent.data.service.impl

import android.annotation.SuppressLint
import android.util.Log
import com.google.firebase.firestore.dataObjects
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.snapshots
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.tasks.await
import pl.cieszk.findyourevent.data.model.Event
import pl.cieszk.findyourevent.data.service.module.AccountService
import pl.cieszk.findyourevent.data.service.module.StorageService
import javax.inject.Inject

class StorageServiceImpl @Inject constructor(private val auth: AccountService) : StorageService {

    override val events: Flow<List<Event>>
        get() =
            Firebase.firestore
                .collection(EVENT_COLLECTION)
                .snapshots() // Listen for real-time updates or use get() for a single fetch
                .map { snapshot ->
                    snapshot.documents.mapNotNull { document ->
                        try {
                            document.toObject(Event::class.java)?.copy(id = document.id)
                        } catch (e: Exception) {
                            Log.e("Firestore", "Error parsing document", e)
                            null
                        }
                    }
                }

    override suspend fun createEvent(event: Event) {
        val eventWithUserId = event.copy(userId = auth.currentUserId)
        Firebase.firestore.collection(EVENT_COLLECTION).add(eventWithUserId).addOnSuccessListener { documentReference ->
            Log.d("TEST", "DocumentSnapshot written with ID: ${documentReference.id}")
        }
            .addOnFailureListener { e ->
                Log.w("TEST", "Error adding document", e)
            }
    }

    override suspend fun readEvent(eventId: String): Event? {
        return Firebase.firestore
            .collection(EVENT_COLLECTION)
            .document(eventId).get().await().toObject(Event::class.java)
    }

    override suspend fun updateEvent(event: Event) {
        Firebase.firestore
            .collection(EVENT_COLLECTION)
            .document(event.id).set(event).await()
    }

    override suspend fun deleteEvent(eventId: String) {
        Firebase.firestore
            .collection(EVENT_COLLECTION)
            .document(eventId).delete().await()
    }

    companion object {
        private const val USER_ID_FIELD = "userId"
        private const val EVENT_COLLECTION = "events"
    }
}