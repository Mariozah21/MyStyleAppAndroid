package cz.mendelu.pef.mystyleapp.ui.screens

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.google.firebase.auth.FirebaseAuth
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import cz.mendelu.pef.mystyleapp.R
import cz.mendelu.pef.mystyleapp.navigation.INavigationRouter
import cz.mendelu.pef.mystyleapp.ui.components.Constants
import cz.mendelu.pef.mystyleapp.ui.elements.BackArrowScreen
import cz.mendelu.pef.mystyleapp.ui.elements.MyScaffold
import cz.mendelu.pef.mystyleapp.ui.screens.destinations.MyItemsScreenDestination
import org.koin.androidx.compose.getViewModel





@Destination
@Composable
fun AddItemScreen(
    navigator: DestinationsNavigator,
    viewModel: FirestoreViewModel = getViewModel(),
){
    MyScaffold(topBarTitle = stringResource(R.string.add_items_screen_title), navigator = navigator, showBackArrow = true, onBackClick = { navigator.navigate(MyItemsScreenDestination) }) {
        AddItemScreenContent(navigator, viewModel)

    }
    /*
    BackArrowScreen(topBarTitle = stringResource(R.string.add_items_screen_title), onBackClick = { navigator.navigate(MyItemsScreenDestination) }) {
        AddItemScreenContent(navigator, viewModel)
    }

     */
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddItemScreenContent(
    navigator: DestinationsNavigator,
    viewModel: FirestoreViewModel
) {
    val title = remember { mutableStateOf("") }
    val price = remember { mutableStateOf("") }
    val selectedImageUri = remember { mutableStateOf<Uri?>(null) }
    val auth = FirebaseAuth.getInstance()
    val (isUploading, setIsUploading) = remember { mutableStateOf(false) }
    val description = remember { mutableStateOf("") }
    val stockCount = remember { mutableStateOf("") }
    //Color selector
    val colors = Constants.colors
    val selectedColor = remember { mutableStateOf("") }
    val isColorDropdownExpanded = remember { mutableStateOf(false) }
    //SizeSelector
    val sizes = Constants.sizes
    val selectedSize = remember { mutableStateOf("") }
    val isSizeDropdownExpanded = remember { mutableStateOf(false) }
    //CategorySelector
    val categories = Constants.categories
    val selectedCategory = remember { mutableStateOf("") }
    val isCategoryDropDownExpanded = remember { mutableStateOf(false) }

    val imagePickerLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        selectedImageUri.value = uri
    }
    val context = LocalContext.current

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
                    text = stringResource(R.string.add_item_tap_to_upload_image),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.LightGray
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        if (title.value.length>45) {
            Snackbar {
                Text(stringResource(R.string.add_item_text_is_too_long))
            }
        }
        TextField(
            value = title.value,
            onValueChange = { if (it.length <= 45) title.value = it },
            label = { Text(stringResource(R.string.add_item_title)) },
            modifier = Modifier.fillMaxWidth(),
            textStyle = MaterialTheme.typography.bodyLarge,
            colors = TextFieldDefaults.textFieldColors(
                textColor = MaterialTheme.colorScheme.onBackground,
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done,
            ),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = price.value,
            onValueChange = { input ->
                val filteredInput = input.filter { it.isDigit() || it == '.' }
                val decimalIndex = filteredInput.indexOf('.')
                if (decimalIndex != -1 && filteredInput.length - decimalIndex > 3) {
                    // Limit the number of digits after the decimal point to 2
                    price.value = filteredInput.substring(0, decimalIndex + 3)
                } else {
                    price.value = filteredInput
                }
            },
            label = { Text(stringResource(R.string.add_item_price)) },
            modifier = Modifier.fillMaxWidth(),
            textStyle = MaterialTheme.typography.bodyLarge,
            colors = TextFieldDefaults.textFieldColors(
                textColor = MaterialTheme.colorScheme.onBackground,
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
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

        //Category dropdown
            //DropDown box
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize(Alignment.Center)
                    .background(MaterialTheme.colorScheme.secondaryContainer)
                    .clickable { isCategoryDropDownExpanded.value = true }
            ) {
                    DropdownMenu(
                        expanded = isCategoryDropDownExpanded.value,
                        onDismissRequest = { isCategoryDropDownExpanded.value = false },
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.secondaryContainer)
                            .fillMaxWidth()
                    ) {
                        categories.forEach { categori ->
                            DropdownMenuItem(
                                text = { Text(text = categori)},
                                onClick = {
                                    selectedCategory.value = categori
                                    isCategoryDropDownExpanded.value = false
                                }
                            )
                        }
                    }
                if (selectedSize.value.isNotEmpty()) {
                    Text(
                        text = stringResource(R.string.add_item_category) + selectedCategory.value,
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),

                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }else{
                    Text(
                        text = stringResource(R.string.add_item_tap_to_select_category),
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
        }
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = description.value,
            onValueChange = { newValue ->
                if (newValue.length <= 300) {
                    description.value = newValue
                }
            },
            label = { Text(stringResource(R.string.add_item_description)) },
            modifier = Modifier.fillMaxWidth(),
            textStyle = MaterialTheme.typography.bodyLarge,
            colors = TextFieldDefaults.textFieldColors(
                textColor = MaterialTheme.colorScheme.onBackground,
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            singleLine = true,
        )
        Spacer(modifier = Modifier.height(8.dp))
        //Color Dropdown
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize(Alignment.Center)
                .background(MaterialTheme.colorScheme.secondaryContainer)
                .clickable { isColorDropdownExpanded.value = true }
        ) {
                    DropdownMenu(
                        expanded = isColorDropdownExpanded.value,
                        onDismissRequest = { isColorDropdownExpanded.value = false } ,
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.secondaryContainer)
                            .fillMaxWidth()
                    ) {
                        colors.forEach { color ->
                            DropdownMenuItem(
                                text = { Text(text = color) },
                                onClick = {
                                    selectedColor.value = color
                                    isColorDropdownExpanded.value = false
                                }
                            )
                        }
                    }

            if (selectedColor.value.isNotEmpty()) {
                Text(
                    text = stringResource(R.string.add_item_color) + selectedColor.value,
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }else{
                Text(
                    text = stringResource(R.string.add_item_tap_to_select_color),
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        //DropDown box
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize(Alignment.Center)
                .background(MaterialTheme.colorScheme.secondaryContainer)
                .clickable { isSizeDropdownExpanded.value = true }
        ) {
                DropdownMenu(
                    expanded = isSizeDropdownExpanded.value,
                    onDismissRequest = { isSizeDropdownExpanded.value = false },
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.secondaryContainer)
                        .fillMaxWidth()
                ) {
                    sizes.forEach { size ->
                        DropdownMenuItem(
                            text = { Text(text = size)},
                            onClick = {
                                selectedSize.value = size
                                isSizeDropdownExpanded.value = false
                            }
                        )
                    }
                }
            if (selectedSize.value.isNotEmpty()) {
                Text(
                    text = stringResource(R.string.add_item_size) + selectedSize.value,
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }else{
                Text(
                    text = stringResource(R.string.add_item_tap_to_select_size),
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
        //end of dropdown box
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                setIsUploading(true)
                viewModel.uploadItem(
                    email = auth.currentUser?.email ?: "",
                    title = title.value,
                    price = price.value,
                    category = selectedCategory.value,
                    description = description.value.takeIf { it.isNotBlank() },
                    stockCount = stockCount.value.takeIf { it.isNotBlank() }?.toIntOrNull(),
                    color = selectedColor.value,
                    size = selectedSize.value,
                    imageUri = selectedImageUri.value,
                    onSuccess = {
                        Toast.makeText(context,"Item has been uploaded successfully", Toast.LENGTH_SHORT).show()
                        setIsUploading(false)
                        navigator.navigate(MyItemsScreenDestination)
                    },
                    onFailure = {
                        Toast.makeText(context,"Please fill out all the Fields", Toast.LENGTH_SHORT).show()
                        setIsUploading(false)

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
                text = if (isUploading) stringResource(R.string.add_item_uploading) else stringResource(
                                    R.string.add_item_upload_item),
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 14.sp
            )
        }
    }
}

