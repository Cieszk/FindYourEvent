package pl.cieszk.findyourevent.screens.sign_up

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import pl.cieszk.findyourevent.screens.sing_in.SignInViewModel

@Composable
fun SignUpScreen(
    openAndPopUp: (String, String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SignUpViewModel = hiltViewModel()
) {

}