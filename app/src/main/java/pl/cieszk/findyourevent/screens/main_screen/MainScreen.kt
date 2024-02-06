package pl.cieszk.findyourevent.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import pl.cieszk.findyourevent.screens.main_screen.MainScreenViewModel

@Composable
fun MainScreen(
    restartApp: (String) -> Unit,
    openScreen: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MainScreenViewModel = hiltViewModel()
) {

}