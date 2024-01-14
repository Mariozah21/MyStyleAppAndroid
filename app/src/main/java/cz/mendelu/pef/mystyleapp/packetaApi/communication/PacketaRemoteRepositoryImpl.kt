package cz.mendelu.pef.mystyleapp.packetaApi.communication

import cz.mendelu.pef.mystyleapp.packetaApi.model.PointResponse
import cz.mendelu.pef.mystyleapp.architecture.CommunicationResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PacketaRemoteRepositoryImpl (
    private val packetaAPI: PacketaAPI
) : IPacketaRemoteRepository {

    override suspend fun getAllBranches(): CommunicationResult<List<PointResponse>>{
        return processResponse(
            withContext(Dispatchers.IO){
                packetaAPI.getAllBranches()
            }
        )
    }

}