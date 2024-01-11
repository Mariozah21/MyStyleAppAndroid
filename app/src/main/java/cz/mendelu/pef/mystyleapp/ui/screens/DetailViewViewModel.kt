package cz.mendelu.pef.mystyleapp.ui.screens

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import cz.mendelu.pef.mystyleapp.data.Item
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.tasks.await


class DetailViewViewModel(
    id: String
) {
    val fetchedItemState: MutableState<Item?> = mutableStateOf(null)


    init {
        fetchItemById(id)
    }

    fun fetchItemById(itemId: String) {
        FirebaseFirestore.getInstance().collection("Items").document(itemId)
            .addSnapshotListener { documentSnapshot, error ->
                if (error != null) {
                    // Handle error
                    fetchedItemState.value = null
                    return@addSnapshotListener
                }

                val item = documentSnapshot?.toObject(Item::class.java)
                if (item != null) {
                    fetchedItemState.value = item // Update the fetched item state
                } else {
                    fetchedItemState.value = null // Set null if item is not found
                }
            }
    }
    suspend fun fetchUsernameByEmail(email: String): String? {
        return try {
            val usersCollection = Firebase.firestore.collection("users")

            val querySnapshot = usersCollection
                .whereEqualTo("email", email)
                .get()
                .await()

            if (!querySnapshot.isEmpty) {
                val userDocument = querySnapshot.documents[0]
                userDocument.getString("username")
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }
}