package pl.cieszk.findyourevent.data.service.impl

import android.provider.ContactsContract
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
        get() =
            auth.currentUser.flatMapLatest { event ->
                Firebase.firestore
                    .collection(EVENT_COLLECTION)
                    .whereEqualTo(USER_ID_FIELD, event?.uid)
                    .dataObjects()
            }

    override suspend fun createNote(event: Event) {
        val noteWithUserId = event.copy(userId = auth.currentUserId)
        Firebase.firestore
            .collection(EVENT_COLLECTION)
            .add(noteWithUserId).await()
    }

    override suspend fun readNote(noteId: String): ContactsContract.CommonDataKinds.Note? {
        return Firebase.firestore
            .collection(EVENT_COLLECTION)
            .document(noteId).get().await().toObject()
    }

    override suspend fun updateNote(event: Event) {
        Firebase.firestore
            .collection(EVENT_COLLECTION)
            .document(note.id).set(note).await()
    }

    override suspend fun deleteNote(noteId: String) {
        Firebase.firestore
            .collection(EVENT_COLLECTION)
            .document(noteId).delete().await()
    }

    companion object {
        private const val USER_ID_FIELD = "userId"
        private const val EVENT_COLLECTION = "events"
    }
}