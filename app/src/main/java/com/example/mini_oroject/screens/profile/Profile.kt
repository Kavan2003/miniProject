package com.example.mini_oroject.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideSubcomposition
import com.bumptech.glide.integration.compose.RequestState
import com.example.mini_oroject.R
import com.example.mini_oroject.screens.bottombar.Bottombar
import com.example.mini_oroject.ui.theme.LightGrey
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
                    Box(
                        Modifier,
                        contentAlignment = Alignment.BottomEnd
                    ) {
                        GlideSubcomposition(
                            model = auth.currentUser?.photoUrl,
                            modifier = Modifier

                                .wrapContentSize(Alignment.Center)
//                                .clip(RoundedCornerShape(50.dp))


                        ) {
                            when (state) {
                                RequestState.Failure -> Image(
                                    painter = painterResource(id = R.mipmap.emptyprofile_foreground),
                                    contentDescription = null,
                                    modifier = Modifier

                                        .wrapContentSize(Alignment.Center)

                                )

                                RequestState.Loading -> CircularProgressIndicator(
                                    color = LightGrey,
                                    modifier = Modifier

                                        .wrapContentSize(Alignment.Center)
                                )

                                is RequestState.Success -> Image(
                                    painter, contentDescription = null, Modifier,
                                )
                            }
                        }
                        Icon(
                            Icons.Filled.Edit,
                            tint = Color.White,
                            contentDescription = "Check mark",
                            modifier = Modifier
                                .background(
//                                    bdbdbd
                                    Color(0xFFBDBDBD)
//                                    MaterialTheme.colorScheme.background
                                )
                                .clip(RoundedCornerShape(50.dp))
//                                .border(
//                                    3.dp,
//                                    MaterialTheme.colorScheme.background,
//                                    shape = RoundedCornerShape(50.dp)
//                                )

                        )
                    }
                    Column(
                        modifier = Modifier.padding(
                            top = 25.dp,
                            bottom = 20.dp,
                            start = 20.dp,
                            end = 20.dp
                        ),
                        verticalArrangement = Arrangement.SpaceAround
                    ) {
                        Text(text = "Name" + auth.currentUser?.displayName)
                        Text(text = "Email" + auth.currentUser?.email)

                    }


                }

                // online auctuon system profile page
                //setings button , logout button, Bid History button, Purchase History button
                ButtonProfile("Settings", onButtonClick = { })

                ButtonProfile("Bid History", onButtonClick = { })
                ButtonProfile("Purchase History", onButtonClick = { })
                ButtonProfile("Logout", onButtonClick = { })


            }

        }
    )
}