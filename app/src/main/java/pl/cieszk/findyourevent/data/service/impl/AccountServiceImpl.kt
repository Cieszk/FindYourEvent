package pl.cieszk.findyourevent.data.service.impl


import android.annotation.SuppressLint
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import com.google.firebase.firestore.auth.User
import pl.cieszk.findyourevent.data.repositories.module.UserRepository
import pl.cieszk.findyourevent.data.service.module.AccountService
import javax.inject.Inject

class AccountServiceImpl @Inject constructor(
    private val userRepository: UserRepository
) : AccountService {
    override val currentUser: Flow<User?>
        @SuppressLint("RestrictedApi")
        get() = callbackFlow {
            val listener = FirebaseAuth.AuthStateListener { auth ->
                this.trySend(auth.currentUser?.let { User(it.uid) })
            }
            Firebase.auth.addAuthStateListener(listener)
            awaitClose { Firebase.auth.removeAuthStateListener(listener) }
        }

    override val currentUserId: String
        get() = Firebase.auth.currentUser?.uid.orEmpty()

    override fun hasUser(): Boolean {
        return Firebase.auth.currentUser != null
    }

    override suspend fun signIn(email: String, password: String) {
        Firebase.auth.signInWithEmailAndPassword(email, password).await()
    }

    override suspend fun signUp(email: String, password: String, username: String, city: String, country: String) {
        val authResult = Firebase.auth.createUserWithEmailAndPassword(email, password).await()
        val firebaseUser = authResult.user
        if (firebaseUser != null) {
            userRepository.addUser(username, city, country, firebaseUser.email ?: email)
        }
    }

    override suspend fun signOut() {
        Firebase.auth.signOut()
    }

    override suspend fun deleteAccount() {
        Firebase.auth.currentUser!!.delete().await()
    }
}