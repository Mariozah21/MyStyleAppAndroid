package cz.mendelu.pef.mystyleapp.ui.screens

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
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
import cz.mendelu.pef.mystyleapp.ui.elements.BackArrowScreen
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
    BackArrowScreen(topBarTitle = "Add items", onBackClick = { navigation.navToMyItemsScreen() }) {
        AddItemScreenContent(navigation, viewModel)
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
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
                .wrapContentHeight(align = Alignment.CenterVertically)
                .aspectRatio(1f)
                .clickable { imagePickerLauncher.launch("image/*") }
                .border(
                    width = 2.dp,
                    color = Color.LightGray,
                    shape = MaterialTheme.shapes.medium,
                    // Add dashed line effect if needed
                    //borderStroke = BorderStroke(2.dp, Color.LightGray, PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f))
                ),
            contentAlignment = Alignment.Center
        ) {
            if (selectedImageUri.value != null) {
                // Image preview
                Image(
                    modifier = Modifier
                        .fillMaxSize(),
                    painter = rememberImagePainter(selectedImageUri.value),
                    contentDescription = "Image Preview",
                    contentScale = ContentScale.Fit
                )
            } else {
                // Text when no image is selected
                Text(
                    text = "Tap to upload image",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.LightGray
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = title.value,
            onValueChange = { title.value = it },
            label = { Text("Title") },
            modifier = Modifier.fillMaxWidth(),
            textStyle = MaterialTheme.typography.bodyLarge,
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = price.value,
            onValueChange = { input ->
                val filteredInput = input.filter { it.isDigit() || it == '.' }
                price.value = filteredInput
            },
            label = { Text("Price") },
            modifier = Modifier.fillMaxWidth(),
            textStyle = MaterialTheme.typography.bodyLarge,
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        CategoryDropdownMenu { selectedCategory ->
            setCategory(selectedCategory)
        }

        Spacer(modifier = Modifier.height(16.dp))

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
                    }
                )
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isUploading
        ) {
            if (isUploading) {
                CircularProgressIndicator(
                    color = Color.White,
                    strokeWidth = 4.dp,
                    modifier = Modifier
                        .size(24.dp)
                        .padding(end = 4.dp)
                )
            }
            Text(
                text = if (isUploading) "Uploading..." else "Upload Item",
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 14.sp
            )
        }
    }
}