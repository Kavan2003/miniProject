package com.example.mini_oroject.screens.create

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.mini_oroject.routes.Routes
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import finalCheckUpload
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.Date


class ImageUploader(private val storage: FirebaseStorage) {
    suspend fun uploadImage(uri: Uri, imageName: String): String {
        val storageRef = storage.reference.child("images/$imageName")
        val imageRef = storageRef.child(imageName)

        val uploadTask = imageRef.putFile(uri)
        uploadTask.await() // Use await() for a simpler approach

        val downloadUrl = imageRef.downloadUrl.await()
        return downloadUrl.toString()
    }
}

enum class _ConditionType(val description: String) {
    VERY_OLD("Very Old"),
    OLD("old"),
    NEW("New")
}

enum class _ReturnType(val description: String) {
    No("No Return"),
    seven("7 days Return"),
    fourteen("14 days Return")
}


@Composable
fun ImageUpload(navController: NavHostController, auth: FirebaseAuth, data: PostData) {
    val selectedImages = remember { mutableStateListOf<Uri>() }
    val uploadedImageUrls = remember { mutableStateListOf<String>() }
    val isUploading = remember { mutableStateOf(false) }
    val imagesUploaded = remember { mutableIntStateOf(0) }
    val showDialog = remember { mutableStateOf(false) }
    val isUploadComplete = remember { mutableStateOf(false) }
    val auctionUploadStatus = remember { mutableStateOf("") }
    var imagemode by remember { mutableStateOf(false) }
    var shippingpolicy by remember { mutableStateOf("") }
    var selectedCondition by remember { mutableStateOf(_ConditionType.VERY_OLD) }
    var selectedReturn by remember { mutableStateOf(_ReturnType.No) }
    var priceshipping by remember {
        mutableStateOf("")
    }


    var expanded by remember { mutableStateOf(false) }
    var expanded2 by remember { mutableStateOf(false) }

    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.PickMultipleVisualMedia()) { uris ->
            selectedImages.addAll(uris)
        }

    fun parseDateString(inputString: String): Date? {
        val inputFormat =
            SimpleDateFormat("dd-MM-yyyy") // Adjust format as needed (e.g., "yyyy-MM-dd")
        return try {
            inputFormat.parse(inputString)
        } catch (e: Exception) {
            Log.e("Date Parsing", "Error parsing date: $e")
            null
        }
    }

    fun stringToFirebaseTimestamp(inputString: String): Timestamp? {
        val parsedDate = parseDateString(inputString)
        return if (parsedDate != null) {
            Timestamp(parsedDate)
        } else {
            null
        }
    }

    val datahash = hashMapOf(
        "itemname" to data.title,
        "startTime" to stringToFirebaseTimestamp(data.selectedStartDate),
        "endTime" to stringToFirebaseTimestamp(data.selectedEndDate),
        "initialPrice" to data.price,
        "created_by" to auth.currentUser?.uid,
        "Categories" to listOf("Nothing"), // Replace with logic to get categories
        "description" to data.productDescription,
        "imageurl" to uploadedImageUrls,
        "priceshipping" to priceshipping,
        "shippingpolicy" to shippingpolicy,
        "condition" to selectedCondition.description,
        "returnpolicy" to selectedReturn.description,
        
        )


    val imageUploader = remember { ImageUploader(FirebaseStorage.getInstance()) }

    suspend fun uploadAllImages() {
        val imageUrls = mutableListOf<String>()
        selectedImages.forEach { uri ->
            val url = imageUploader.uploadImage(uri, uri.lastPathSegment ?: "")
            imageUrls.add(url)
        }
        uploadedImageUrls.addAll(imageUrls)
        if (uploadedImageUrls.size == selectedImages.size) {
            isUploading.value = false
            finalCheckUpload(datahash, data.price, auth)
        }

    }






    Scaffold(
        topBar = {
            Column {
                if (isUploading.value) {
                    // Show horizontal loading indicator
                    LinearProgressIndicator(
                        progress = { imagesUploaded.value.toFloat() / selectedImages.size },
                    )
                }
            }
        },
        bottomBar = {

            if (selectedImages.isNotEmpty()) {
                Button(onClick = {
                    showDialog.value = true
                    CoroutineScope(Dispatchers.IO).launch {
                        try {
                            uploadAllImages()
                            isUploadComplete.value = true
                            auctionUploadStatus.value = "Upload successful!"
                        } catch (e: Exception) {
                            auctionUploadStatus.value = "Error uploading images: ${e.message}"
                        }
                    }
                }) {
                    Text("Create Auction")
                }
            }
        },
        content = {


            if (!imagemode) {
                Column(
                    Modifier,
                    verticalArrangement = Arrangement.SpaceAround,
                    horizontalAlignment = Alignment.Start,
                ) {


                    Row {
                        Text(
                            text = "Condition: ${selectedCondition.description}",
                            modifier = Modifier
                                .clickable { expanded2 = true }
                                .padding(10.dp)
                                .border(
                                    1.dp,
                                    MaterialTheme.colorScheme.onSurface,
                                    MaterialTheme.shapes.small,
                                )
                                .padding(10.dp)

                        )
                        DropdownMenu(
                            expanded = expanded2,
                            onDismissRequest = { expanded2 = false },
                        ) {
                            _ConditionType.entries.forEach { condition ->
                                DropdownMenuItem(
                                    onClick = {
                                        selectedCondition = condition
                                        expanded2 = false
                                    },
                                    text = { Text(condition.description) }

                                )
                            }
                        }
                    }



                    Row {
                        Text(
                            text = "Return Policy: ${selectedReturn.description}",
                            modifier = Modifier
                                .clickable { expanded = true }
                                .padding(10.dp)
                                .border(
                                    1.dp,
                                    MaterialTheme.colorScheme.onSurface,
                                    MaterialTheme.shapes.small,
                                )
                                .padding(10.dp)

                        )
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false },
                        ) {
                            _ReturnType.entries.forEach { condition ->
                                DropdownMenuItem(
                                    onClick = {
                                        selectedReturn = condition
                                        expanded = false
                                    },
                                    text = { Text(condition.description) }

                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(20.dp))


                    TextField(
                        value = priceshipping,
                        onValueChange = { priceshipping = it },
                        label = { Text("Shipping Price") },
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




                    Spacer(modifier = Modifier.height(20.dp))

                    TextField(
                        value = shippingpolicy,
                        onValueChange = { shippingpolicy = it },
                        label = { Text("Enter your Product's current city location example: Rajkot ") },
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
                    Spacer(modifier = Modifier.height(20.dp))
                    TextButton(
                        onClick = { imagemode = true },
                        modifier = Modifier
                            .padding(20.dp)
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.onBackground),
                    ) {
                        Text(text = "Next");
                    }


                }

            } else
                if (selectedImages.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Surface(
                            modifier = Modifier
                                .padding(40.dp)
                                .size(200.dp, 200.dp)
                                .background(color = Color.Gray)
                                .clickable {
                                    launcher.launch(
                                        PickVisualMediaRequest(
                                            mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly
                                        )
                                    )
                                },
                            color = Color.Transparent
                        ) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "Upload Image Here!",
                                    color = Color.White,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(it),
                        contentAlignment = Alignment.TopCenter
                    ) {
                        LazyColumn {
                            items(selectedImages) { uri ->
                                Image(
                                    painter = rememberAsyncImagePainter(model = uri),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .height(200.dp)
                                        .fillMaxWidth()
                                        .padding(16.dp)
                                )
                            }
                        }
                    }
                }



            if (showDialog.value) {
                AlertDialog(
                    onDismissRequest = { },
                    title = { Text("Uploading Images") },
                    text = { Text("Please wait...") },
                    confirmButton = { }
                )
            }

            if (isUploadComplete.value) {
                // Handle upload completion and navigation
                if (auctionUploadStatus.value == "Upload successful!") {
                    navController.navigate(Routes.Home.rout)
                }
            }
        })
}

