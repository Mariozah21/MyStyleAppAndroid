package cz.mendelu.pef.mystyleapp.data

import android.os.Parcelable

data class Item(
    val imageUrl: String = "",
    val title: String = "",
    val price: Double = 0.0,
    val email: String = ""
){
    constructor() : this("", "", 0.0, "")
}