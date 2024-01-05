package com.example.mini_oroject.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import com.example.mini_oroject.R
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(navController : NavHostController ) {

    Scaffold(

        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text("Welcome User", color = Color.White)
                }
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.primary,
            ) {

                  //Home Add profile past icons
                Spacer(modifier = Modifier.weight(1.0F))

                  Icon(Icons.Default.Home, contentDescription = "Home", Modifier.weight(1.0F))
                Spacer(modifier = Modifier.weight(1.0F))
                  Icon(Icons.Default.Search, contentDescription = "Search", Modifier.weight(1.0F))
                Spacer(modifier = Modifier.weight(1.0F))

                  Icon(Icons.Default.Add, contentDescription = "Add", Modifier.weight(1.0F))
                Spacer(modifier = Modifier.weight(1.0F))

                  Icon(Icons.Default.AccountBox, contentDescription = "AccountBox", Modifier.weight(1.0F))

                Spacer(modifier = Modifier.weight(1.0F))


            }
        },

    ) { innerPadding ->

        ListEventCard(Modifier.padding(innerPadding).background(Color.White))

    }

}

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
    Column(
        modifier = modifier

            .padding(16.dp)

    ) {
        // Image from network
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = stringResource(id = R.string.app_name)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = title,
            color = Color.Black
//            style = MaterialTheme.typography.h5
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row {
            // Start time and end time
            Text(
                text = startTime,
                color = Color.Black
//                style = MaterialTheme.typography.subtitle1
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = endTime,
                color = Color.Black
//                style = MaterialTheme.typography.subtitle1
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Button with price
        Button(
            onClick = onButtonClick,
            modifier = Modifier
                .align(Alignment.Start)
        ) {
            Text(text = price)
        }
    }
}



@Composable
fun ListEventCard( modifier: Modifier = Modifier) {


    Column(modifier = modifier.verticalScroll(rememberScrollState())) {

        Row( modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
        ) {
            EventCard(imageUrl = "", title = "title", startTime ="15:30" , endTime = "18:50", price = "5000", onButtonClick = { /*TODO*/ },modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.width(16.dp))
            EventCard(imageUrl = "", title = "title", startTime ="15:30" , endTime = "18:50", price = "5000", onButtonClick = { /*TODO*/ },modifier = Modifier.weight(1f))

        }
        Row( modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)) {
            EventCard(imageUrl = "", title = "title", startTime ="15:30" , endTime = "18:50", price = "5000", onButtonClick = { /*TODO*/ },modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.width(16.dp))
            EventCard(imageUrl = "", title = "title", startTime ="15:30" , endTime = "18:50", price = "5000", onButtonClick = { /*TODO*/ },modifier = Modifier.weight(1f))

        }
        Row( modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)) {
            EventCard(imageUrl = "", title = "title", startTime ="15:30" , endTime = "18:50", price = "5000", onButtonClick = { /*TODO*/ },modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.width(16.dp))
            EventCard(imageUrl = "", title = "title", startTime ="15:30" , endTime = "18:50", price = "5000", onButtonClick = { /*TODO*/ },modifier = Modifier.weight(1f))

        }
        Row( modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)) {
            EventCard(imageUrl = "", title = "title", startTime ="15:30" , endTime = "18:50", price = "5000", onButtonClick = { /*TODO*/ },modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.width(16.dp))
            EventCard(imageUrl = "", title = "title", startTime ="15:30" , endTime = "18:50", price = "5000", onButtonClick = { /*TODO*/ },modifier = Modifier.weight(1f))

        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
        ListEventCard(Modifier)

}