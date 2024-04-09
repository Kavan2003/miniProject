package com.example.mini_oroject.screens.home

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideSubcomposition
import com.bumptech.glide.integration.compose.RequestState
import com.example.mini_oroject.R
import com.example.mini_oroject.data_model.Event
import com.example.mini_oroject.ui.theme.LightGrey
import com.meetup.twain.MarkdownText

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun EventReelCard(
    event: Event,
    onEventClick: () -> Unit,
    navController: NavHostController? = null, // for optional navigation
    modifier: Modifier = Modifier.fillMaxHeight()
) {


    Card(
        modifier = Modifier
            .clickable { onEventClick() },
        colors = CardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface,
            disabledContainerColor = Color.Gray,
            disabledContentColor = Color.Gray
        ),

        shape = RoundedCornerShape(16.dp),
    ) {
        Column(
            modifier = Modifier
                .padding(5.dp)
                .border(1.dp, MaterialTheme.colorScheme.onSecondary, RoundedCornerShape(16.dp))
                .padding(10.dp),
        ) {
            // Image section
            GlideSubcomposition(
                modifier = Modifier

                    .height(300.dp)
                    .align(Alignment.CenterHorizontally),
                model = event.imageUrl,
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
                            .fillMaxHeight(0.4f),
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


// Title, Time and Description section
            Text(
                text = event.itemname,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Log.d("timedate", "EventReelCard: ${event.startTime} - ${event.endTime}")
            Text(
                text = "${event.startTime}",
                fontSize = 16.sp,
                color = Color.Gray
            )
//            Text(
//                text = ,
//                maxLines = 2,
//                overflow = TextOverflow.Ellipsis,
//                fontSize = 14.sp,
//                color = MaterialTheme.colorScheme.onSurface
//
//            )
            MarkdownText(
                markdown = event.description.substring(0, event.description.length / 5),
                modifier = Modifier.fillMaxWidth(),
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurface


            )

// Information bar with price and optional button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                if (navController != null) {
                    OutlinedButton(
                        onClick = onEventClick,
                        colors = buttonColors(contentColor = MaterialTheme.colorScheme.inverseSurface),
                        modifier = Modifier.padding(start = 8.dp)
                    ) {
                        Text(text = "Place Bid")
                    }
                }
            }
        }
    }
}
