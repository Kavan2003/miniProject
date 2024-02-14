package com.example.mini_oroject.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideSubcomposition
import com.bumptech.glide.integration.compose.RequestState
import com.example.mini_oroject.R
import com.example.mini_oroject.ui.theme.LightGrey

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
                        contentDescription = null,

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

