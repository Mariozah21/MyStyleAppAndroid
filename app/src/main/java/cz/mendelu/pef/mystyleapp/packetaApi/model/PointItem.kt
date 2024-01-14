package cz.mendelu.pef.mystyleapp.packetaApi.model

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
/*
@JsonClass(generateAdapter = true)
data class PointResponse(
    @Json(name = "data")
    val data: Map<String, PointItem>
)

 */

@JsonClass(generateAdapter = true)
data class PointItem(
    val id: String,
    val name: String,
    val special: String?,
    val place: String,
    val street: String,
    val city: String,
    val zip: String,
    val country: String,
    val currency: String?,
    val status: Status?,
    val latitude: Double,
    val longitude: Double,
    val url: String?,
    val maxWeight: String?,
    @Json(name = "labelRouting")
    val labelRouting: String?,
    @Json(name = "labelName")
    val labelName: String?,
    /*
    @Json(name = "openingHours")
    val openingHours: OpeningHours?

     */
) : ClusterItem {
    override fun getPosition(): LatLng {
        return LatLng(latitude, longitude)
    }

    override fun getTitle(): String? {
        return name.toString()
    }

    override fun getSnippet(): String? {
        return "$city $street"
    }

    //override fun getShortOpeningHours()

    override fun getZIndex(): Float? {
        return 0.0f
    }
}

@JsonClass(generateAdapter = true)
data class Status(
    @Json(name = "statusId")
    val statusId: String?,
    val description: String?
)


@JsonClass(generateAdapter = true)
data class OpeningHours(
    @Json(name = "compactShort")
    val compactShort: String?,
    @Json(name = "compactLong")
    val compactLong: String?,
    @Json(name = "tableLong")
    val tableLong: String?,
    val regular: Map<String, String>?,
    val exceptions: OpeningHoursExceptions?
)

@JsonClass(generateAdapter = true)
data class OpeningHoursExceptions(
    val exception: List<String>?  // adjust the type accordingly
)
