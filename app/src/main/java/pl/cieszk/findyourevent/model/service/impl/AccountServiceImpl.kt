package pl.cieszk.findyourevent.model.service.impl

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import pl.cieszk.findyourevent.model.service.User
import pl.cieszk.findyourevent.model.service.module.AccountService
import javax.inject.Inject

class AccountServiceImpl @Inject constructor() : AccountService {
    override val currentUser: Flow<User?>
        get() = callbackFlow {
            //TODO
        }
    override val currentUserId: String
        get() = Firebase.auth.currentUser?.uid.orEmpty()

    override fun hasUser(): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun signIn(email: String, password: String) {
        TODO("Not yet implemented")
    }

    override suspend fun signUp(email: String, password: String) {
        TODO("Not yet implemented")
    }

    override suspend fun signOut() {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAccount() {
        TODO("Not yet implemented")
    }
}