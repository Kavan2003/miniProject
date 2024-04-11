package com.example.mini_oroject.screens.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.example.mini_oroject.routes.Routes
import com.example.mini_oroject.screens.bottombar.Bottombar
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun Profile(navController: NavHostController, auth: FirebaseAuth) {
    Scaffold(
        topBar = {
        },
        bottomBar = {
            Bottombar(navController)
        },
        content = {
            Column(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth()
                        .fillMaxHeight(0.2f)
                        .padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                ) {

                    Column(
                        modifier = Modifier.padding(
                            top = 25.dp,
                            bottom = 20.dp,
                            start = 20.dp,
                            end = 20.dp
                        ),
                        verticalArrangement = Arrangement.SpaceAround
                    ) {
                        Text(text = "Name " + auth.currentUser?.displayName)
                        Text(text = "Email " + auth.currentUser?.email)

                    }


                }


                ButtonProfile("Bid History", onButtonClick = {
                    navController.navigate(Routes.bid_his.rout)


                })
                ButtonProfile("Purchase History", onButtonClick = { })
                ButtonProfile("Logout", onButtonClick = {
                    auth.signOut()
                    navController.navigate(Routes.LoginChoose.rout)


                })


            }

        }
    )
}