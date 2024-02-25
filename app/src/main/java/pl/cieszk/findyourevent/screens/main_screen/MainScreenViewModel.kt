package pl.cieszk.findyourevent.screens.main_screen

import android.app.Activity
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import pl.cieszk.findyourevent.EVENT_ID
import pl.cieszk.findyourevent.EVENT_SCREEN
import pl.cieszk.findyourevent.EVENT_SCREEN_ADD
import pl.cieszk.findyourevent.common.filterEventsByLocation
import pl.cieszk.findyourevent.data.model.Event
import pl.cieszk.findyourevent.data.service.module.AccountService
import pl.cieszk.findyourevent.data.service.module.LocationService
import pl.cieszk.findyourevent.data.service.module.StorageService
import pl.cieszk.findyourevent.screens.EventAppViewModel
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val accountService: AccountService,
    private val storageService: StorageService,
    private val locationService: LocationService
) : EventAppViewModel() {
    private val _eventsFlow = MutableStateFlow<List<Event>>(emptyList())

    init {
        loadEventsForCurrentUserLocation()
    }

    private fun loadEventsForCurrentUserLocation(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            storageService.events.collect { allEvents ->
                val filteredEvents = filterEventsByLocation(allEvents, latitude, longitude)
                _eventsFlow.value = filteredEvents
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