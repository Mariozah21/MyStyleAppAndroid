package cz.mendelu.pef.mystyleapp.packetaApi.model

import com.squareup.moshi.Json

data class BranchesResponse(
    @Json(name = "data")
    val data: Map<String, PointItem>
)