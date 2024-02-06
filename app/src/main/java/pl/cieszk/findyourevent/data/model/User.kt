package pl.cieszk.findyourevent.data.model

data class User(
    val id: String = "",
    val username: String,
    val email: String,
    val city: String,
    val country: String
)