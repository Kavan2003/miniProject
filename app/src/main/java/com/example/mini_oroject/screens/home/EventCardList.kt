package com.example.mini_oroject.screens.home

// Import DraggableState and Orientation
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
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
    isAdmin: Boolean
) {
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
                isAdmin = isAdmin,
                onEventClick = {

                    var p = PostData(
                        id = event.id,
                        title = event.itemname,
                        selectedStartDate = event.startTime,
                        selectedEndDate = "$isAdmin",
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
