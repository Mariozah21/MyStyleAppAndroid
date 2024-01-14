package cz.mendelu.pef.mystyleapp.cartDatabase.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_items")
data class CartItem(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("id")
    val id: Int? = null,

    @ColumnInfo("title")
    val title: String,
    @ColumnInfo("price")
    val price: Double,
    @ColumnInfo("image_url")
    val imageUrl: String,
    @ColumnInfo("size")
    val size: String,
)
