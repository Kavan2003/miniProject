package com.example.mini_oroject.screens.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.example.mini_oroject.routes.Routes
import com.example.mini_oroject.screens.bottombar.Bottombar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

data class UserData(
    val name: String = "",
    val email: String = "",
    val phone: String = "",
    val address: String = ""
)

private suspend fun fetchUserData(auth: FirebaseAuth): UserData? {
    val db = FirebaseFirestore.getInstance()
    val uid = auth.currentUser?.uid ?: return null

    val docRef = db.collection("users_data").document(uid)
    val snapshot = docRef.get().await()

    return if (snapshot.exists()) {
//        snapshot.toObject(UserData::class.java)
        UserData(
            snapshot.getString("Name") ?: "",
            snapshot.getString("Email") ?: "",
            snapshot.getString("Phone") ?: "",
            snapshot.getString("Address") ?: ""
        )
    } else {
        null
    }
}


@OptIn(ExperimentalGlideComposeApi::class)

@Composable
fun Profile(navController: NavHostController, auth: FirebaseAuth) {
    var userData by remember { mutableStateOf<UserData?>(null) }

    LaunchedEffect(Unit) {
        userData = fetchUserData(auth)
    }

    Scaffold(
        topBar = {},
        bottomBar = {
            Bottombar(navController)
        },
        content = {
            if (userData != null) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                            .fillMaxHeight(0.2f),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Text(text = "Profile Information")
                    }

//                    Table(
//                        columns = ContentColumn(
//                            listOf("Name", "Email", "Phone", "Address")
//                        )
//                    ) {
//                        TableRow {
//                            TableCell(Text(text = userData.name))
//                            TableCell(Text(text = userData.email))
//                            TableCell(Text(text = userData.phone))
//                            TableCell(Text(text = userData.address))
//                        }
//                    }
                    if (userData != null) {
                        Text(
                            "Name: ${userData?.name}",
                            modifier = Modifier.padding(10.dp),
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            "Email: ${userData?.email}",
                            modifier = Modifier.padding(10.dp),
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            "Phone: ${userData?.phone}",
                            modifier = Modifier.padding(10.dp),
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            "Address: ${userData?.address}",
                            modifier = Modifier.padding(10.dp),
                            style = MaterialTheme.typography.titleMedium
                        )


                        ButtonProfile("Bid History", onButtonClick = {
                            navController.navigate(Routes.bid_his.rout)
                        })
                        ButtonProfile("Logout", onButtonClick = {
                            auth.signOut()
                            navController.navigate(Routes.LoginChoose.rout)
                        })
                    } else {
                        CircularProgressIndicator()
                    }
                }
            }

        })
}
