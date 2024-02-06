package pl.cieszk.findyourevent.screens.sing_in

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import pl.cieszk.findyourevent.MAIN_APP_SCREEN
import pl.cieszk.findyourevent.SIGN_IN_SCREEN
import pl.cieszk.findyourevent.SIGN_UP_SCREEN
import pl.cieszk.findyourevent.model.service.module.AccountService
import pl.cieszk.findyourevent.screens.EventAppViewModel
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val accountService: AccountService
) : EventAppViewModel() {
    val email = MutableStateFlow("")
    val password = MutableStateFlow("")

    fun updateEmail(newEmail: String) {
        email.value = newEmail
    }

    fun updatePassword(newPassword: String) {
        password.value = newPassword
    }

    fun onSignInClick(openAndPopUp: (String, String) -> Unit) {
        launchCatching {
            accountService.signIn(email.value, password.value)
            openAndPopUp(MAIN_APP_SCREEN, SIGN_IN_SCREEN)
        }
    }

    fun onSignUpClick(openAndPopUp: (String, String) -> Unit) {
        openAndPopUp(SIGN_UP_SCREEN, SIGN_IN_SCREEN)
    }
}