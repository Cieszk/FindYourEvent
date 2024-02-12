package pl.cieszk.findyourevent.screens.sign_up

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import pl.cieszk.findyourevent.MAIN_APP_SCREEN
import pl.cieszk.findyourevent.SIGN_UP_SCREEN
import pl.cieszk.findyourevent.data.service.module.AccountService
import pl.cieszk.findyourevent.screens.EventAppViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val accountService: AccountService
) : EventAppViewModel() {
    val email = MutableStateFlow("")
    val password = MutableStateFlow("")
    val confirmPassword = MutableStateFlow("")
    val username = MutableStateFlow("")
    val city = MutableStateFlow("")
    val country = MutableStateFlow("")

    fun updateEmail(newEmail: String) {
        email.value = newEmail
    }

    fun updatePassword(newPassword: String) {
        password.value = newPassword
    }

    fun updateConfirmPassword(newConfirmPassword: String) {
        confirmPassword.value = newConfirmPassword
    }

    fun updateUsername(newUsername: String) {
        username.value = newUsername
    }

    fun updateCity(newCity: String) {
        city.value = newCity
    }

    fun updateCountry(newCountry: String) {
        country.value = newCountry
    }

    fun onSignUpClick(openAndPopUp: (String, String) -> Unit) {
        launchCatching {
            if (password.value != confirmPassword.value) {
                throw Exception("Passwords do not mach")
            }
            accountService.signUp(
                email.value,
                password.value,
                username.value,
                city.value,
                country.value
            )
            openAndPopUp(MAIN_APP_SCREEN, SIGN_UP_SCREEN)
        }
    }
}