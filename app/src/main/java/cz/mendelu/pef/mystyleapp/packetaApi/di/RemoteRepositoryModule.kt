package cz.mendelu.pef.mystyleapp.packetaApi.di

import cz.mendelu.pef.mystyleapp.packetaApi.communication.PacketaAPI
import cz.mendelu.pef.mystyleapp.packetaApi.communication.PacketaRemoteRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteRepositoryModule {

    @Provides
    @Singleton
    fun provideRestaurantRemoteRepository(packetaAPI: PacketaAPI): PacketaRemoteRepositoryImpl
            = PacketaRemoteRepositoryImpl(packetaAPI)
}