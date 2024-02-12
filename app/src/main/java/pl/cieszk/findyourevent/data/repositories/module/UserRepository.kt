package pl.cieszk.findyourevent.data.repositories.module

import kotlinx.coroutines.flow.Flow
import pl.cieszk.findyourevent.common.Result
import pl.cieszk.findyourevent.data.model.User

interface UserRepository {
    suspend fun addUser(
        username: String, city: String, country: String,
        email: String
    ): Result<Unit>
    suspend fun getUser(userId: String): Flow<User>
    suspend fun deleteUser(userId: String)
    suspend fun updateUser(user: User) : Flow<User>

}