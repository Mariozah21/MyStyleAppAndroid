package cz.mendelu.pef.mystyleapp.ui.screens.mycart

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import cz.mendelu.pef.mystyleapp.architecture.BaseViewModel
import cz.mendelu.pef.mystyleapp.cartDatabase.communication.ItemsRepository
import cz.mendelu.pef.mystyleapp.cartDatabase.model.CartItem
import kotlinx.coroutines.launch


class CartViewModel(
    private val itemsRepository: ItemsRepository
) : BaseViewModel(){

    val cartItems: MutableState<CartItemsUIState> = mutableStateOf(CartItemsUIState.Default)
    var shippingOption: ShippingOption = ShippingOption.Address
    fun addToCart(title: String,price: Double,imageUrl: String,size: String,){
        val cartItem = CartItem( title = title, price = price, imageUrl = imageUrl,size = size)
        launch {
            itemsRepository.insertItem(cartItem)
        }
    }

    fun loadItems(){
        launch{
            itemsRepository.getAllItemsStream().collect{
                cartItems.value = CartItemsUIState.Success(it)
            }
        }
    }

    fun removeFromCart(cartItem: CartItem){
        launch {
            itemsRepository.deleteItem(cartItem)
        }
    }


}