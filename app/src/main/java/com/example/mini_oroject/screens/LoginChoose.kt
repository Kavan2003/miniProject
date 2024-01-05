package com.example.mini_oroject.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import com.example.mini_oroject.R

@Composable
fun LoginChoose(navController: NavHostController){

    Column {

        Row {
            Image(painter =  painterResource(id = R.mipmap.ic_launcher), contentDescription ="" )
            Text(text = "Welcome to Mini Project")
        }

    }

}