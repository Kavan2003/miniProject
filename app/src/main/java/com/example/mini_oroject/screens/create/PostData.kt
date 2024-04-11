package com.example.mini_oroject.screens.create

import android.util.Log
import androidx.compose.runtime.Composable
import com.google.gson.Gson

data class PostData(
    var id: String = "",
    var title: String,
    var priceshipping: String,
    var returnpolicy: String,
    var shippingpolicy: String,
    var condition: String,

    var selectedStartDate: String,
    var selectedEndDate: String,
    var price: String,
    var productDescription: String
)

fun postDataSerializer(postData: PostData): String {
    val gson = Gson()  // Remember the Gson instance
    val jsonData = gson.toJson(postData)
    return jsonData
}

@Composable
fun JsonToPostData(jsonString: String): PostData? {
    val gson = Gson()
    return try {
        gson.fromJson(jsonString, PostData::class.java)
    } catch (e: Exception) {
        Log.e("JsonToPostData page line 28", "JsonToPostData: $e")
        null // Handle potential parsing errors gracefully (optional)
    }
}
