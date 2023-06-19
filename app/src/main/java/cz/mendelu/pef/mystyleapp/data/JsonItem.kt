package cz.mendelu.pef.mystyleapp.data

import android.net.Uri
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.net.URL

@JsonClass(generateAdapter = true)
data class JsonItem(
    @Json(name = "title")
    var title: String,
    @Json(name = "price")
    var price: String,
    @Json(name = "user")
    var user: String,
)