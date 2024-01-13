package cz.mendelu.pef.mystyleapp.ui.screens.mycart

import cz.mendelu.pef.mystyleapp.data.CartItem

sealed class CartItemsUIState{
    object Default : CartItemsUIState()
    class Success(val cartItems: List<CartItem>): CartItemsUIState()
}
