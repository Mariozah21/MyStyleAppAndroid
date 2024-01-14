package cz.mendelu.pef.mystyleapp.packetaApi.communication

import cz.mendelu.pef.mystyleapp.architecture.CommunicationResult
import cz.mendelu.pef.mystyleapp.packetaApi.model.BranchesResponse
import cz.mendelu.pef.mystyleapp.packetaApi.model.PointItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PacketaRemoteRepositoryImpl (
    private val packetaAPI: PacketaAPI
) : IPacketaRemoteRepository {

    override suspend fun getAllBranches(): CommunicationResult<BranchesResponse>{
        return processResponse(
            withContext(Dispatchers.IO){
                packetaAPI.getAllBranches()
            }
        )
    }

}