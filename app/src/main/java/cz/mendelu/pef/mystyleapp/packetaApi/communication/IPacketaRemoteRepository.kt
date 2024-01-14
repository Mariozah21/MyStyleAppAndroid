package cz.mendelu.pef.mystyleapp.packetaApi.communication


import cz.mendelu.pef.mystyleapp.architecture.CommunicationResult
import cz.mendelu.pef.mystyleapp.architecture.IBaseRemoteRepository
import cz.mendelu.pef.mystyleapp.packetaApi.model.BranchesResponse
import cz.mendelu.pef.mystyleapp.packetaApi.model.PointItem

interface IPacketaRemoteRepository : IBaseRemoteRepository {
    suspend fun getAllBranches(): CommunicationResult<BranchesResponse>
}