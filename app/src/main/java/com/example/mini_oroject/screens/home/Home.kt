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
import com.example.mini_oroject.data_model.Event
import com.example.mini_oroject.screens.bottombar.Bottombar
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore

fun listenForPriceChanges(itemId: String) {
    val database = FirebaseDatabase.getInstance()
    val myRef = database.getReference("items")

    val priceListener = object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            val updatedPrice =
                dataSnapshot.child(itemId).child("price").getValue(Double::class.java)
            // Do something with the updated price
        }

        override fun onCancelled(databaseError: DatabaseError) {
            // Failed to read value
            Log.w("TAG", "Failed to read value.", databaseError.toException())
        }
    }
    myRef.addValueEventListener(priceListener)
}


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
                        id = document.id,
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
                        imageUrl = (if ((document.data["imageurl"] != null) && (document.data["imageurl"] as List<String>).isNotEmpty()) {
                            document.data["imageurl"] as List<String>
                        } else {
                            listOf("")
                        }),
                        initialPrice = (if ((document.data["initialPrice"] != null)) {
                            document.data["initialPrice"] as String
                        } else {
                            "0"
                        }),
                        itemname = (if ((document.data["itemname"] != null)) {
                            document.data["itemname"] as String
                        } else {
                            "itemname not available"
                        }),
                        startTime = (if ((document.data["startTime"] != null)) {
                            (document.data["startTime"] as Timestamp).toDate().toString()
                        } else {
                            "startTime not available"
                        }),
                        endTime = (if ((document.data["endTime"] != null)) {
                            (document.data["endTime"] as Timestamp).toDate().toString()
                        } else {
                            "endTime not available"
                        }),

                        )

                )
                Log.d("TAG 86 line EVENT", events.toString())

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
                    events,

                    navController
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


        }
    )

}

