import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

fun finalCheckUpload(datahash: HashMap<String, Any?>, price: String, auth: FirebaseAuth) {

    val firestoreDb = FirebaseFirestore.getInstance()
    val firestoreCollectionRef = firestoreDb.collection("auction_item")

    val realtimeDb =
        FirebaseDatabase.getInstance().reference.child("auction_items") // Reference to Realtime Database

    val uploadScope = CoroutineScope(Dispatchers.IO)
    uploadScope.launch {
        try {
            // Wait for all images to upload first

            firestoreCollectionRef.add(datahash)
                .addOnSuccessListener { documentReference ->
                    val documentId = documentReference.id
                    val priceData = HashMap<String, Any?>()
                    priceData["id"] = documentId
                    priceData["price"] =
                        price.toDouble()  // Assuming price is a String, convert to Double

                    // Write data to Realtime Database
                    realtimeDb.child(documentId).setValue(priceData)
                        .addOnSuccessListener {
                            Log.d("RealtimeDB", "Data saved for document: $documentId")
                        }
                        .addOnFailureListener { e ->
                            Log.w("RealtimeDB", "Error saving data to Realtime Database", e)
                        }

                    Log.d("Firestore", "Document written with ID: ${documentReference.id}")
                }
                .addOnFailureListener { e ->
                    Log.w("Firestore", "Error adding document", e)
                }
        } catch (e: Exception) {
            Log.e("Firestore", "Error uploading data:", e)
        }
    }.invokeOnCompletion {
        datahash.forEach { (key, value) ->
            Log.d("Tags for  final", "$key: $value")
        }
        return@invokeOnCompletion
    }


}
