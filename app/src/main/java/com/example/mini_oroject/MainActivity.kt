package com.example.mini_oroject

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mini_oroject.routes.Routes
import com.example.mini_oroject.screens.create.CreatePost
import com.example.mini_oroject.screens.home.Home
import com.example.mini_oroject.screens.login_register.Login
import com.example.mini_oroject.screens.login_register.LoginChoose
import com.example.mini_oroject.screens.login_register.Register
import com.example.mini_oroject.screens.profile.Profile
import com.example.mini_oroject.ui.theme.Mini_orojectTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth

    //TODO:
    private var route = Routes.LoginChoose.rout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initialize Firebase Auth

        installSplashScreen().apply {
            setKeepOnScreenCondition {
                val currentUser = auth.currentUser
                route = if (currentUser != null) {
                    Log.d("TAG", "onStart: Login user already")
                    Routes.Home.rout
                } else {
                    Log.d("TAG", "onStart: No user")
                    Routes.LoginChoose.rout
                }
                false
            }
        }

        auth = Firebase.auth
        setContent {

            Mini_orojectTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),


                    ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = route) {
                        composable(Routes.Login.rout) {
                            Login(navController = navController, auth = auth)
                        }
                        composable(Routes.Register.rout) {
                            Register(navController = navController, auth = auth)
                        }
                        composable(Routes.Home.rout) {
                            Home(navController = navController, auth = auth)
                        }
                        composable(Routes.LoginChoose.rout) {
                            MaterialTheme(colorScheme = MaterialTheme.colorScheme) { // Apply theme here
                                LoginChoose(navController = navController, auth = auth)

                            }
                        }
                        composable(Routes.Profile.rout) {
                            MaterialTheme(colorScheme = MaterialTheme.colorScheme) { // Apply theme here
                                Profile(navController = navController, auth = auth)
                            }
                        }
                        composable(Routes.Event.rout) {
                            MaterialTheme(colorScheme = MaterialTheme.colorScheme) { // Apply theme here
                                CreatePost(navController = navController, auth = auth)
                            }
                        }
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Mini_orojectTheme {
        Text("Sample text to visualize colors")
    }
}


