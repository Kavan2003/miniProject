package com.example.mini_oroject.screens.create

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
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
import com.example.mini_oroject.routes.Routes
import com.google.ai.client.generativeai.GenerativeModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URLEncoder
import java.nio.charset.StandardCharsets


suspend fun geminiai(input: String): String? {
    val generativeModel = GenerativeModel(
        // For text-only input, use the gemini-pro model
        modelName = "gemini-pro",
        // Access your API key as a Build Configuration variable (see "Set up your API key" above)
        apiKey = "AIzaSyAoq73J1Om_xL_mT_uJYkaBVgG6Ff9-xtU"
    )

    val prompt =
        "Act as an writer and improve this product description for auction write Directly the body as this is going to be pasted in description of product and don't write any other additional textother that required ones. Use Mark Down format. Text = ###  $input ### "
    val response = generativeModel.generateContent(prompt)
    print(response.text)
    return response.text
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable

fun CreatePost(navController: NavHostController, auth: FirebaseAuth) {
    var title by remember { mutableStateOf("") }
    var selectedStartDate by remember { mutableStateOf("") }
    var selectedEndDate by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var productDescription by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }


//    var input by remember { mutableStateOf("") }//this is produc

    val context = LocalContext.current
    selectedStartDate = "Select Start Date"
    selectedEndDate = "Select End Date"


    BoxWithConstraints {
        val constraints = this.constraints


        Scaffold(
            topBar = {
            },
            bottomBar = {
                Box(modifier = Modifier.size(0.dp, 0.dp))
            }

        ) {
            Column(
                modifier = Modifier
                    .padding(it)
                    .height(constraints.maxHeight.dp)
                    .scrollable(

                        enabled = true,
                        orientation = Orientation.Vertical,
                        state = rememberScrollState(),
                        reverseDirection = true,

                        ),

                ) {
                TextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title", onTextLayout = { }) },
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
                    Spacer(modifier = Modifier.height(20.dp))

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
                    Spacer(modifier = Modifier.height(10.dp))

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
                Spacer(modifier = Modifier.height(10.dp))


                TextField(
                    value = price,
                    onValueChange = { price = it },
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
                Spacer(modifier = Modifier.height(10.dp))


                //Discription BOx
                if (isLoading) {
                    Box(modifier = Modifier.padding(20.dp)) {
                        Text("Loading....")
                    }
                } else {
                    LazyColumn(content = {
                        item {
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
                                textStyle = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier
                                    .padding(20.dp)
                                    .fillMaxWidth()
                                    .height(150.dp)
                            )
                        }

                    })
                }
                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = {
                        if (productDescription.isNotEmpty()) {
                            isLoading = true
                            CoroutineScope(Dispatchers.IO).launch {
                                val improvedDescription = geminiai(productDescription)
                                withContext(Dispatchers.IO) {
                                    if (improvedDescription != null)
                                        productDescription = improvedDescription.toString()

                                }
                                isLoading = false

                            }
                        } else {
                            Toast.makeText(context, "Fill Description", Toast.LENGTH_LONG)
                                .show()
                        }
                    },
                    modifier = Modifier
                        .padding(20.dp)
                        .fillMaxWidth(),
                ) {
                    Text("Improve with AI")
                }
                Spacer(modifier = Modifier.height(20.dp))

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
                            if (title.isNotEmpty() && selectedStartDate.isNotEmpty() && selectedEndDate.isNotEmpty() && price.isNotEmpty() && productDescription.isNotEmpty()) {
//                            navController.navigate("home")
                                Toast.makeText(context, "Form Success", Toast.LENGTH_LONG)
                                    .show()

                                var p = PostData(
                                    title = title,
                                    selectedStartDate = selectedStartDate,
                                    selectedEndDate = selectedEndDate,
                                    price = price,
                                    productDescription =
                                    URLEncoder.encode(
                                        productDescription,
                                        StandardCharsets.UTF_8.toString()
                                    ),
                                    shippingpolicy = "shipping policy",
                                    returnpolicy = "return policy",
                                    priceshipping = "", condition = ""

                                )

                                //convert to string
                                val data = postDataSerializer(p)
                                navController.navigate(Routes.Image.rout + "/$data")
                            } else {

                                //Show Error
                                Toast.makeText(context, "Fill all Details", Toast.LENGTH_LONG)
                                    .show()

                            }
                        }
                    ) {
                        Text("Next")
                    }
                }


            }
        }
    }
}