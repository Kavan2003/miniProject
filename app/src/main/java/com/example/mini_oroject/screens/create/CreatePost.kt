package com.example.mini_oroject.screens.create

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.mini_oroject.screens.bottombar.Bottombar
import com.example.mini_oroject.screens.profile.ButtonProfile
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreatePost(navController: NavHostController, auth: FirebaseAuth) {
    var title by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
        },
        bottomBar = {
            Bottombar(navController)
        },
        content = {
            Column(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
            ) {
//                ButtonProfile(item = "", onButtonClick = { })
                TextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title") },
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        focusedLabelColor = MaterialTheme.colorScheme.inverseSurface,
                        focusedTextColor = MaterialTheme.colorScheme.inverseSurface,
                    ),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    textStyle = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .padding(20.dp)
                        .fillMaxWidth()

                )
                Row {
                    ButtonProfile(item = "Select Date and Time") {
                       
                    }
                }
            }
        }

    )
}