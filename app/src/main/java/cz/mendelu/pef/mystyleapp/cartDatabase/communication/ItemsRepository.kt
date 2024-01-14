package cz.mendelu.pef.mystyleapp.cartDatabase.communication

import cz.mendelu.pef.mystyleapp.cartDatabase.model.CartItem
import kotlinx.coroutines.flow.Flow

interface ItemsRepository {

    fun getAllItemsStream(): Flow<List<CartItem>>
    suspend fun insertItem(cartItem: CartItem)
    suspend fun deleteItem(cartItem: CartItem)
    suspend fun updateItem(cartItem: CartItem)
}