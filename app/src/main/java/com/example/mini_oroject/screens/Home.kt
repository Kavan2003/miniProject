package com.example.mini_oroject.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideSubcomposition
import com.bumptech.glide.integration.compose.RequestState
import com.example.mini_oroject.R
import com.example.mini_oroject.routes.Routes
import com.example.mini_oroject.sampledata.Event
import com.example.mini_oroject.ui.theme.LightGrey
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
                        "Welcome User",
                        // "Welcome ${auth.currentUser?.displayName},",
                        style = MaterialTheme.typography.headlineMedium
                    )
                }
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            ) {

                //Home Add profile past icons
                Spacer(modifier = Modifier.weight(1.0F))

                Icon(
                    Icons.Default.Home,
                    contentDescription = "Home",
                    Modifier
                        .weight(1.0F)
                        .clickable {
                            tabIndex.intValue = 0
                            Log.d("TAG", "Home: $tabIndex")

                        })
                Spacer(modifier = Modifier.weight(1.0F))
                Icon(Icons.Default.Search, contentDescription = "Search",
                    Modifier
                        .weight(1.0F)
                        .clickable {
                            tabIndex.intValue = 1

                        })
                Spacer(modifier = Modifier.weight(1.0F))

                Icon(Icons.Default.Add, contentDescription = "Add",
                    Modifier
                        .weight(1.0F)
                        .clickable {
                            tabIndex.intValue = 2

                        })
                Spacer(modifier = Modifier.weight(1.0F))

                Icon(Icons.Default.AccountBox, contentDescription = "AccountBox",
                    Modifier
                        .weight(1.0F)
                        .clickable {

                            tabIndex.intValue = 3

                        })

                Spacer(modifier = Modifier.weight(1.0F))


            }
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
            else if (tabIndex.intValue == 3)
                Column(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Selected page: Profile")
                    Button(onClick = { navController.navigate(Routes.LoginChoose.rout) }) {
                        Text("Logout")
                    }
                }

        }
    )

}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun EventCard(
    imageUrl: String,
    title: String,
    startTime: String,
    endTime: String,
    price: String,
    onButtonClick: () -> Unit,
    modifier: Modifier
) {
    Row(
        modifier
            .clickable { onButtonClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()

                .padding(10.dp),
            verticalArrangement = Arrangement.Center // Center content vertically
        ) {
            GlideSubcomposition(
                model = imageUrl,
                modifier = Modifier
                    .fillMaxSize()

            ) {
                when (state) {
                    RequestState.Failure -> Image(
                        painter = painterResource(id = R.drawable.wifi_foreground),
                        contentDescription = null
                    )

                    RequestState.Loading -> CircularProgressIndicator(
                        color = LightGrey,
                        modifier = Modifier
                            .fillMaxSize()
                            .wrapContentSize(Alignment.Center)
                    )

                    is RequestState.Success -> Image(
                        painter, contentDescription = null, Modifier.clip(
                            RoundedCornerShape(
                                topStart = 15.dp,
                                topEnd = 15.dp,
                                bottomEnd = 15.dp,
                                bottomStart = 15.dp
                            )
                        )
                    )
                }
            }

        }
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f),
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Text(text = title, style = MaterialTheme.typography.headlineMedium)
            Row {
                Text(text = "Start Time: $startTime ", style = MaterialTheme.typography.titleSmall)

            }
            OutlinedButton(onClick = { /*TODO*/ }) {

                Text(
                    text = "Price: $price",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }


        }
    }
}


@Composable
fun ListEventCard(modifier: Modifier = Modifier, events: List<Event>) {

    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .background(MaterialTheme.colorScheme.secondary)
            .padding(10.dp)


    ) {

        events.forEach {
            EventCard(
                imageUrl = it.imageUrl,
                title = it.itemname,
                startTime = it.startTime,
                endTime = it.endTime,
                price = it.initialPrice.toString(),
                onButtonClick = { /*TODO*/ },
                modifier = Modifier

//            .background(MaterialTheme.colorScheme.background)
                    .fillMaxWidth()
                    .height(140.dp)
                    .border(
                        1.dp,
                        MaterialTheme.colorScheme.onPrimaryContainer,
                        RoundedCornerShape(25.dp)
                    )
            )
        }

    }
}
