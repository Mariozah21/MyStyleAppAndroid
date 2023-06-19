package cz.mendelu.pef.mystyleapp.ui.screens

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import cz.mendelu.pef.mystyleapp.architecture.BaseViewModel
import cz.mendelu.pef.mystyleapp.data.Item
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import org.koin.core.component.KoinComponent
import java.util.*

class FirestoreViewModel(
    private val context: Context
) : BaseViewModel(), KoinComponent {
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val itemsCollection: CollectionReference = firestore.collection("Items")
    private val storageRef = FirebaseStorage.getInstance().reference
    private val auth = FirebaseAuth.getInstance()

    val itemsState = mutableStateListOf<Item>()
    val loadingState = mutableStateOf(false)
    val uploadingState = mutableStateOf(false)

    fun fetchItems() {
        loadingState.value = true
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val snapshot = itemsCollection.get().await()
                val items = snapshot.documents.map { document ->
                    val imageUrl = document.getString("imageUrl") ?: ""
                    val title = document.getString("title") ?: ""
                    val price = document.getDouble("price") ?: 0.0
                    val userEmail = document.getString("email") ?: ""
                    Item(imageUrl, title, price.toString(), userEmail)
                }
                itemsState.addAll(items)
            } catch (e: Exception) {
                // Handle exception here
            } finally {
                loadingState.value = false
            }
        }
    }

    fun uploadItem(
        email: String,
        title: String,
        price: String,
        category: String,
        imageUri: Uri?,
        onSuccess: () -> Unit,
        onFailure: () -> Unit
    ) {
        if (title.isEmpty() || price.isEmpty() || category.isEmpty() || imageUri == null) {
            // Handle validation error, e.g., show a Toast or display an error message
            onFailure()
            return
        }

        setIsUploading(true)

        val imageRef = storageRef.child("images/${UUID.randomUUID()}")

        val uploadTask = imageRef.putFile(imageUri)

        uploadTask.addOnSuccessListener { uploadTaskSnapshot ->
            imageRef.downloadUrl.addOnSuccessListener { downloadUrl ->
                val imageUrl = downloadUrl.toString()

                val item = mutableMapOf<String, Any>(
                    "email" to email,
                    "title" to title,
                    "price" to price.toDouble(),
                    "category" to category,
                    "imageUrl" to imageUrl
                )

                itemsCollection.add(item)
                    .addOnSuccessListener {
                        // Item data saved successfully
                        setIsUploading(false)
                        onSuccess()
                    }
                    .addOnFailureListener { e ->
                        // Error saving item data
                        setIsUploading(false)
                        onFailure()
                    }
            }
        }.addOnFailureListener { e ->
            // Error uploading image
            setIsUploading(false)
            onFailure()
        }
    }

    private fun setIsUploading(isUploading: Boolean) {
        uploadingState.value = isUploading
    }
}