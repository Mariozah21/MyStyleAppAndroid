package cz.mendelu.pef.mystyleapp.packetaApi.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import cz.mendelu.pef.mystyleapp.packetaApi.communication.PacketaAPI

import cz.mendelu.pef.mystyleapp.packetaApi.model.PointItem
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val apiModule = module {
    single { createWebService<PacketaAPI>() }
}

fun provideRetrofit(moshi: Moshi): Retrofit = Retrofit.Builder()
    .baseUrl("https://www.zasilkovna.cz/api/v4/5d32f243ef0dea43/")
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .build()

fun provideMoshi(): Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()


inline fun <reified T> createWebService(): T {
    return Retrofit.Builder()
        .baseUrl("https://www.zasilkovna.cz/api/v4/5d32f243ef0dea43/")
        .addConverterFactory(MoshiConverterFactory.create(Moshi.Builder().add(KotlinJsonAdapterFactory()).build()))
        .build()
        .create(T::class.java)


}