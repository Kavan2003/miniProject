package com.example.mini_oroject.screens.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.mini_oroject.sampledata.Bottombar
import com.google.firebase.auth.FirebaseAuth

@Composable
fun Profile(navController: NavHostController, auth: FirebaseAuth) {
    Scaffold(
        topBar = {
//            Text(text = "Profile")
        },
        bottomBar = {
            Bottombar(navController)
        },
        content = {
            Column(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(modifier = Modifier.padding(10.dp)) {


                }
            }

        }
    )
}