package cz.mendelu.pef.mystyleapp.packetaApi.communication


import cz.mendelu.pef.mystyleapp.packetaApi.model.BranchesResponse
import cz.mendelu.pef.mystyleapp.packetaApi.model.PointItem
import retrofit2.http.GET
import retrofit2.Response

interface PacketaAPI {

    @GET("branch.json")
    suspend fun getAllBranches(): Response<BranchesResponse>
}