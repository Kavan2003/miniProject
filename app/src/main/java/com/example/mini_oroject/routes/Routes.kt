package com.example.mini_oroject.routes

sealed class Routes (val rout: String) {
object Home: Routes("home")
object Login: Routes("login")
object Register: Routes("register")

}