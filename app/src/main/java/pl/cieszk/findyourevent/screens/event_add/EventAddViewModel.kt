package pl.cieszk.findyourevent.screens.event_add

import dagger.hilt.android.lifecycle.HiltViewModel
import pl.cieszk.findyourevent.EVENT_SCREEN
import pl.cieszk.findyourevent.MAIN_APP_SCREEN
import pl.cieszk.findyourevent.data.model.Event
import pl.cieszk.findyourevent.data.service.module.AccountService
import pl.cieszk.findyourevent.data.service.module.StorageService
import pl.cieszk.findyourevent.screens.EventAppViewModel
import javax.inject.Inject

@HiltViewModel
class EventAddViewModel @Inject constructor(
    private val storageService: StorageService,
    private val accountService: AccountService
) : EventAppViewModel() {
    fun createEvent(openScreen: (String) -> Unit, event: Event) {
        launchCatching {
            val userId = accountService.currentUserId
            if (userId.isNotEmpty()) {
                event.userId = userId
            }
            storageService.createEvent(event)
        }
        openScreen(MAIN_APP_SCREEN)
    }
}