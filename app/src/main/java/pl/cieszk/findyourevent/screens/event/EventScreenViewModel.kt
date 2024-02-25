package pl.cieszk.findyourevent.screens.event

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import pl.cieszk.findyourevent.EVENT_DEFAULT_ID
import pl.cieszk.findyourevent.SPLASH_SCREEN
import pl.cieszk.findyourevent.data.model.Event
import pl.cieszk.findyourevent.data.service.module.AccountService
import pl.cieszk.findyourevent.data.service.module.StorageService
import pl.cieszk.findyourevent.screens.EventAppViewModel
import javax.inject.Inject

@HiltViewModel
class EventScreenViewModel @Inject constructor(
    private val storageService: StorageService,
    val accountService: AccountService
): EventAppViewModel() {
    val event = MutableStateFlow(Event())

    fun initialize(eventId: String, restartApp: (String) -> Unit){
        launchCatching {
            event.value = storageService.readEvent(eventId) ?: event.value
        }
    }

    private fun observeAuthenticationState(restartApp: (String) -> Unit) {
        launchCatching {
            accountService.currentUser.collect { user ->
                if (user == null) restartApp(SPLASH_SCREEN)
            }
        }
    }

    fun updateEventStringValues(newText: String, field: String) {
        when (field) {
            TITLE -> {
                event.value.title = newText
            }
            DESCRIPTION -> {
                event.value.description = newText
            }
            CITY -> {
                event.value.city = newText
            }
            COUNTRY -> {
                event.value.country = newText
            }
        }
    }

    fun deleteEvent(popUpScreen: () -> Unit) {
        launchCatching {
            if(accountService.currentUserId == event.value.userId) {
                storageService.deleteEvent(event.value.id)
            } else {
                throw Exception("You cannot delete event that is not yours!")
            }

        }
    }

    companion object {
        private val TITLE = "title"
        private val DESCRIPTION = "description"
        private val CITY = "city"
        private val COUNTRY = "country"
    }
}