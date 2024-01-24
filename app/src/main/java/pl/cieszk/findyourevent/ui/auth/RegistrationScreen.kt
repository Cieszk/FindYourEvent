package pl.cieszk.findyourevent.ui.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import pl.cieszk.findyourevent.utils.registerUser
import pl.cieszk.findyourevent.utils.signIn

@Composable
fun RegistrationScreen(navController: NavController) {
    // State variables for user input
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var repeatPassword by remember { mutableStateOf("") }
    var country by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var dateOfBirth by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") }
        )
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") }
        )
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation()
        )
        OutlinedTextField(
            value = repeatPassword,
            onValueChange = { repeatPassword = it },
            label = { Text("Repeat Password") },
            visualTransformation = PasswordVisualTransformation()
        )
        OutlinedTextField(
            value = country,
            onValueChange = { country = it },
            label = { Text("Country") }
        )
        OutlinedTextField(
            value = city,
            onValueChange = { city = it },
            label = { Text("City") }
        )
        OutlinedTextField(
            value = dateOfBirth,
            onValueChange = { dateOfBirth = it },
            label = { Text("Date of Birth") }
        )
        // Login button
        Button(onClick = {
            registerUser(email= email, password=password, confirmPassword=repeatPassword)
        }) {
            Text("Login")
        }
    }
}