package cz.mendelu.pef.mystyleapp.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import cz.mendelu.pef.mystyleapp.R
import cz.mendelu.pef.mystyleapp.navigation.INavigationRouter
import cz.mendelu.pef.mystyleapp.ui.elements.BottomNavigation
import java.util.UUID

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun AddItemScreen() {
    val title = remember { mutableStateOf("") }
    val price = remember { mutableStateOf("") }
    val category = remember { mutableStateOf("") }
    val selectedImageUri = remember { mutableStateOf<Uri?>(null) }

    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()
    val firestore = FirebaseFirestore.getInstance()
    val storage = FirebaseStorage.getInstance()

    val imagePickerLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        selectedImageUri.value = uri
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        TextField(
            value = title.value,
            onValueChange = { title.value = it },
            label = { Text("Title") }
        )
        Spacer(modifier = Modifier.padding(8.dp))
        TextField(
            value = price.value,
            onValueChange = { price.value = it },
            label = { Text("Price") }
        )
        Spacer(modifier = Modifier.padding(8.dp))
        TextField(
            value = category.value,
            onValueChange = { category.value = it },
            label = { Text("Category") }
        )
        Spacer(modifier = Modifier.padding(8.dp))
        Button(
            onClick = { imagePickerLauncher.launch("image/*") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Select Image")
        }
        Spacer(modifier = Modifier.padding(16.dp))
        Button(
            onClick = { uploadItem(auth.currentUser?.email ?: "", title.value, price.value, category.value, selectedImageUri.value) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Upload Item")
        }
    }
}

private fun uploadItem(email: String, title: String, price: String, category: String, imageUri: Uri?) {
    if (title.isEmpty() || price.isEmpty() || category.isEmpty() || imageUri == null) {
        // Handle validation error, e.g., show a Toast or display an error message
        return
    }

    // Upload image to Firebase Storage
    val storageRef = FirebaseStorage.getInstance().reference
    val imageRef = storageRef.child("images/${UUID.randomUUID()}")
    val uploadTask = imageRef.putFile(imageUri)

    uploadTask.continueWithTask { task ->
        if (!task.isSuccessful) {
            throw task.exception ?: RuntimeException("Failed to upload image.")
        }

        // Get the image URL from the uploaded file
        imageRef.downloadUrl
    }.addOnCompleteListener { task ->
        if (task.isSuccessful) {
            val imageUrl = task.result.toString()

            // Create item data
            val item = hashMapOf(
                "email" to email,
                "title" to title,
                "price" to price,
                "category" to category,
                "imageUrl" to imageUrl
            )

            // Save item data to Firestore
            FirebaseFirestore.getInstance().collection("Items")
                .add(item)
                .addOnSuccessListener {
                    // Item data saved successfully
                }
                .addOnFailureListener { e ->
                    // Error saving item data
                    // Handle the error appropriately (e.g., show a Toast)
                }
        } else {
            // Error uploading image
            // Handle the error appropriately (e.g., show a Toast)
        }
    }
}
