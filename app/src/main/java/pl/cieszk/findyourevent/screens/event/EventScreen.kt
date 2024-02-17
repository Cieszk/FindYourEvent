package pl.cieszk.findyourevent.screens.event

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import pl.cieszk.findyourevent.screens.event_add.EventAddViewModel

@Composable
fun EventScreen(
    eventId: String,
    popUpScreen: () -> Unit,
    restartApp: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
}