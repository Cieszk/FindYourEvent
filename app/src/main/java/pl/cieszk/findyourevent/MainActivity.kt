package pl.cieszk.findyourevent

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import pl.cieszk.findyourevent.ui.auth.LoginScreen
import pl.cieszk.findyourevent.ui.auth.RegistrationScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val auth: FirebaseAuth = FirebaseAuth.getInstance()

            NavHost(navController = navController, startDestination = "login") {
                composable("login") { LoginScreen(navController, auth) }
                composable("registration") { RegistrationScreen(navController) }
            }
        }
    }
}
