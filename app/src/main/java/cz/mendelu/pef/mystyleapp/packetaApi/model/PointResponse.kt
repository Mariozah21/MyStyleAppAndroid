package cz.mendelu.pef.mystyleapp.packetaApi.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PointResponse(
    @Json(name = "data")
    val data: Map<String, PointItem>
)

@JsonClass(generateAdapter = true)
data class PointItem(
    val id: String,
    val name: String,
    val special: String,
    val place: String,
    val street: String,
    val city: String,
    val zip: String,
    val country: String,
    val currency: String,
    val status: Status,
    @Json(name = "displayFrontend")
    val displayFrontend: String,
    val directions: String,
    val directionsCar: String,
    val directionsPublic: String,
    val wheelchairAccessible: String,
    val creditCardPayment: String,
    val dressingRoom: String,
    val claimAssistant: String,
    val packetConsignment: String,
    val latitude: String,
    val longitude: String,
    val url: String,
    val maxWeight: String,
    @Json(name = "labelRouting")
    val labelRouting: String,
    @Json(name = "labelName")
    val labelName: String,
    val photos: List<Photo>,
    @Json(name = "openingHours")
    val openingHours: OpeningHours
)

@JsonClass(generateAdapter = true)
data class Status(
    @Json(name = "statusId")
    val statusId: String,
    val description: String
)


@JsonClass(generateAdapter = true)
data class Photo(
    val thumbnail: String,
    val normal: String
)

@JsonClass(generateAdapter = true)
data class OpeningHours(
    @Json(name = "compactShort")
    val compactShort: String,
    @Json(name = "compactLong")
    val compactLong: String,
    @Json(name = "tableLong")
    val tableLong: String,
    val regular: Map<String, String>,
    val exceptions: Map<String, String>
)
