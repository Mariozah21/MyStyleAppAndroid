package cz.mendelu.pef.mystyleapp.packetaApi.di

import cz.mendelu.pef.mystyleapp.packetaApi.communication.PacketaAPI
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val apiModule = module {
    single { createWebService<PacketaAPI>() }
}

inline fun <reified T> createWebService(): T {
    return Retrofit.Builder()
        .baseUrl("https://www.zasilkovna.cz/api/v4/5d32f243ef0dea43/")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
        .create(T::class.java)
}