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
import com.example.mini_oroject.screens.create.PostData
import com.example.mini_oroject.ui.theme.LightGrey
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.snapshots
import com.meetup.twain.MarkdownText

fun updatePublishStatus(documentId: String) {
    val db = FirebaseFirestore.getInstance()
    Log.w("TAG", "updatePublishStatus: ${documentId}")

    val docRef = db.collection("auction_item").document(documentId)
    Log.w("TAG", "updatePublishStatus: ${docRef.snapshots()}")
    docRef.update("publish", true)
        .addOnSuccessListener {
            // Update successful
            Log.d("PublishUpdate", "Document published successfully!")
        }
        .addOnFailureListener { e ->
            Log.w("PublishUpdate", "Error publishing document: $e")
        }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalGlideComposeApi::class)
@Composable
fun EventDetails(data: PostData, auth: FirebaseAuth) {

    val firestore = FirebaseFirestore.getInstance()
    val isAdmin = data.selectedEndDate == "true"
    val id = data.id
    //   var postData by remember { mutableStateOf<PostData?>(null) }
    var imageUrls by remember { mutableStateOf<List<String>?>(null) }
    var currentPrice by remember { mutableStateOf<String?>("currentPrice") }
    var nextPrice by remember { mutableStateOf<Int?>(0) }


    val realtimeDatabase =
        FirebaseDatabase.getInstance().reference.child("auction_items").child(data.id)


    // Fetch data from Firebase on initial load or when itemId changes
    LaunchedEffect(data.id) {

        realtimeDatabase.addValueEventListener(object :

            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()) {


                    snapshot.children.forEach {
                        Log.e(" for value", "onDataChangefwfewdqwdfq: ${it.value.toString()}")
                        currentPrice = it.value.toString()
                    }
                    Log.e(
                        "TAG above current price",
                        "onDataChange:${(currentPrice?.toInt() ?: 500000) + 100} ",
                    )
                    nextPrice = (currentPrice?.toInt() ?: 500000) + ((currentPrice?.toInt()
                        ?: 500000) * 0.20).toInt()
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

        firestore.collection("auction_item").document(data.id)
            .get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {


                    //postData = data
                    imageUrls =
                        (if ((documentSnapshot.data?.get("imageurl") != null) && (documentSnapshot.data!!["imageurl"] as List<String>).isNotEmpty()) {
                            documentSnapshot.data!!["imageurl"] as List<String>
                        } else {
                            listOf("")
                        }) // Assuming imageUrl is a list of strings
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
        Text(text = "${data.title}", style = MaterialTheme.typography.titleMedium)

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
                        Text(
                            text = data.condition,
                            fontSize = 18.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
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
                            text = data.shippingpolicy,
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
                        Text(
                            text = data.priceshipping,
                            fontSize = 18.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
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
                        Text(
                            text = data.returnpolicy,
                            fontSize = 18.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
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
                    MarkdownText(
                        markdown = data.productDescription,
                        modifier = Modifier
                            .fillMaxWidth(),
                        fontSize = 14.sp,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface


                    )
                }
            })

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
        if (!(isAdmin))
            Button(onClick = {
                // Place bid
                val priceData = HashMap<String, Any?>()
                priceData["id"] = data.id
                priceData["price"] =
                    nextPrice?.toDouble()  // Assuming price is a String, convert to Double

                // Write data to Realtime Database
                realtimeDatabase.setValue(priceData)
                    .addOnSuccessListener {
                        Log.d("RealtimeDB", "Data saved for document: ${data.id}")
                        val dataToSave = hashMapOf(
                            "pay_by" to auth.currentUser?.uid,
                            "pay_amt" to nextPrice,
                            "product_reference" to data.id
                        )
                        firestore.collection("user_payment").add(dataToSave)
                            .addOnSuccessListener { documentReference ->
                                Log.d("TAG", "Document written with ID: ${documentReference.path}")
                            }
                            .addOnFailureListener { e ->
                                Log.w("TAG", "Error adding document", e)
                            }
                    }
                    .addOnFailureListener { e ->
                        Log.w("RealtimeDB", "Error saving data to Realtime Database", e)
                    }

            }) {
                Text(text = "Place Bid : $nextPrice")
            }
        else
            Button(
                onClick = {
                    Log.w("TAG", "updatePublishStatus: ${id}")

                    updatePublishStatus(id)
                    
                },
                content = {
                    Text("Approve")
                }
            )
    }
}