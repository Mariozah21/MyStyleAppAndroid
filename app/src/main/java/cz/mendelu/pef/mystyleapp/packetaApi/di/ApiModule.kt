package cz.mendelu.pef.mystyleapp.packetaApi.di

import cz.mendelu.pef.mystyleapp.packetaApi.communication.PacketaAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun providePetsAPI(retrofit: Retrofit): PacketaAPI
            = retrofit.create(PacketaAPI::class.java)
}