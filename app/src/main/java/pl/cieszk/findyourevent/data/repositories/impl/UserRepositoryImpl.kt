package pl.cieszk.findyourevent.data.repositories.impl

import android.util.Log
import com.firebase.ui.auth.data.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
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
            val user = pl.cieszk.findyourevent.data.model.User(
                id = uid,
                username = username,
                city = city,
                country = country,
                email = email
            )

            val result = eventDatabase.collection(COLLECTION_PATH_NAME)
                .document(uid)
                .set(user)
                .await()

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
}