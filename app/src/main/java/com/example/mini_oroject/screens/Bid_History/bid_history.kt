package com.example.mini_oroject.screens.Bid_History


import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import com.example.mini_oroject.screens.bottombar.Bottombar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore


data class bid(
    val bid: String,
    val itemName: String,
)


@Composable
fun Bid_History_item(bid: bid, onStopAuction: () -> Unit) {
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
        //elevation = 4.dp
    ) {
        Row(
            modifier = Modifier,

            verticalAlignment = Alignment.CenterVertically,

            ) {
            Column(modifier = Modifier.padding(16.dp)) {

                Text(
                    text = "Bid Price: ${bid.bid}",
                    style = MaterialTheme.typography.labelLarge
                )
                Text(
                    text = "Bid on : ${bid.itemName}",
                    style = MaterialTheme.typography.labelLarge
                )


            }

        }


    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun bidScreen(

    navController: NavHostController,
    auth: FirebaseAuth
) {
    val currentUser = auth.currentUser!!.uid
    val auctionList = remember { mutableStateListOf<bid>() }

    Log.e("TAG", "NotificationScreen: start")


    // Access your FirebaseFirestore instance
    val db = FirebaseFirestore.getInstance()
    var currentPrice by remember { mutableStateOf<String?>("currentPrice") }

    val firestore = FirebaseFirestore.getInstance()
    val realtimeDatabase =
        FirebaseDatabase.getInstance().reference.child("auction_items")
    var id = ""

    val docRef = db.collection("user_payment")
    Log.e("TAG", "NotificationScreen: start")


    docRef.whereEqualTo("pay_by", currentUser)
        .get()
        .addOnSuccessListener { documents ->
            auctionList.clear()
            for (document in documents) {

                Log.i("loadActiveAuctions", "loadActiveAuctions: ${document.data["itemname"]} ")
                val data = document.data
                id = document.id

                var product_referance = data["product_reference"] as String
                val payamt = data["pay_amt"].toString()
                Log.d("TAG", "bidScreen: $payamt ")

                db.collection("auction_item").document(product_referance).get()
                    .addOnSuccessListener { documentSnapshot ->
                        if (documentSnapshot.exists()) {
                            val data = documentSnapshot.data
                            Log.d("TAG", "bidScreen: #data $data")
                            if (data != null && data.containsKey("itemname")) {
                                Log.d("TAG", "bidScreen: #data ${data["itemname"]}")

                                val itemName = data["itemname"] as String
                                Log.d("TAG", "bidScreen: #itemName $itemName")

                                Log.d("itemName", "bidScreen: #itemName ${itemName}")

                                auctionList.add(
                                    bid(payamt, itemName)
                                )
                                // Use itemName here
                            } else {
                                // Handle the case where the document exists but doesn't have the "itemname" field
                                Log.w("Firestore", "Document $id exists but lacks 'itemname' field")
                            }
                        } else {
                            // Handle the case where the document with the given ID doesn't exist
                            Log.w("Firestore", "Document $id does not exist")
                        }
                    }


            }

        }

        .addOnFailureListener { exception ->
            // Handle any errors
            Log.w("NotificationViewModel", "Error getting documents: $exception")
        }


    Scaffold(
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
                        "Bid History,",
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


                        auctionList.forEach { bids ->
                            Bid_History_item(bid = bids, onStopAuction = {
                                //stopAuction(auction.id.toInt())
                            })

                        }

                    }
                }
            }
        }

    )
}


