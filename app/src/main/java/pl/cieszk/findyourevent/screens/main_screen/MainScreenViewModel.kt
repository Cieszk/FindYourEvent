package pl.cieszk.findyourevent.screens.main_screen

import dagger.hilt.android.lifecycle.HiltViewModel
import pl.cieszk.findyourevent.SPLASH_SCREEN
import pl.cieszk.findyourevent.data.service.module.AccountService
import pl.cieszk.findyourevent.data.service.module.StorageService
import pl.cieszk.findyourevent.screens.EventAppViewModel
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val accountService: AccountService,
    storageService: StorageService
) : EventAppViewModel() {
    val events = storageService.events

    fun initialize(restartApp: (String) -> Unit) {
        launchCatching {
            val userId = accountService.currentUserId
            if (userId.isEmpty()) {
                restartApp(SPLASH_SCREEN)
            }
        }
    }
}