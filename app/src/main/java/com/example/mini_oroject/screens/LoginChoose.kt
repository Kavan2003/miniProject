package com.example.mini_oroject.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MailOutline
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.mini_oroject.R
import com.example.mini_oroject.routes.Routes
import com.google.firebase.auth.FirebaseAuth

@Composable
fun LoginChoose(navController: NavHostController, auth: FirebaseAuth) {


    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.fillMaxHeight(fraction = 0.1f))
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
        ) {
            Image(
                painter = painterResource(id = R.mipmap.bidbuddy_foreground),
                contentDescription = "",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.fillMaxWidth(fraction = 0.5f)
            )
            Text(
                text = "Bid Buddy",
                fontSize = 30.sp,
                modifier = Modifier.padding(end = 30.dp),
                style = MaterialTheme.typography.displayLarge
            )
        }

        Spacer(modifier = Modifier.fillMaxHeight(fraction = 0.2f))

        Row {
            Spacer(modifier = Modifier.weight(0.1f))
            ElevatedCard(

                elevation = CardDefaults.cardElevation(
                    defaultElevation = 6.dp
                ),
                modifier = Modifier
                    .weight(1f)
            )

            {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                ) {
                    Image(
                        painter = painterResource(id = R.mipmap.facebook_foreground),
                        contentDescription = "",
                        modifier = Modifier
                            .size(60.dp)
                            .padding(vertical = 15.dp, horizontal = 10.dp)
                    )
                    Text(
                        text = "FaceBook",
                        modifier = Modifier,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.titleMedium,
                    )
                }
            }
            Spacer(modifier = Modifier.weight(0.3f))
            ElevatedCard(
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 6.dp
                ),
                modifier = Modifier
                    .weight(1f)
            )
            {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable(
                        enabled = true,
                        onClickLabel = "Google",
                        onClick = {

                            //Here Sign With google

                            
                        }
                    )
                )
                {
                    Image(
                        painter = painterResource(id = R.mipmap.google_foreground),
                        contentDescription = "",
                        modifier = Modifier
                            .size(60.dp)
                            .padding(vertical = 15.dp, horizontal = 10.dp)
                    )
                    Text(
                        text = "Google",
                        modifier = Modifier,
                        style = MaterialTheme.typography.titleMedium,

                        textAlign = TextAlign.Center,
                    )
                }
            }
            Spacer(modifier = Modifier.weight(0.1f))
        }
        Spacer(modifier = Modifier.fillMaxHeight(fraction = 0.1f))

        OutlinedCard(
            colors = CardDefaults.cardColors(
                containerColor = Color.Transparent,
            ),
            border = BorderStroke(1.dp, Color.Black),
            modifier = Modifier
                .fillMaxWidth(fraction = 0.8f)
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {

                Icon(
                    Icons.Outlined.MailOutline,
                    contentDescription = "Mail",
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.padding(top = 3.dp)
                )
                TextButton(onClick = {
                    navController.navigate(Routes.Login.rout)

                }) {
                    Text(
                        text = "Login With Email ?",
                        modifier = Modifier,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onPrimary,
                    )
                }

            }

        }
        Spacer(modifier = Modifier.fillMaxHeight(0.7f))
        Text(
            text = "Don't Have An Account ?",
            textDecoration = TextDecoration.None,

            style = MaterialTheme.typography.titleMedium
        )
        TextButton(onClick = {
            navController.navigate(Routes.Register.rout)

        }) {
            Text(
                text = "Register",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.headlineMedium,
                textDecoration = TextDecoration.Underline,
                fontSize = 20.sp
            )
        }


    }

}


