package com.example.mini_oroject.screens.bottombar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.mini_oroject.routes.Routes


@Composable
fun Bottombar(navController: NavHostController) {
    BottomAppBar(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
    ) {


        Spacer(modifier = Modifier.weight(1.0F))

        Icon(
            Icons.Default.Home,
            contentDescription = "Home",
            Modifier
                .weight(1.0F)
                .clickable {
                    navController.navigate(Routes.Home.rout)


                })
        Spacer(modifier = Modifier.weight(1.0F))
        Icon(
            Icons.Default.Notifications, contentDescription = "Search",
            Modifier
                .weight(1.0F)
                .clickable {
                    navController.navigate(Routes.notification.rout)

                })
        Spacer(modifier = Modifier.weight(1.0F))

        Icon(
            Icons.Default.Add, contentDescription = "Add",
            Modifier
                .weight(1.0F)
                .clickable {
                    navController.navigate(Routes.Event.rout)
                })
        Spacer(modifier = Modifier.weight(1.0F))

        Icon(
            Icons.Default.AccountBox, contentDescription = "AccountBox",
            Modifier
                .weight(1.0F)
                .clickable {
                    navController.navigate(Routes.Profile.rout)

                })

        Spacer(modifier = Modifier.weight(1.0F))


    }
}