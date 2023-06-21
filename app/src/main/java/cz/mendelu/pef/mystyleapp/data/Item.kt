package cz.mendelu.pef.mystyleapp.data

import android.os.Parcelable
import cz.mendelu.pef.mystyleapp.ui.components.Constants

data class Item(
    val id: String = "",
    val imageUrl: String = "",
    val title: String = "",
    val price: Double = 0.0,
    val email: String = "",
    val description: String? = null,
    val stockCount: Int? = null,
    val color: String = "",
    val size: String = "",
    val category: String = "",
) {
    constructor() : this("", "", "", 0.0, "")
}