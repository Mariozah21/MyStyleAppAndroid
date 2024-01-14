package cz.mendelu.pef.mystyleapp.ui.screens.mycart

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import cz.mendelu.pef.mystyleapp.R
import cz.mendelu.pef.mystyleapp.cartDatabase.model.CartItem
import cz.mendelu.pef.mystyleapp.packetaApi.model.BranchesResponse
import cz.mendelu.pef.mystyleapp.packetaApi.model.PointItem
import cz.mendelu.pef.mystyleapp.ui.elements.MyScaffold
import cz.mendelu.pef.mystyleapp.ui.screens.destinations.OrderSuccessScreenDestination
import cz.mendelu.pef.mystyleapp.ui.screens.destinations.PacketaMapScreenDestination
import cz.mendelu.pef.mystyleapp.ui.screens.mapscreen.MarkerClusteringErrors
import cz.mendelu.pef.mystyleapp.ui.screens.mapscreen.PacketaMapViewModel
import cz.mendelu.pef.mystyleapp.ui.screens.mapscreen.UiState
import org.koin.androidx.compose.getViewModel

@Destination
@Composable
fun MyCartScreen(
    navigator: DestinationsNavigator,
    viewModel: CartViewModel = getViewModel(),
    mapViewModel: PacketaMapViewModel = getViewModel(),
) {
    val cartItems = remember {
        mutableStateListOf<CartItem>()
    }
    val uiState: MutableState<UiState<BranchesResponse, MarkerClusteringErrors>> =
        rememberSaveable { mutableStateOf(UiState()) }

    mapViewModel.placesUIState.value.let {
        uiState.value = it
    }


    viewModel.cartItems.value.let {
        when (it) {
            CartItemsUIState.Default -> {
                viewModel.loadItems()
            }

            is CartItemsUIState.Success -> {
                cartItems.clear()
                cartItems.addAll(it.cartItems)
            }
        }
    }
    MyScaffold(
        topBarTitle = "My Cart",
        navigator = navigator,
        showBackArrow = true,
        onBackClick = { navigator.popBackStack() }) {
        MyCartScreenContent(navigator, cartItems, viewModel, mapViewModel, uiState.value.data)
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyCartScreenContent(
    navigator: DestinationsNavigator,
    cartItems: MutableList<CartItem>,
    viewModel: CartViewModel,
    mapViewModel: PacketaMapViewModel,
    mapItems: BranchesResponse?,
) {

    var selectedShippingOption by rememberSaveable { mutableStateOf<ShippingOption?>(ShippingOption.Address) }
    var shippingPrice by remember { mutableStateOf(0.0) }
    var totalCost by remember { mutableStateOf(cartItems.sumOf { it.price }) }
    //Forms for shipping
    val name = remember { mutableStateOf("") }
    val surname = remember { mutableStateOf("") }
    val streetName = remember { mutableStateOf("") }
    val number = remember { mutableStateOf("") }
    val postalCode = remember { mutableStateOf("") }
    val city = remember { mutableStateOf("") }
    //context
    val context = LocalContext.current
    var searchText by remember { mutableStateOf("") }
    var suggestions by remember { mutableStateOf<List<PointItem>>(emptyList()) }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Scrollable content
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp) // Add padding to the LazyColumn
        ) {
            // Header for "Products in Cart" section
            item {
                Text(
                    text = stringResource(R.string.products_in_cart),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            }

            if (cartItems.isEmpty()) {
                // Display default text if cart is empty
                item {
                    Text(
                        text = stringResource(R.string.no_items_in_cart),
                        modifier = Modifier
                            .fillMaxSize()
                            .wrapContentSize(Alignment.Center),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            } else {
                // "Products in Cart" section

                items(cartItems) { cartItem ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp), // Add some spacing between rows
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Box(
                            modifier = Modifier
                                .size(80.dp)
                                .clip(CircleShape)
                        ) {
                            Image(
                                painter = rememberImagePainter(cartItem.imageUrl),
                                contentDescription = "Item Image",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop,
                            )
                        }
                        Column {
                            Text(text = cartItem.title)
                            Text("Size:" + cartItem.size)
                        }
                        Text(text = "Price: €${cartItem.price}")
                        IconButton(
                            onClick = {
                                viewModel.removeFromCart(cartItem)
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Remove From Cart"
                            )
                        }
                    }
                }

                // Spacer to create space between sections
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
            // "Shipping Info" section
            item {
                Text(
                    text = "Shipping Info",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            }

            // Rows for shipping information
            item {
                ShippingOptionRow(
                    text = "Delivery to address",
                    checked = selectedShippingOption == ShippingOption.Address,
                    onCheckedChange = {
                        if (it) {
                            selectedShippingOption = ShippingOption.Address
                        }
                    }
                )
            }
            item {
                ShippingOptionRow(
                    text = "Delivery to Pickup Box",
                    checked = selectedShippingOption == ShippingOption.PickupBox,
                    onCheckedChange = {
                        if (it) {
                            selectedShippingOption = ShippingOption.PickupBox
                        }
                    }
                )
            }
            // Additional content based on the selected shipping option
            when (selectedShippingOption) {
                ShippingOption.Address -> {
                    // Display content for standard shipping option
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            TextField(
                                value = name.value, // Provide the actual value from your data or use a mutableState
                                onValueChange = { name.value = it },
                                label = { Text("Name") }
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            TextField(
                                value = surname.value, // Provide the actual value from your data or use a mutableState
                                onValueChange = { surname.value = it },
                                label = { Text("Surname") }
                            )
                            Spacer(modifier = Modifier.height(8.dp))

                                TextField(
                                    value = streetName.value, // Provide the actual value from your data or use a mutableState
                                    onValueChange = { streetName.value = it },
                                    label = { Text("Street name") }
                                )
                                Spacer(modifier = Modifier.height(8.dp))

                                TextField(
                                    value = number.value, // Provide the actual value from your data or use a mutableState
                                    onValueChange = { input ->
                                        val filteredInput = input.filter { char ->
                                            char.isDigit() || char == '/'
                                        }
                                        val isValidFormat = filteredInput.matches(Regex("^[0-9]+(/?[a-zA-Z]?)?$"))
                                        if (isValidFormat) {
                                            number.value = filteredInput
                                        } else {
                                            // Handle invalid format
                                        }},
                                    label = { Text("Orientation Number") }
                                )


                            Spacer(modifier = Modifier.height(8.dp))

                                TextField(
                                    value = city.value, // Provide the actual value from your data or use a mutableState
                                    onValueChange = { city.value = it },
                                    label = { Text("City") }
                                )
                                Spacer(modifier = Modifier.height(8.dp))

                                TextField(
                                    value = postalCode.value, // Provide the actual value from your data or use a mutableState
                                    onValueChange = {
                                            input ->
                                        val filteredInput = input.filter { it.isDigit() }
                                        if (filteredInput.length < 6) {
                                            postalCode.value = filteredInput
                                        }else{
                                            Toast.makeText(context, "Wrong format", Toast.LENGTH_SHORT).show()
                                        }},
                                    label = { Text("Postal Code") }
                                )



                            Spacer(modifier = Modifier.height(8.dp))
                            // Address fields
                            // Add more text fields for street, orientation number, city, postal code as needed
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                // Additional info about delivery
                                Text("Shipping Price:", fontWeight = FontWeight.Bold)
                                Text("€5.00", textAlign = TextAlign.End)
                                shippingPrice = 5.00
                            }
                        }
                    }
                }

                ShippingOption.PickupBox -> {
                    // Display content for express shipping option
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            // TextField for typing the address or postal code
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                TextField(
                                    value = searchText, // Provide the actual value from your data or use a mutableState
                                    onValueChange = {
                                        searchText = it
                                        if(mapItems != null){
                                            suggestions = getMatchingItems(mapItems, it)
                                        } else{

                                        }

                                    },
                                    label = { Text("Enter Address or Postal Code") },
                                    modifier = Modifier.weight(1f),
                                            keyboardOptions = KeyboardOptions.Default.copy(
                                            imeAction = ImeAction.Done
                                            )
                                )
                                /* TODO - I need to show suggestions here*/


                                // Spacer for some separation
                                Spacer(modifier = Modifier.width(8.dp))

                                // Icon at the end
                                IconButton(
                                    onClick = { navigator.navigate(PacketaMapScreenDestination)  },
                                    modifier = Modifier.size(48.dp)
                                ) {
                                    // Replace the placeholder icon with your desired icon
                                    Icon(
                                        imageVector = Icons.Default.LocationOn,
                                        contentDescription = "Location Icon"
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                // Additional info about delivery
                                Text("Shipping Price:", fontWeight = FontWeight.Bold)
                                Text("€3.00", textAlign = TextAlign.End)
                                shippingPrice = 3.00
                            }

                            // Additional content for express shipping option if needed
                        }
                    }
                }

                else -> {
                    // Display default content or handle other cases
                    item {
                        Text("Select a shipping option")
                    }
                }
            }
            item {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        // Additional info about delivery
                        Text("Total cost of order:", fontWeight = FontWeight.Bold)

                        totalCost = cartItems.sumOf { it.price } + shippingPrice
                        Text("€${totalCost}", textAlign = TextAlign.End)
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    Button(
                        onClick = {
                            if (selectedShippingOption == ShippingOption.Address) {
                                if(
                                    name.value.isNotEmpty() &&
                                    surname.value.isNotEmpty() &&
                                    streetName.value.isNotEmpty() &&
                                    number.value.isNotEmpty() &&
                                    city.value.isNotEmpty() &&
                                    postalCode.value.isNotEmpty()
                                    ){
                                    /*TODO - animation of loading, send request (redirect to success screen)*/
                                    navigator.navigate(OrderSuccessScreenDestination(totalCost,streetName.toString(),number.toString(),city.toString(), ""))
                                } else {
                                    Toast.makeText(context, "Please fill out all the fields", Toast.LENGTH_SHORT).show()
                                }
                            } else {
                                if(searchText.isNotEmpty()){
                                    navigator.navigate(OrderSuccessScreenDestination(totalCost,"","","", searchText.toString()))

                                } else {
                                    Toast.makeText(context, "Please fill out all the fields", Toast.LENGTH_SHORT).show()
                                }
                                /*TODO - check if all fields that are displayed for this option are filled out*/

                            }
                        },
                        enabled = cartItems.isNotEmpty(),
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text(text = "Proceed to checkout")
                    }
                }
            }
        }
    }

}
private fun getMatchingItems(mapItems: BranchesResponse, query: String): List<PointItem> {
    return mapItems.data.values.filter { item ->
        item.place.contains(query, ignoreCase = true) ||
                item.street.contains(query, ignoreCase = true) ||
                item.zip.contains(query, ignoreCase = true)
    }
}

private fun PointItem.getFullAddress(): String {
    return "$place, $street, $city, $zip"
}


@Composable
fun ShippingOptionRow(
    text: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween // Align to the end
    ) {
        Text(text = text)
        Spacer(modifier = Modifier.width(16.dp))
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange,
            modifier = Modifier.size(30.dp) // Adjust the size as needed
        )
    }
}

enum class ShippingOption {
    Address,
    PickupBox
}


