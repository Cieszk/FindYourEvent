package pl.cieszk.findyourevent.data.repositories.module

import com.firebase.ui.auth.data.model.User
import kotlinx.coroutines.flow.Flow
import pl.cieszk.findyourevent.common.Result

interface UserRepository {
    suspend fun addUser(
        username: String, city: String, country: String,
        email: String
    ): Result<Unit>
    suspend fun getUser(userId: String): Flow<User>
    suspend fun deleteUser(userId: String)
    suspend fun updateUser(user: User) : Flow<User>

}