package cz.mendelu.pef.mystyleapp.packetaApi.di

import cz.mendelu.pef.mystyleapp.architecture.CommunicationResult
import cz.mendelu.pef.mystyleapp.packetaApi.communication.IPacketaRemoteRepository
import cz.mendelu.pef.mystyleapp.packetaApi.communication.PacketaRemoteRepositoryImpl
import cz.mendelu.pef.mystyleapp.packetaApi.model.PointResponse
import org.koin.dsl.module

val packetaRemoteRepositoryModule = module {
    single<IPacketaRemoteRepository> { PacketaRemoteRepositoryImpl(get()) }
}