package pl.cieszk.findyourevent.data.repositories.impl

import android.util.Log
import com.firebase.ui.auth.data.model.User
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeoutOrNull
import pl.cieszk.findyourevent.common.Result
import pl.cieszk.findyourevent.common.COLLECTION_PATH_NAME
import pl.cieszk.findyourevent.data.database.IoDispatcher
import pl.cieszk.findyourevent.common.PLEASE_CHECK_INTERNET_CONNECTION
import pl.cieszk.findyourevent.data.repositories.module.UserRepository
import java.lang.IllegalStateException
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val eventDatabase: FirebaseFirestore,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : UserRepository {

    override suspend fun addUser(
        username: String,
        city: String,
        country: String,
        email: String
    ) : Result<Unit> {
        return try {
            withContext(ioDispatcher) {
                val user = hashMapOf(
                    "username" to username,
                    "city" to city,
                    "country" to country,
                    "email" to email
                )

                val addUserTimeout = withTimeoutOrNull(10000L) {
                    eventDatabase.collection(COLLECTION_PATH_NAME)
                        .add(user)
                }

                if (addUserTimeout == null) {
                    Log.d("ERROR: ", PLEASE_CHECK_INTERNET_CONNECTION)

                    Result.Failure(IllegalStateException(PLEASE_CHECK_INTERNET_CONNECTION))
                }

                Result.Success(Unit)
            }
        } catch (exception: Exception) {
            Log.d("ERROR: ", "$exception")
            Result.Failure(exception = exception)
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