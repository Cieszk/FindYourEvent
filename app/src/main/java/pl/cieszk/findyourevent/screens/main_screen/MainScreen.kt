package pl.cieszk.findyourevent.screens

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch
import pl.cieszk.findyourevent.screens.main_screen.MainScreenViewModel

@Composable
fun MainScreen(
    openAndPopUp: (String, String) -> Unit,
    restartApp: (String) -> Unit,
    openScreen: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MainScreenViewModel = hiltViewModel()
) {
    var signOutTrigger by remember { mutableStateOf(false) }

    LaunchedEffect(signOutTrigger) {
        if (signOutTrigger) {
            viewModel.onSignOutClick(openAndPopUp)
            signOutTrigger = false
        }
    }

    Button(onClick = { signOutTrigger = true }) {
        Text("Logout")
    }
}