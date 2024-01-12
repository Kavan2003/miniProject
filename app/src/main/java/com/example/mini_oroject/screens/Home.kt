package com.example.mini_oroject.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold

import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.Placeholder
import com.example.mini_oroject.R
import com.example.mini_oroject.routes.Routes
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(navController : NavHostController, auth: FirebaseAuth) {

    Scaffold(

        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text("Welcome ${auth.currentUser?.displayName}", color = Color.White)
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

                  Icon(Icons.Default.Home, contentDescription = "Home", Modifier.weight(1.0F))
                Spacer(modifier = Modifier.weight(1.0F))
                  Icon(Icons.Default.Search, contentDescription = "Search", Modifier.weight(1.0F))
                Spacer(modifier = Modifier.weight(1.0F))

                  Icon(Icons.Default.Add, contentDescription = "Add", Modifier.weight(1.0F))
                Spacer(modifier = Modifier.weight(1.0F))

                  Icon(Icons.Default.AccountBox, contentDescription = "AccountBox",
                      Modifier
                          .weight(1.0F)
                          .clickable {

//                      logout
                              auth.signOut()
                              navController.navigate(Routes.LoginChoose.rout)


                          })

                Spacer(modifier = Modifier.weight(1.0F))


            }
        },

    ) { innerPadding ->

        ListEventCard(
            Modifier
                .padding(innerPadding)
                .background(Color.White))

    }

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
        Modifier
            .fillMaxWidth()
            .padding(0.dp)
            .clickable { onButtonClick() }
            .background(MaterialTheme.colorScheme.background)
            .border(1.dp, MaterialTheme.colorScheme.onPrimaryContainer)
            .padding(10.dp)
            .padding(horizontal = 0.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround,

    ){
        Column(
            modifier = Modifier
                .padding(10.dp)
                .padding(horizontal = 0.dp),

        ){
            GlideImage(
                model =  imageUrl,
                contentDescription = "Image",
                Modifier.size(100.dp),

            )

        }
        Column(
            modifier = Modifier
                .padding(10.dp)
                .padding(horizontal = 0.dp)
                .fillMaxWidth(0.999f),
            verticalArrangement = Arrangement.SpaceAround,



        ) {
            Text(text ="$title", style = MaterialTheme.typography.headlineMedium)
Row {
    Text(text = "Start Time: $startTime ", style = MaterialTheme.typography.titleSmall)
    Text(text = "End Time: $endTime", style = MaterialTheme.typography.titleSmall)

}
            Button(onClick = { /*TODO*/ },

                Modifier.align(Alignment.End),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onPrimaryContainer, contentColor = MaterialTheme.colorScheme.primaryContainer)

                ) {
                Text(text = "Price: $price", style = MaterialTheme.typography.titleSmall)

            }

        }
    }
}



@Composable
fun ListEventCard( modifier: Modifier = Modifier) {


    Column(modifier = modifier.verticalScroll(rememberScrollState())) {

        EventCard(imageUrl = "https://firebasestorage.googleapis.com/v0/b/miniproject-online-auction.appspot.com/o/car.jpg?alt=media&token=bde32f7c-61f7-49de-8633-99c8bb431fa7", title = "title", startTime ="15:30" , endTime = "18:50", price = "5000", onButtonClick = { /*TODO*/ },modifier = Modifier.weight(1f))
        EventCard(imageUrl = "https://firebasestorage.googleapis.com/v0/b/miniproject-online-auction.appspot.com/o/car.jpg?alt=media&token=bde32f7c-61f7-49de-8633-99c8bb431fa7", title = "title", startTime ="15:30" , endTime = "18:50", price = "5000", onButtonClick = { /*TODO*/ },modifier = Modifier.weight(1f))
        EventCard(imageUrl = "https://firebasestorage.googleapis.com/v0/b/miniproject-online-auction.appspot.com/o/car.jpg?alt=media&token=bde32f7c-61f7-49de-8633-99c8bb431fa7", title = "title", startTime ="15:30" , endTime = "18:50", price = "5000", onButtonClick = { /*TODO*/ },modifier = Modifier.weight(1f))
        EventCard(imageUrl = "https://firebasestorage.googleapis.com/v0/b/miniproject-online-auction.appspot.com/o/car.jpg?alt=media&token=bde32f7c-61f7-49de-8633-99c8bb431fa7", title = "title", startTime ="15:30" , endTime = "18:50", price = "5000", onButtonClick = { /*TODO*/ },modifier = Modifier.weight(1f))
        EventCard(imageUrl = "https://firebasestorage.googleapis.com/v0/b/miniproject-online-auction.appspot.com/o/car.jpg?alt=media&token=bde32f7c-61f7-49de-8633-99c8bb431fa7", title = "title", startTime ="15:30" , endTime = "18:50", price = "5000", onButtonClick = { /*TODO*/ },modifier = Modifier.weight(1f))
        EventCard(imageUrl = "https://firebasestorage.googleapis.com/v0/b/miniproject-online-auction.appspot.com/o/car.jpg?alt=media&token=bde32f7c-61f7-49de-8633-99c8bb431fa7", title = "title", startTime ="15:30" , endTime = "18:50", price = "5000", onButtonClick = { /*TODO*/ },modifier = Modifier.weight(1f))
        EventCard(imageUrl = "https://firebasestorage.googleapis.com/v0/b/miniproject-online-auction.appspot.com/o/car.jpg?alt=media&token=bde32f7c-61f7-49de-8633-99c8bb431fa7", title = "title", startTime ="15:30" , endTime = "18:50", price = "5000", onButtonClick = { /*TODO*/ },modifier = Modifier.weight(1f))
        EventCard(imageUrl = "https://firebasestorage.googleapis.com/v0/b/miniproject-online-auction.appspot.com/o/car.jpg?alt=media&token=bde32f7c-61f7-49de-8633-99c8bb431fa7", title = "title", startTime ="15:30" , endTime = "18:50", price = "5000", onButtonClick = { /*TODO*/ },modifier = Modifier.weight(1f))
        EventCard(imageUrl = "https://firebasestorage.googleapis.com/v0/b/miniproject-online-auction.appspot.com/o/car.jpg?alt=media&token=bde32f7c-61f7-49de-8633-99c8bb431fa7", title = "title", startTime ="15:30" , endTime = "18:50", price = "5000", onButtonClick = { /*TODO*/ },modifier = Modifier.weight(1f))

    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
        ListEventCard(Modifier)


}