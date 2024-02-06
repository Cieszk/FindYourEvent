package pl.cieszk.findyourevent

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import pl.cieszk.findyourevent.screens.MainScreen
import pl.cieszk.findyourevent.screens.event.EventScreen
import pl.cieszk.findyourevent.screens.sign_up.SignUpScreen
import pl.cieszk.findyourevent.screens.sing_in.SignInScreen
import pl.cieszk.findyourevent.screens.splash.SplashScreen
import pl.cieszk.findyourevent.ui.theme.FindYourEventTheme

@Composable
fun EventApp() {
    FindYourEventTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            val appState = rememberAppState()

            Scaffold { innerPaddingModifier ->
                NavHost(
                    navController = appState.navController,
                    startDestination = SPLASH_SCREEN,
                    modifier = Modifier.padding(innerPaddingModifier)
                ) {
                    eventGraph(appState)
                }

            }
        }
    }
}

@Composable
fun rememberAppState(navController: NavHostController = rememberNavController()) =
    remember(navController) {
        EventAppState(navController)
    }

fun NavGraphBuilder.eventGraph(appState: EventAppState) {
    composable(MAIN_APP_SCREEN) {
        MainScreen(
            restartApp = { route -> appState.clearAndNavigate(route) },
            openScreen = { route -> appState.navigate(route) }
        )
    }

    composable(
        route = "$EVENT_SCREEN$EVENT_ID_ARG",
        arguments = listOf(navArgument(EVENT_ID) { defaultValue = EVENT_DEFAULT_ID })
    ) {
        EventScreen(
            noteId = it.arguments?.getString(EVENT_ID) ?: EVENT_DEFAULT_ID,
            popUpScreen = { appState.popUp() },
            restartApp = { route -> appState.clearAndNavigate(route) }
        )
    }

    composable(SIGN_IN_SCREEN) {
        SignInScreen(openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) })
    }

    composable(SIGN_UP_SCREEN) {
        SignUpScreen(openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) })
    }

    composable(SPLASH_SCREEN) {
        SplashScreen(openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) })
    }
}