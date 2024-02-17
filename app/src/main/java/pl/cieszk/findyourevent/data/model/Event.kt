package pl.cieszk.findyourevent.data.model

import com.google.firebase.firestore.DocumentId
import java.time.LocalDate
import java.util.Date

data class Event (
    @DocumentId val id: String = "",
    var userId: String = "",
    val eventDate: Date,
    val creationDate: Date = Date(),
    val title: String,
    val description: String,
    val city: String,
    val country: String
) {
    constructor() : this(
        "",
        "",
        Date(),
        Date(),
        "",
        "",
        "",
        ""
    )
}

fun Event.getTitle(): String {
    return this.title
}