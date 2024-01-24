package pl.cieszk.findyourevent.ui

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth

@Composable
fun LoginScreen(navController: NavController, auth: FirebaseAuth) {
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val context = LocalContext.current

    fun signIn(email: String, password: String, context: Context) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                navController.navigate("home")
            } else {
                Toast.makeText(context, "Wrong credentials", Toast.LENGTH_SHORT).show()
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Email input
        OutlinedTextField(
            value = email.value,
            onValueChange = { email.value = it },
            label = { Text("Email") }
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Password input
        OutlinedTextField(
            value = password.value, // Bind to state
            onValueChange = { password.value = it }, // Update state
            label = { Text("Password") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Login button
        Button(onClick = {
            signIn(
                email = email.value,
                password = password.value,
                context = context
            )
        }) {
            Text("Login")
        }

        val annotatedText = buildAnnotatedString {
            append("Don't have an account? Create it ")
            pushStringAnnotation(tag = "registration", annotation = "registration")
            withStyle(
                style = SpanStyle(
                    color = Color.Blue,
                    textDecoration = TextDecoration.Underline
                )
            ) {
                append("here")
            }
            pop()
        }

        ClickableText(
            text = annotatedText,
            onClick = { offset ->
                annotatedText.getStringAnnotations("registration", start = offset, end = offset)
                    .firstOrNull()?.let {
                        navController.navigate(it.item)
                    }
            }
        )

    }

}

