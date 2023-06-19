package cz.mendelu.pef.mystyleapp.ui.screens

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import cz.mendelu.pef.mystyleapp.R
import cz.mendelu.pef.mystyleapp.navigation.INavigationRouter
import cz.mendelu.pef.mystyleapp.ui.components.Category
import cz.mendelu.pef.mystyleapp.ui.elements.BottomNavigation
import cz.mendelu.pef.mystyleapp.ui.elements.CategoryDropdownMenu
import org.koin.androidx.compose.getViewModel
import org.koin.androidx.compose.viewModel
import java.io.File
import java.util.UUID



@Composable
fun AddItemScreen(
    navigation: INavigationRouter,
    navController: NavController,
    viewModel: FirestoreViewModel = getViewModel(),
){
    BottomNavigation(navController = navController, topBarTitle = "Chat Screen") {
        AddItemScreenContent(navigation, viewModel)
    }
}
@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun AddItemScreenContent(
    navigation: INavigationRouter,
    viewModel: FirestoreViewModel
) {
    val title = remember { mutableStateOf("") }
    val price = remember { mutableStateOf("") }
    val (category, setCategory) = remember { mutableStateOf("") }
    val selectedImageUri = remember { mutableStateOf<Uri?>(null) }
    val auth = FirebaseAuth.getInstance()
    val (isUploading, setIsUploading) = remember { mutableStateOf(false) }

    val imagePickerLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        selectedImageUri.value = uri
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize(align = Alignment.Center)
                .aspectRatio(1f)
                .background(color = Color.LightGray), // Add a background color or custom styling for the box
            contentAlignment = Alignment.Center
        ) {
            // Image preview
            selectedImageUri.value?.let { uri ->
                Image(
                    modifier = Modifier
                        .fillMaxSize(),
                    painter = rememberImagePainter(uri),
                    contentDescription = "Image Preview",
                    contentScale = ContentScale.Fit
                )
            }
        }

        Spacer(modifier = Modifier.padding(8.dp))
        TextField(
            value = title.value,
            onValueChange = { title.value = it },
            label = { Text("Title") }
        )
        Spacer(modifier = Modifier.padding(8.dp))
        TextField(
            value = price.value,
            onValueChange = { input ->
                val filteredInput = input.filter { it.isDigit() || it == '.' }
                price.value = filteredInput
            },
            label = { Text("Price") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            visualTransformation = VisualTransformation.None
        )
        Spacer(modifier = Modifier.padding(8.dp))

        CategoryDropdownMenu { selectedCategory ->
            setCategory(selectedCategory)
        }
        Spacer(modifier = Modifier.padding(8.dp))
        Button(
            onClick = {
                imagePickerLauncher.launch("image/*")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Select Image")
        }
        Spacer(modifier = Modifier.padding(16.dp))
        Button(
            onClick = {
                setIsUploading(true)
                viewModel.uploadItem(
                    auth.currentUser?.email ?: "",
                    title.value,
                    price.value,
                    category,
                    selectedImageUri.value,
                    onSuccess = { // Success callback
                        setIsUploading(false)
                        navigation.navToMainScreen() // Navigate to the mainScreen
                    },
                    onFailure = { // Failure callback
                        setIsUploading(false)
                        // Inform the user about the failure, e.g., show a Toast or display an error message
                    })

            },
            modifier = Modifier.fillMaxWidth()
        ) {
            if (viewModel.uploadingState.value) {
                CircularProgressIndicator(
                    color = Color.White, // Set a custom color for the progress indicator
                    strokeWidth = 4.dp
                ) // Show the loading icon while uploading
            } else {
                Text("Upload Item")
            }
        }
    }
}
private fun uploadItem(
    email: String,
    title: String,
    price: String,
    category: String,
    imageUri: Uri?,
    onSuccess: () -> Unit, // Success callback function
    onFailure: () -> Unit
) {
    if (title.isEmpty() || price.isEmpty() || category.isEmpty() || imageUri == null) {
        // Handle validation error, e.g., show a Toast or display an error message

        return
    }

    val storageRef = FirebaseStorage.getInstance().reference
    val imageRef = storageRef.child("images/${UUID.randomUUID()}")

    val uploadTask = imageRef.putFile(imageUri)

    uploadTask.addOnSuccessListener { uploadTaskSnapshot ->
        imageRef.downloadUrl.addOnSuccessListener { downloadUrl ->
            val imageUrl = downloadUrl.toString()

            val item = hashMapOf(
                "email" to email,
                "title" to title,
                "price" to price,
                "category" to category,
                "imageUrl" to imageUrl
            )

            FirebaseFirestore.getInstance().collection("Items")
                .add(item)
                .addOnSuccessListener {
                    // Item data saved successfully
                    onSuccess()
                }
                .addOnFailureListener { e ->
                    // Error saving item data
                    onFailure()
                }
        }
    }.addOnFailureListener { e ->
        // Error uploading image

    }
}