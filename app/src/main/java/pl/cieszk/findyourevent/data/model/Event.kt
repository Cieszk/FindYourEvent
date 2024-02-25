package pl.cieszk.findyourevent.data.model

import com.google.firebase.firestore.DocumentId
import java.time.LocalDate
import java.util.Date

data class Event (
    @DocumentId val id: String = "",
    var userId: String = "",
    val eventDate: Date,
    val creationDate: Date = Date(),
    var title: String,
    var description: String,
    var city: String,
    var country: String
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