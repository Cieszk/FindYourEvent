package pl.cieszk.findyourevent.data.service.impl

import android.annotation.SuppressLint
import android.util.Log
import com.google.firebase.firestore.dataObjects
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import pl.cieszk.findyourevent.data.model.Event
import pl.cieszk.findyourevent.data.service.module.AccountService
import pl.cieszk.findyourevent.data.service.module.StorageService
import javax.inject.Inject

class StorageServiceImpl @Inject constructor(private val auth: AccountService) : StorageService {

    @OptIn(ExperimentalCoroutinesApi::class)
    override val events: Flow<List<Event>>
        @SuppressLint("RestrictedApi")
        get() =
            auth.currentUser.flatMapLatest { event ->
                Firebase.firestore
                    .collection(EVENT_COLLECTION)
                    .whereEqualTo(USER_ID_FIELD, event?.uid)
                    .dataObjects()
            }

    override suspend fun createEvent(event: Event) {
        val eventWithUserId = event.copy(userId = auth.currentUserId)
//        val event = Event(
//            title= event.title,
//            description = event.description,
//            creationDate = event.creationDate,
//            eventDate = event.eventDate,
//            userId = eventWithUserId.userId,
//             city = event.city,
//             country = event.country
//        )
        Firebase.firestore.collection(EVENT_COLLECTION).add(eventWithUserId).addOnSuccessListener { documentReference ->
            Log.d("TEST", "DocumentSnapshot written with ID: ${documentReference.id}")
        }
            .addOnFailureListener { e ->
                Log.w("TEST", "Error adding document", e)
            }

//        Firebase.firestore
//            .collection(EVENT_COLLECTION)
//            .add(eventWithUserId).await()
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