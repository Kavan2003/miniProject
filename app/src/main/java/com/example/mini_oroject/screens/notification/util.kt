package com.example.mini_oroject.screens.notification

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore

fun getLargestPaymentDetails(
    productId: String,
    onUserDetailsFetched: (UserData?) -> Unit
) {
    Log.w("klnjTAG", "getLargestPaymentDetails: ")
    val db = FirebaseFirestore.getInstance()

    var a = UserData("", "", "", "") // Initialize user data

    // Find payments for the product
    val paymentQuery = db.collection("user_payment")
        .whereEqualTo("product_reference", productId)

    paymentQuery.get().addOnCompleteListener { task ->
        if (task.isSuccessful) {
            val documents = task.result.documents
            if (documents.isNotEmpty()) {
                // Find the document with the largest payment
                val paymentDoc = documents.maxByOrNull { it.getDouble("pay_amt") ?: 0.0 }


                val paymentAmount = paymentDoc?.get("pay_amt").toString()
                Log.e("daqdc", "getLargestPaymentDetails: $paymentAmount")
                val payingUserId = paymentDoc?.getString("pay_by")
                Log.e("daqdc", "getLargestPaymentDetails: $payingUserId")


                // Find user details based on payingUserId
                val userQuery = db.collection("users_data").document(payingUserId!!)


                userQuery.get().addOnCompleteListener { innerTask ->

                    if (innerTask.isSuccessful) {
                        if (innerTask.result.exists()) {
                            val userDoc = innerTask.result
                            a.address = userDoc["Address"].toString()
                            a.name = userDoc.getString("Name")
                            a.email = userDoc.getString("Email")
                            a.phone = userDoc.getString("Phone")
                            onUserDetailsFetched(a) // Call the callback with user data

                        }
                    } else {
                        Log.w("TAG", "Error getting user details", innerTask.exception)
                        onUserDetailsFetched(null) // Call the callback with null for error
                    }
                }
            } else {
                onUserDetailsFetched(null) // Call the callback with null if no payments found
            }
        } else {
            Log.w("TAG", "Error getting payments", task.exception)
            onUserDetailsFetched(null) // Call the callback with null for error
        }
    }
}