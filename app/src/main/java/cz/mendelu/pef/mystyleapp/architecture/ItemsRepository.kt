package cz.mendelu.pef.mystyleapp.architecture

import cz.mendelu.pef.mystyleapp.data.CartItem
import kotlinx.coroutines.flow.Flow

interface ItemsRepository {

    fun getAllItemsStream(): Flow<List<CartItem>>
    suspend fun insertItem(cartItem: CartItem)
    suspend fun deleteItem(cartItem: CartItem)
    suspend fun updateItem(cartItem: CartItem)
}