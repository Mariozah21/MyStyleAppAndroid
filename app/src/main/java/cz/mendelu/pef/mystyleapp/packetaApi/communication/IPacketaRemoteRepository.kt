package cz.mendelu.pef.mystyleapp.packetaApi.communication

import cz.mendelu.pef.mystyleapp.packetaApi.model.PointResponse
import cz.mendelu.pef.mystyleapp.architecture.CommunicationResult
import cz.mendelu.pef.mystyleapp.architecture.IBaseRemoteRepository

interface IPacketaRemoteRepository : IBaseRemoteRepository {
    suspend fun getAllBranches(): CommunicationResult<List<PointResponse>>
}