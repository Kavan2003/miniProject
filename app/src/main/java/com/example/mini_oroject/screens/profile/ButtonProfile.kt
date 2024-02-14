package com.example.mini_oroject.screens.profile

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ButtonProfile(item: String, onButtonClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onButtonClick() }
            .padding(10.dp)
            .border(
                1.dp,
                MaterialTheme.colorScheme.onPrimaryContainer,
                shape = RoundedCornerShape(25.dp)
            )
            .padding(25.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = item)
    }
}