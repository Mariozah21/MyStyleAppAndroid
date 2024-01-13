package cz.mendelu.pef.mystyleapp.ui.screens.mycart

import android.content.Context
import android.location.Address
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import cz.mendelu.pef.mystyleapp.R
import cz.mendelu.pef.mystyleapp.data.CartItem
import cz.mendelu.pef.mystyleapp.ui.elements.BottomNavigation
import cz.mendelu.pef.mystyleapp.ui.elements.MyItemCard
import org.koin.androidx.compose.getViewModel

@Destination
@Composable
fun MyCartScreen(
    navigator: DestinationsNavigator,
    viewModel: CartViewModel = getViewModel(),
){
    var cartItems = remember {
        mutableStateListOf<CartItem>()
    }

    viewModel.cartItems.value.let {
        when(it){
            CartItemsUIState.Default -> {
                viewModel.loadItems()
            }
            is CartItemsUIState.Success -> {
                cartItems.clear()
                cartItems.addAll(it.cartItems)
            }
        }
    }
    BottomNavigation(false,navigator, topBarTitle = "My Cart") {
        MyCartScreenContent(navigator,cartItems,viewModel)
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyCartScreenContent(
    navigator: DestinationsNavigator,
    cartItems: MutableList<CartItem>,
    viewModel: CartViewModel,
) {
    var selectedShippingOption by rememberSaveable { mutableStateOf<ShippingOption?>(ShippingOption.Address) }

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
                                value = "", // Provide the actual value from your data or use a mutableState
                                onValueChange = { /* Handle value change */ },
                                label = { Text("Name") }
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            TextField(
                                value = "", // Provide the actual value from your data or use a mutableState
                                onValueChange = { /* Handle value change */ },
                                label = { Text("Surname") }
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
                                    value = "", // Provide the actual value from your data or use a mutableState
                                    onValueChange = { /* Handle value change */ },
                                    label = { Text("Enter Address or Postal Code") },
                                    modifier = Modifier.weight(1f)
                                )

                                // Spacer for some separation
                                Spacer(modifier = Modifier.width(8.dp))

                                // Icon at the end
                                IconButton(
                                    onClick = { /* Handle icon click */ },
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
                        Text("€", textAlign = TextAlign.End)
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    Button(
                        onClick = { /*TODO*/ },
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
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = text)
            Spacer(modifier = Modifier.width(16.dp))
            Checkbox(
                checked = checked,
                onCheckedChange = onCheckedChange
            )
        }
    }

enum class ShippingOption {
        Address,
        PickupBox
}
