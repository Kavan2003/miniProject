package com.example.mini_oroject.screens.create

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreatePost(navController: NavHostController, auth: FirebaseAuth) {
    var title by remember { mutableStateOf("") }
    var selectedStartDate by remember { mutableStateOf("") }
    var selectedEndDate by remember { mutableStateOf("") }
    var Price by remember { mutableStateOf("") }
    var productDescription by remember { mutableStateOf("") }

    val context = LocalContext.current
    selectedStartDate = "Select Start Date"
    selectedEndDate = "Select End Date"


    Scaffold(
        topBar = {
        },
        bottomBar = {
//            Bottombar(navController)
        }

    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
//                ButtonProfile(item = "", onButtonClick = { })
            TextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title", onTextLayout = { /* Do nothing */ }) },
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    focusedLabelColor = MaterialTheme.colorScheme.inverseSurface,
                    focusedTextColor = MaterialTheme.colorScheme.inverseSurface
                ),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                textStyle = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth()

            )
            Row(
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Button(

                    modifier = Modifier
                        .padding(20.dp)
                        .background(Color.Transparent)
                        .border(
                            1.dp,
                            MaterialTheme.colorScheme.onPrimaryContainer,
                            MaterialTheme.shapes.small
                        ),
                    colors = ButtonColors(
                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        containerColor = Color.Transparent,
                        disabledContentColor = MaterialTheme.colorScheme.onPrimaryContainer.copy(
                            alpha = 0.5f
                        ),
                        disabledContainerColor = Color.Transparent,

                        ),
                    onClick = { showDatePickerDialog(context, { selectedStartDate = it }) }) {
                    Text(text = selectedStartDate)
                }
                Button(
                    modifier = Modifier
                        .padding(20.dp)
                        .background(Color.Transparent)
                        .border(
                            1.dp,
                            MaterialTheme.colorScheme.onPrimaryContainer,
                            MaterialTheme.shapes.small
                        ),
                    colors = ButtonColors(
                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        containerColor = Color.Transparent,
                        disabledContentColor = MaterialTheme.colorScheme.onPrimaryContainer.copy(
                            alpha = 0.5f
                        ),
                        disabledContainerColor = Color.Transparent,

                        ),
                    onClick = { showDatePickerDialog(context, { selectedEndDate = it }) }) {
                    Text(text = selectedEndDate)

                }

            }
            TextField(
                value = Price,
                onValueChange = { Price = it },
                label = { Text("Price") },
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    focusedLabelColor = MaterialTheme.colorScheme.inverseSurface,
                    focusedTextColor = MaterialTheme.colorScheme.inverseSurface
                ),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                textStyle = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth()
            )
            //Discription BOx
            OutlinedTextField(
                value = productDescription,
                onValueChange = { productDescription = it },
                label = { Text("Description") },
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    focusedLabelColor = MaterialTheme.colorScheme.inverseSurface,
                    focusedTextColor = MaterialTheme.colorScheme.inverseSurface
                ),

                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                textStyle = MaterialTheme.typography.bodyLarge,
                maxLines = 10,
                minLines = 6,

                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth()
            )

            Row(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Button(
                    modifier = Modifier
                        .padding(20.dp)
                        .width(150.dp)
                        .height(50.dp),
                    colors = ButtonColors(
                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        containerColor = MaterialTheme.colorScheme.primary,
                        disabledContentColor = MaterialTheme.colorScheme.onPrimaryContainer.copy(
                            alpha = 0.5f
                        ),
                        disabledContainerColor = Color.Transparent,

                        ),
                    onClick = {
                        navController.navigate("home")
                    }

                ) {
                    Text("Cancel")
                }
                Button(
                    modifier = Modifier
                        .padding(20.dp)
                        .width(180.dp)
                        .height(50.dp),
                    colors = ButtonColors(
                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        containerColor = MaterialTheme.colorScheme.primary,
                        disabledContentColor = MaterialTheme.colorScheme.onPrimaryContainer.copy(
                            alpha = 0.5f
                        ),
                        disabledContainerColor = Color.Transparent,

                        ),
                    onClick = {

                    }
                ) {
                    Text("Next")
                }
            }

        }
    }
}