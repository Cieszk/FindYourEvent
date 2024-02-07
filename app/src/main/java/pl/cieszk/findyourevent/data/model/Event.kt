package pl.cieszk.findyourevent.data.model

import com.google.firebase.firestore.DocumentId
import java.util.Date

data class Event (
    @DocumentId val id: String = "",
    val userId: String = "",
    val eventDate: Date,
    val creationDate: Date,
    val title: String,
    val description: String,
    val city: String,
    val country: String
)