package com.example.mini_oroject.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mini_oroject.sampledata.Event


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
