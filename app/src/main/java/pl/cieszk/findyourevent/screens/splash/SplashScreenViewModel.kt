package pl.cieszk.findyourevent.screens.splash

import dagger.hilt.android.lifecycle.HiltViewModel
import pl.cieszk.findyourevent.MAIN_APP_SCREEN
import pl.cieszk.findyourevent.SIGN_IN_SCREEN
import pl.cieszk.findyourevent.SPLASH_SCREEN
import pl.cieszk.findyourevent.data.service.module.AccountService
import pl.cieszk.findyourevent.screens.EventAppViewModel
import javax.inject.Inject
@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    private val accountService: AccountService
) : EventAppViewModel() {
    fun onAppStart(openAndPopUp: (String, String) -> Unit) {
        if (accountService.hasUser()) openAndPopUp(MAIN_APP_SCREEN, SPLASH_SCREEN)
        else openAndPopUp(SIGN_IN_SCREEN, SPLASH_SCREEN)
    }
}