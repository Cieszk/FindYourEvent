package pl.cieszk.findyourevent.screens.main_screen

import dagger.hilt.android.lifecycle.HiltViewModel
import pl.cieszk.findyourevent.EVENT_DEFAULT_ID
import pl.cieszk.findyourevent.EVENT_ID
import pl.cieszk.findyourevent.EVENT_SCREEN
import pl.cieszk.findyourevent.EVENT_SCREEN_ADD
import pl.cieszk.findyourevent.SPLASH_SCREEN
import pl.cieszk.findyourevent.data.model.Event
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
    fun onSignOutClick() {
        launchCatching {
            accountService.signOut()
        }
    }
    fun onAddClick(openScreen: (String) -> Unit) {
        openScreen(EVENT_SCREEN_ADD)
    }

    fun onEventClick(openScreen: (String) -> Unit, event: Event) {
        openScreen("$EVENT_SCREEN?$EVENT_ID=${event.id}")
    }
}