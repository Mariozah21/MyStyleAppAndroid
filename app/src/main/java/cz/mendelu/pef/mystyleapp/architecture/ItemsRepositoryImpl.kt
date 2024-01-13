package cz.mendelu.pef.mystyleapp.architecture

import cz.mendelu.pef.mystyleapp.data.CartItem
import kotlinx.coroutines.flow.Flow

class ItemsRepositoryImpl(private val itemDao: ItemDao) : ItemsRepository {
    override fun getAllItemsStream(): Flow<List<CartItem>> {
        return itemDao.getAllItems()
    }

    override suspend fun insertItem(cartItem: CartItem) {
        return itemDao.insert(cartItem)
    }

    override suspend fun deleteItem(cartItem: CartItem) {
        return itemDao.delete(cartItem)
    }

    override suspend fun updateItem(cartItem: CartItem) = itemDao.update(cartItem)
}