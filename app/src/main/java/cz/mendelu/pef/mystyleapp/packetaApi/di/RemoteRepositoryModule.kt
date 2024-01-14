package cz.mendelu.pef.mystyleapp.packetaApi.di

import cz.mendelu.pef.mystyleapp.packetaApi.communication.PacketaRemoteRepositoryImpl
import org.koin.dsl.module

val remoteRepositoryModule = module {
    single { PacketaRemoteRepositoryImpl(get()) }
}