package pl.cieszk.findyourevent.data.repositories.impl

import android.util.Log
import pl.cieszk.findyourevent.data.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import pl.cieszk.findyourevent.common.COLLECTION_PATH_NAME
import pl.cieszk.findyourevent.common.Result
import pl.cieszk.findyourevent.data.database.IoDispatcher
import pl.cieszk.findyourevent.data.repositories.module.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val eventDatabase: FirebaseFirestore,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : UserRepository {

    override suspend fun addUser(username: String, city: String, country: String, email: String): Result<Unit> = withContext(ioDispatcher) {
        try {
            val uid = FirebaseAuth.getInstance().currentUser?.uid ?: throw IllegalStateException("User not logged in")
            val user = User(
                id = uid,
                username = username,
                city = city,
                country = country,
                email = email
            )

            Firebase.firestore.collection(USER_COLLECTION).add(user)

            Result.Success(Unit)
        } catch (e: Exception) {
            Log.d("ERROR: ", e.toString())
            Result.Failure(e)
        }
    }

    override suspend fun getUser(userId: String): Flow<User> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteUser(userId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun updateUser(user: User): Flow<User> {
        TODO("Not yet implemented")
    }
    companion object {
        private const val USER_COLLECTION = "users"
    }
}

