package pl.cieszk.findyourevent.screens.event

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun EventScreen(
    noteId: String,
    popUpScreen: () -> Unit,
    restartApp: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: EventViewModel = hiltViewModel()
) {

}