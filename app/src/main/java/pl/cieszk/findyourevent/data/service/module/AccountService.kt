package pl.cieszk.findyourevent.data.service.module

import kotlinx.coroutines.flow.Flow
import com.google.firebase.firestore.auth.User

interface AccountService {
    val currentUser: Flow<User?>
    val currentUserId: String
    fun hasUser(): Boolean
    suspend fun signIn(email: String, password: String)
    suspend fun signUp(email: String, password: String, username: String, city: String, country: String)
    suspend fun signOut()
    suspend fun deleteAccount()
}