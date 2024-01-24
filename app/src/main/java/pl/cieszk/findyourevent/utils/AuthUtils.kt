package pl.cieszk.findyourevent.utils

import android.content.Context
import android.widget.Toast
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth

fun isValidEmail(email: String): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

fun doPasswordsMatch(password: String, confirmPassword: String): Boolean {
    return password == confirmPassword
}

fun registerUser(email: String, password: String, confirmPassword: String) {
    if (!isValidEmail(email)) {
        return
    }
    if (!doPasswordsMatch(password, confirmPassword)) {
        return
    }
    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {

            } else {

            }
        }
}

fun signIn(
    email: String,
    password: String,
    context: Context,
    auth: FirebaseAuth,
    navController: NavController
) {
    auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
        if (task.isSuccessful) {
            navController.navigate("home")
        } else {
            Toast.makeText(context, "Wrong credentials", Toast.LENGTH_SHORT).show()
        }
    }
}