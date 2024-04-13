package com.example.mini_oroject.screens.sampledata


import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.mini_oroject.routes.Routes
import com.example.mini_oroject.screens.bottombar.Bottombar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore


data class Auction(
    val id: String,
    val itemName: String,
    val currentPrice: String,
    val buyerName: String,
    val isActive: Boolean
)


@Composable
fun AdminItem(auction: Auction, onStopAuction: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.onBackground),
        colors = CardColors(
            containerColor = MaterialTheme.colorScheme.onBackground,
            contentColor = MaterialTheme.colorScheme.surface,
            disabledContentColor = MaterialTheme.colorScheme.onSurface,
            disabledContainerColor = MaterialTheme.colorScheme.onSurface

        ),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),

            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween

        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = auction.itemName, style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Initial Price: ${auction.currentPrice}",
                    style = MaterialTheme.typography.labelLarge
                )
                Text(
                    text = "Is Active : ${auction.isActive}",
                    style = MaterialTheme.typography.labelLarge
                )


            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = onStopAuction) {
                Text("View Auction")
            }
        }


    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminScreen(

    navController: NavHostController,
    auth: FirebaseAuth
) {
    val currentUser = auth.currentUser!!.uid
    val auctionList = remember { mutableStateListOf<Auction>() }

    val db = FirebaseFirestore.getInstance()
    var currentPrice by remember { mutableStateOf<String?>("currentPrice") }

    val firestore = FirebaseFirestore.getInstance()
    val realtimeDatabase =
        FirebaseDatabase.getInstance().reference.child("auction_items")
    var id = ""

    val docRef = db.collection("auction_item")
    //  Log.e("TAG", "NotificationScreen: start")


    docRef.whereEqualTo("created_by", currentUser).whereEqualTo("publish", false)
        .get()
        .addOnSuccessListener { documents ->
            auctionList.clear()
            for (document in documents) {
                val data = document.data
                id = document.id

                val itemName = data["itemname"] as String
                currentPrice = (data["initialPrice"] as String)
                val isActive = data["isActive"] as Boolean
                Log.d("itemcheck", "$itemName, $currentPrice, $isActive")


                auctionList.add(
                    Auction(
                        id, itemName, currentPrice!!, "", isActive
                    )
                )
            }

        }

        .addOnFailureListener { exception ->
            // Handle any errors
            Log.w("NotificationViewModel", "Error getting documents: $exception")
        }


    Scaffold(
//        topBar = { Text("") },
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
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
                        "Notification,",
                        style = MaterialTheme.typography.headlineMedium
                    )
                }
            )
        },
        bottomBar = {

            Bottombar(navController)

        },
        content = { innerPadding ->

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(16.dp)
                    .padding(innerPadding),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    item {


                        auctionList.forEach { auction ->
                            AdminItem(auction = auction, onStopAuction = {
                                //navigation to pEventDetails()
                                Log.e("TAG", "NotificationScreen: onStopAuction")
                                val data = auction.id
                                navController.navigate(Routes.bid_his_des.rout + "/$data")

                            })

                        }

                    }
                }
            }
        }

    )
}


