package com.example.mini_oroject.routes

sealed class Routes(val rout: String) {
    object Home : Routes("home")
    object Login : Routes("login")
    object Register : Routes("register")
    object LoginChoose : Routes("loginChoose")
    object Profile : Routes("profile")
    object Event : Routes("event")
    object notification : Routes("notification")
    object CreatePost : Routes("CreatePost")
    object EventDetails : Routes("EventDetails")

    object Image : Routes("com.example.mini_oroject.screens.create.ImageUpload")
}