package com.example.mini_oroject.screens.home

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.mini_oroject.sampledata.Bottombar
import com.example.mini_oroject.sampledata.Event
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(navController: NavHostController, auth: FirebaseAuth) {
    var tabIndex = remember { mutableIntStateOf(0) }
    var events = remember { mutableStateListOf<Event>() };

    FirebaseFirestore.getInstance().collection("auction_item")
        .get()
        .addOnSuccessListener { documents ->
            events.clear()



            for (document in documents) {
                Log.i("TAG", document.toString())

                events.add(
                    Event(
                        categories = (if ((document.data["Categories"] != null)) {
                            document.data["Categories"] as List<String>
                        } else {
                            listOf("Categories not available")
                        }) as List<String>,
                        description = (if ((document.data["description"] != null)) {
                            document.data["description"] as String
                        } else {
                            "description not available"
                        }) as String,
                        imageUrl = (if ((document.data["imageURL"] != null)) {
                            document.data["imageURL"] as String

                        } else {
                            "https://firebasestorage.googleapis.com/v0/b/mini-oroproject.appspot.com/o/images%2Fimage_picker_2021-09-27-16-11-46-1.jpg?alt=media&token=3b9b9b1a-9b9a-4b9e-9b0a-9b9b9b9b9b9b"
                        }) as String,
                        initialPrice = (if ((document.data["initialPrice"] != null)) {
                            document.data["initialPrice"] as String
                        } else {
                            "0"
                        }) as String,
                        itemname = (if ((document.data["itemname"] != null)) {
                            document.data["itemname"] as String
                        } else {
                            "itemname not available"
                        }) as String,
                        startTime = (if ((document.data["startTime"] != null)) {
                            (document.data["startTime"] as Timestamp).toDate().toString()
                        } else {
                            "startTime not available"
                        }) as String,
                        endTime = "endTime not available",

                        )

                )
                Log.d("TAG 110 line EVENT", events[0].description)
            }
        }


    Scaffold(

        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSecondary,

                    ),
                modifier = Modifier.shadow(
                    elevation = 80.dp,
                    ambientColor = Color.Black,
                    spotColor = Color.Black
                ),
                title = {
                    Text(
//                        "Welcome User",
                        "Welcome ${auth.currentUser?.displayName},",
                        style = MaterialTheme.typography.headlineMedium
                    )
                }
            )
        },
        bottomBar = {
            Bottombar(navController)
        },

        content = { innerPadding ->
            if (tabIndex.intValue == 0)
                ListEventCard(
                    Modifier
                        .padding(innerPadding)
                        .background(Color.White),
                    events
                )
            else if (tabIndex.intValue == 1)
                Column(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Selected page: Search")
                }
            else if (tabIndex.intValue == 2)
                Column(
                    modifier = Modifier
                        .padding(innerPadding)

                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Selected page: Add Item")
                    Text(text = "Selected page: Add Item")
                    Text(text = "Selected page: Add Item")
                    Text(text = "Selected page: Add Item")

                }
//            else if (tabIndex.intValue == 3)
//                Column(
//                    modifier = Modifier
//                        .padding(innerPadding)
//                        .fillMaxSize(),
//                    verticalArrangement = Arrangement.Center,
//                    horizontalAlignment = Alignment.CenterHorizontally
//                ) {
//                    Text(text = "Selected page: Profile")
//                    Button(onClick = { navController.navigate(Routes.LoginChoose.rout) }) {
//                        Text("Logout")
//                    }
//                }

        }
    )

}

