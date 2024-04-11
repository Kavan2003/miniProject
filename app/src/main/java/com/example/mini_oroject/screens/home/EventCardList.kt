package com.example.mini_oroject.screens.home

// Import DraggableState and Orientation
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.mini_oroject.data_model.Event
import com.example.mini_oroject.routes.Routes
import com.example.mini_oroject.screens.create.PostData
import com.example.mini_oroject.screens.create.postDataSerializer
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun ListEventCard(
    modifier: Modifier = Modifier,
    events: List<Event>,
    navController: NavHostController,
) {
    val offsetX = remember { mutableStateOf(0f) }
    val maxOffsetX = 200f // Threshold for swipe distance

    val currentIndex = remember { mutableStateOf(0) } // Track current item index



    LazyColumn(
        modifier = modifier
            .background(MaterialTheme.colorScheme.secondary)
            .padding(10.dp)
    ) {
        items(events) { event ->
            EventReelCard(
                navController = navController,
                event = event,
                modifier = Modifier,
                onEventClick = {

                    var p = PostData(
                        id = event.id,
                        title = event.itemname,
                        selectedStartDate = event.startTime,
                        selectedEndDate = "",
                        price = event.initialPrice.toString(),
                        productDescription = URLEncoder.encode(
                            event.description,
                            StandardCharsets.UTF_8.toString()
                        ),
                        priceshipping = event.priceshipping,
                        returnpolicy = event.returnpolicy,
                        shippingpolicy = event.shippingpolicy,
                        condition = event.condition
                    )
                    //convert to string
                    val data = postDataSerializer(p)

                    navController.navigate(Routes.EventDetails.rout + "/$data")
                }
            )
        }
    }
}
