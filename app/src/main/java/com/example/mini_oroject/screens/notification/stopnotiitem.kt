package com.example.mini_oroject.screens.notification

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideSubcomposition
import com.bumptech.glide.integration.compose.RequestState
import com.example.mini_oroject.R
import com.example.mini_oroject.ui.theme.LightGrey
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.meetup.twain.MarkdownText

data class UserData(
    var name: String?,
    var email: String?,
    var phone: String?,
    var address: String?,

    )


@OptIn(ExperimentalFoundationApi::class, ExperimentalGlideComposeApi::class)
@Composable
fun pEventDetails(
    id: String,
    auth: FirebaseAuth
) {

    val firestore = FirebaseFirestore.getInstance()
    //   var postData by remember { mutableStateOf<PostData?>(null) }
    var imageUrls by remember { mutableStateOf<List<String>?>(null) }
    var bidstop by remember { mutableStateOf<String?>("stop Bid") }

    var itemname by remember { mutableStateOf<String?>(null) }
    var description by remember { mutableStateOf<String?>(null) }
    var condition by remember { mutableStateOf<String?>(null) }
    var city by remember { mutableStateOf<String?>(null) }
    var shippingprice by remember { mutableStateOf<String?>(null) }
    var returnpolicy by remember { mutableStateOf<String?>(null) }


    var currentPrice by remember { mutableStateOf<String?>("currentPrice") }
    var nextPrice by remember { mutableStateOf<Int?>(0) }
    var a by remember { mutableStateOf<UserData?>(null) }

    val realtimeDatabase =
        FirebaseDatabase.getInstance().reference.child("auction_items").child(id)


    // Fetch data from Firebase on initial load or when itemId changes
    LaunchedEffect(id) {

        realtimeDatabase.addValueEventListener(object :

            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()) {


                    snapshot.children.forEach {
                        currentPrice = it.value.toString()
                    }

                    Log.i("TAG at current price", "onDakltaChmmange: $currentPrice ")
                } else {
                    currentPrice = "1000000.0"
                    Log.e("TAG at 68 EventDetails", "onDataChange: No data found")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle errors
                Log.e("TAG at 73 EventDetails", "onCancelled: $error")
            }
        })

        firestore.collection("auction_item").document(id)
            .get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {


                    //postData = data
                    imageUrls =
                        (if ((documentSnapshot.data?.get("imageurl") != null) && (documentSnapshot.data!!["imageurl"] as List<String>).isNotEmpty()) {
                            documentSnapshot.data!!["imageurl"] as List<String>
                        } else {
                            listOf("")
                        })
                    itemname = documentSnapshot.data?.get("itemname") as String
                    description = documentSnapshot.data?.get("description") as String
                    condition = documentSnapshot.data?.get("condition") as String
                    bidstop =
                        if (documentSnapshot.data?.get("isActive") as Boolean) "stop Bid" else "Bid Stopped"
                    city = documentSnapshot.data?.get("shippingpolicy") as String
                    shippingprice = documentSnapshot.data?.get("priceshipping") as String
                    returnpolicy = documentSnapshot.data?.get("returnpolicy") as String
                } else {
                    Log.e("TAG at 98 EventDetails", "Document does not exist")


                }
                if (bidstop == "Bid Stopped") {
                    Log.e(" ", "pEventDetails: a callse ")
                    //   a = getLargestPaymentDetails(id)
                    getLargestPaymentDetails(id) { fetchedUserData ->
                        a = fetchedUserData // Update user data state
                    }
                    Log.e(" ", "pEventDetails: a  ")

                }
            }
    }


    val pagerState = rememberPagerState(pageCount = { imageUrls?.size ?: 0 })

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (a == null) {
            HorizontalPager(state = pagerState, modifier = Modifier.height(200.dp)) { page ->
                GlideSubcomposition(
                    modifier = Modifier

                        .height(300.dp)
                        .align(Alignment.CenterHorizontally),
                    model = imageUrls?.get(page),
                ) {
                    when (state) {

                        RequestState.Failure -> Image(
                            painter = painterResource(id = R.drawable.wifi_foreground),
                            contentDescription = null,
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxHeight(0.2f),
                        )

                        RequestState.Loading -> CircularProgressIndicator(
                            color = LightGrey,
                            modifier = Modifier
                                .wrapContentSize(Alignment.Center)
                                .fillMaxWidth(0.4f),
                        )

                        is RequestState.Success -> Image(
                            painter, contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(1.dp, Color.Gray, RoundedCornerShape(16.dp)),
                            contentScale = ContentScale.FillBounds
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "$itemname", style = MaterialTheme.typography.titleMedium)

            Spacer(modifier = Modifier.height(8.dp))
            Column {
                Row {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .border(1.dp, Color.Gray)
                    ) {
                        Column(modifier = Modifier.padding(8.dp)) {
                            Text(
                                text = "Condition",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            condition?.let {
                                Text(
                                    text = it,
                                    fontSize = 18.sp,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .border(1.dp, Color.Gray)
                    ) {
                        Column(modifier = Modifier.padding(8.dp)) {
                            Text(
                                text = "product city",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = city ?: "N/A",
                                fontSize = 18.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
                Row {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .border(1.dp, Color.Gray)
                    ) {
                        Column(modifier = Modifier.padding(8.dp)) {
                            Text(
                                text = "Shipping Price",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            shippingprice?.let {
                                Text(
                                    text = it,
                                    fontSize = 18.sp,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .border(1.dp, Color.Gray)
                    ) {
                        Column(modifier = Modifier.padding(8.dp)) {
                            Text(
                                text = "Return Policy",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            returnpolicy?.let {
                                Text(
                                    text = it,
                                    fontSize = 18.sp,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }
                }
            }





            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(
                Modifier.height(
                    200.dp
                ),
                content = {
                    item {
                        description?.let {
                            MarkdownText(
                                markdown = it,
                                modifier = Modifier
                                    .fillMaxWidth(),
                                fontSize = 14.sp,
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurface


                            )
                        }
                    }
                })
            Spacer(modifier = Modifier.height(16.dp))
        } else {
            Column {
                Text(text = "Winner User Details", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Name: ${a!!.name}",
                    style = MaterialTheme.typography.titleSmall
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Email: ${a!!.email}",
                    style = MaterialTheme.typography.titleSmall
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Phone: ${a!!.phone}",
                    style = MaterialTheme.typography.titleSmall
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Address: ${a!!.address}",
                    style = MaterialTheme.typography.titleSmall
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        if (currentPrice == "currentPrice") {
            Text(
                text = "Current Price: $currentPrice",
                style = MaterialTheme.typography.titleSmall
            )
        } else {
            Text(
                text = "Current Price: ${currentPrice ?: "N/A"}",
                style = MaterialTheme.typography.titleSmall
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        if (bidstop == "Bid Stopped") {

        }
        Button(onClick = {
            if (bidstop == "stop Bid")
                if (auth.currentUser != null) {
                    firestore.collection("auction_item").document(id)
                        .update("isActive", false) // Update isActive property to false
                        .addOnSuccessListener {
                            Log.d("TAG", "Bid stopped successfully")
                            // Update UI to reflect stopped bid (optional)
                            bidstop = "Bid Stopped"
                            getLargestPaymentDetails(id) { fetchedUserData ->
                                a = fetchedUserData // Update user data state
                            }
                        }
                        .addOnFailureListener { exception ->
                            Log.w("TAG", "Error stopping bid: $exception")
                            // Handle errors (optional)
                        }
                }


        }) {
            Text(text = "${bidstop} ")
        }
    }
}
