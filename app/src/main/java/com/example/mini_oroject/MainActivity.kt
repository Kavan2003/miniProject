package com.example.mini_oroject

import android.content.res.Resources.Theme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mini_oroject.routes.Routes
import com.example.mini_oroject.screens.Home
import com.example.mini_oroject.screens.Login
import com.example.mini_oroject.screens.LoginChoose
import com.example.mini_oroject.screens.Register
import com.example.mini_oroject.ui.theme.DarkGreen
import com.example.mini_oroject.ui.theme.Mini_orojectTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            Mini_orojectTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),



                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = Routes.LoginChoose.rout ){
                        composable(Routes.Login.rout){
                            Login(navController = navController)
                        }
                        composable(Routes.Register.rout){
                            Register(navController = navController)
                        }
                        composable(Routes.Home.rout){
                            Home(navController = navController)
                        }
                        composable(Routes.LoginChoose.rout){
                            MaterialTheme(colorScheme = MaterialTheme.colorScheme) { // Apply theme here
                                LoginChoose(navController = navController)

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


