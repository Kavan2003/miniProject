package com.example.mini_oroject.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.mini_oroject.routes.Routes
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest

@Composable
fun Register(navController: NavHostController, auth: FirebaseAuth) {
    val context = LocalContext.current
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var Name by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var confirmpassword by remember { mutableStateOf("") }

    Scaffold(
        bottomBar = {

            BottomAppBar(
                containerColor = Color.Transparent,
                modifier = Modifier

                    .padding(16.dp),


                ) {
                if (!isLoading)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = " have an account?",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(bottom = 0.dp)
                        )
                        TextButton(
                            onClick = { navController.navigate(Routes.Login.rout) },
                            modifier = Modifier


                        ) {
                            Text(
                                text = "Login",
                                style = MaterialTheme.typography.bodyMedium,
                                textDecoration = TextDecoration.Underline,
                                color = MaterialTheme.colorScheme.onPrimary,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                            )

                        }
                    }
            }
        }

    ) { it ->

        Column(

            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Hey There 👋",
                style = MaterialTheme.typography.displayMedium,
                modifier = Modifier.padding(bottom = 0.dp)
            )
            Text(
                text = "Discover the thrill of the auction! Register and make your bidding dreams come true.",
                style = MaterialTheme.typography.labelMedium,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.inverseSurface,
                modifier = Modifier.padding(bottom = 40.dp)
            )
            TextField(
                value = Name,
                onValueChange = { Name = it },
                label = { Text("Name") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    focusedLabelColor = MaterialTheme.colorScheme.inverseSurface,
                    focusedTextColor = MaterialTheme.colorScheme.inverseSurface,
                ),

//            textStyle = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.inverseSurface),
                textStyle = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.fillMaxWidth()
            )
            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    focusedLabelColor = MaterialTheme.colorScheme.inverseSurface,
                    focusedTextColor = MaterialTheme.colorScheme.inverseSurface,
                ),

//            textStyle = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.inverseSurface),
                textStyle = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    focusedLabelColor = MaterialTheme.colorScheme.inverseSurface,
                    focusedTextColor = MaterialTheme.colorScheme.inverseSurface,
                ),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                textStyle = MaterialTheme.typography.bodyLarge,
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(10.dp))

            TextField(
                value = confirmpassword,
                onValueChange = { confirmpassword = it },
                label = { Text("confirm Password") },
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    focusedLabelColor = MaterialTheme.colorScheme.inverseSurface,
                    focusedTextColor = MaterialTheme.colorScheme.inverseSurface,
                ),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                textStyle = MaterialTheme.typography.bodyLarge,
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedButton(
                onClick = {

                    if (password == confirmpassword) {
                        navController.navigate(Routes.Home.rout)

                        isLoading = true
                        auth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener() { task ->
                                isLoading = false
                                if (task.isSuccessful) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d("TAG", "createUserWithEmail:success")
                                    val user = auth.currentUser
                                    Toast.makeText(context, "Registered", Toast.LENGTH_SHORT).show()
                                    user?.updateProfile(
                                        UserProfileChangeRequest.Builder()
                                            .setDisplayName(Name)
                                            .build()

                                    )
                                    navController.navigate(Routes.Home.rout)
                                    Log.d("TAG", "Register:" + user?.email)
//                                updateUI(user)
                                } else {
                                    // If sign in fails, display a message to the user.
                                    val exception = task.exception

                                    if (exception != null) {
                                        Toast.makeText(
                                            context,
                                            "" + exception.localizedMessage,
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }

                                    Log.w("TAG", "createUserWithEmail:failure", exception)
                                }

                            }
                    } else
                        Toast.makeText(
                            context,
                            " Confirm Password & Password Not Matched",
                            Toast.LENGTH_SHORT
                        ).show()
                    navController.navigate(Routes.Home.rout)

                },
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .height(50.dp)
            ) {
                Text(
                    text = "Submit",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimary,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

        }
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f)) // Semi-transparent background
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }

}