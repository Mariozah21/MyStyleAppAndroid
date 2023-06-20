package cz.mendelu.pef.mystyleapp.ui.screens

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
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
    val loadingState = mutableStateOf(false)
    val uploadingState = mutableStateOf(false)
    private val _itemsState = mutableStateListOf<Item>()
    val itemsState: List<Item> get() = _itemsState
    val currentUserEmail: String
        get() = FirebaseAuth.getInstance().currentUser?.email ?: ""
    val myItems: MutableState<List<Item>> = mutableStateOf(emptyList())
    init {
        fetchItems()
        fetchItemsByCurrentUserEmail()
    }

    private fun fetchItems() {
        FirebaseFirestore.getInstance().collection("Items")
            .get()
            .addOnSuccessListener { querySnapshot ->
                val items = querySnapshot.documents.mapNotNull { document ->
                    document.toObject(Item::class.java)
                }
                _itemsState.clear()
                _itemsState.addAll(items)
            }
            .addOnFailureListener { e ->
                // Handle error fetching items, e.g., show a Toast or display an error message
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
                        fetchItemsByCurrentUserEmail()
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

    private fun fetchItemsByCurrentUserEmail() {
        val currentUserEmail = currentUserEmail
        if (currentUserEmail.isNotEmpty()) {
            val itemsCollection = Firebase.firestore.collection("Items")

            itemsCollection
                .whereEqualTo("email", currentUserEmail)
                .get()
                .addOnSuccessListener { querySnapshot ->
                    val fetchedItems = querySnapshot.toObjects(Item::class.java)
                    myItems.value = fetchedItems
                }
                .addOnFailureListener { e ->
                    // Handle error fetching items
                }
        } else {
            // Handle case when current user email is empty
        }
    }



}